package com.thoughtworks.conferencetrack.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.thoughtworks.conferencetrack.beans.Day;
import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.constants.ConferenceTrackManagementConstants;
import com.thoughtworks.conferencetrack.util.ApproximateDayCountCalculator;
import com.thoughtworks.conferencetrack.util.ConferenceUtil;
import com.thoughtworks.conferencetrack.util.StringToTalkObjectParser;

/**
 * The Class ConferenceSchedular is used to parse the talk information
 * from file and prints its track schedule.
 * 
 * @author sidharthan.r
 */
public class ConferenceScheduler {

	/**
	 * Schedule conference according to the string in the given file and prints
	 * it.
	 * 
	 * @param filePath
	 *            Path of the file that contains the conference informations
	 * @throws Exception
	 *             the exception
	 */
	public void scheduleConference(String filePath) throws Exception {
		// read the String from file line by line
		final List<String> listOfStrings = readStringsFromFile(filePath);
		// Validate and create talk objects
		StringToTalkObjectParser stringToTalkObjectParser = new StringToTalkObjectParser(listOfStrings);
		final List<Talk> talkList = stringToTalkObjectParser.validateAndCreateTalk();
		// To efficient allocation sort the order in ascending order
		Collections.sort(talkList);
		Map map= new HashMap<>();
		// Calculate approximate days 
		ApproximateDayCountCalculator calculateApproximateDayCount =new ApproximateDayCountCalculator(180,180);
		final int approxNumberOfDays = calculateApproximateDayCount.calculateNumberOfPossibleDays(talkList);
		
		List<Day> listOfDays= new ArrayList<Day>();
		//get possible combinations for morning sessions
		findCombinationOfSessions(talkList, approxNumberOfDays, true,listOfDays);
		//Throws Exception if it does not find enough morning session combinations
		if (approxNumberOfDays != listOfDays.size()) {
			throw new Exception(
					"Cannot schedule enough morning sessions with given talk times");
		}
		//remove allocated talks from given list 
		for(Day day: listOfDays)
		{
			talkList.removeAll(day.getMorningSessionTalks());
		}
		
		//get possible combinations for evening sessions
		findCombinationOfSessions(talkList, approxNumberOfDays, false,listOfDays);
		
		//remove allocated talks from given list 
		for(Day day: listOfDays)
		{
			if(day.getEveningSessionTalks()!=null)
			talkList.removeAll(day.getEveningSessionTalks());
			else
				//Throws Exception if it does not find enough Evening session combinations
				throw new Exception(
						"Cannot schedule enough evening sessions with given talk times");
				
		}
		ConferenceUtil conferenceUtil= new ConferenceUtil();
		// still some talks remains then it will try to allocate in evening sessions else those are eliminated
		if (!talkList.isEmpty()) {
			for(Day day: listOfDays)
			{
				List<Talk> eveSession= day.getEveningSessionTalks();
				int totalDuration = 0;
				totalDuration = conferenceUtil.calculateTotalDurationOfTaskList(eveSession);
				final List<Talk> listOfAllocatedTalks = new ArrayList<Talk>();
				for (Talk talk : talkList) {
					if (totalDuration + talk.getTalkDuration() <=ConferenceTrackManagementConstants.MAX_EVENING_SESSION_TIME) {
						listOfAllocatedTalks.add(talk);
						talk.setScheduled(true);
						eveSession.add(talk);
					}
				}
				talkList.removeAll(listOfAllocatedTalks);
				if (talkList.isEmpty()) {
					break;
				}
			}
		}
		for(Day day: listOfDays)
		{
			conferenceUtil.updateDayObject(day,ConferenceTrackManagementConstants.LUNCH_TIME,ConferenceTrackManagementConstants.TIME_FORMAT);
		}
		//Print all tracks in required format
		conferenceUtil.printTracks(listOfDays);
		/*	for (Talk talk : talkList) {
			System.out.println(talk.getTalkTitle() + ""
					+ talk.getTalkDuration());
		}*/
	}

	/**
	 * Update the scheduled objects isScheduled to true
	 * 
	 * @param possibleCombinations
	 *            the possible combinations
	 * @param combinationOfSession
	 *            the combination of session
	 */
	private void updateTalkListAndAddToList(List<Day> possibleCombinations,
			List<Talk> combinationOfSession) {
		for (Talk talk : combinationOfSession) {
			// set isScheduled to true
			talk.setScheduled(true);
		}
	
	}

	/**
	 * Find combination of sessions.
	 * 
	 * @param talkList
	 *            The talk List
	 * @param approxNumberOfDays
	 *            the number of days
	 * @param isMorningSession
	 *            true or false
	 * @param listOfDay 
	 * @return the list list of combination talk lists
	 */
	private List<Day> findCombinationOfSessions(List<Talk> talkList,
			int approxNumberOfDays, boolean isMorningSession, List<Day> listOfDay) {
		// define max mins 180 if its morning session else 240
		final int maxSessionTime = isMorningSession ? ConferenceTrackManagementConstants.MAX_MORNING_SESSION_TIME
				: ConferenceTrackManagementConstants.MAX_EVENING_SESSION_TIME;
		//final List<Day> possibleCombinations = new ArrayList<Day>();
		// loop until you find enough combination for each session for Maximum
		// session mins
		int dayIndex=0;
		for (int index = 0; index < talkList.size(); index++) {
			int startingPoint = index;
			int totaltime = 0;// talkList.get(index).getTalkDuration();
			final List<Talk> combinationOfSession = new ArrayList<Talk>();
			while (startingPoint != talkList.size()) {
				final Talk tempTalk = talkList.get(startingPoint);
				startingPoint++;
				if (totaltime == maxSessionTime) {
					break;
				}
				/*
				 * Skip the talk object if talk is scheduled (or) timeDuration
				 * of talk exceeds maxSessionTime (or) Summation of totalTime
				 * and talk's time exceeds maxSessionTime
				 */
				if (tempTalk == null
						|| tempTalk.isScheduled()
						|| tempTalk.getTalkDuration() > maxSessionTime
						|| tempTalk.getTalkDuration() + totaltime > maxSessionTime) {
					continue;
				} else {
					totaltime += tempTalk.getTalkDuration();
					combinationOfSession.add(tempTalk);
				}
			}
			// if it is morning session total duration of session is 180
			if (isMorningSession) {
				if (totaltime == ConferenceTrackManagementConstants.MAX_MORNING_SESSION_TIME) {
					updateTalkListAndAddToList(listOfDay,
							combinationOfSession);
					Day day= new Day();
					day.setMorningSessionTalks(combinationOfSession);
					listOfDay.add(day);
				}
				if (listOfDay.size() == approxNumberOfDays) {
					break;
				}
			} else {
				// if it is Evening session session total duration of session is
				// 180<= to 240>=
				if (totaltime >= ConferenceTrackManagementConstants.MAX_MORNING_SESSION_TIME || totaltime <= ConferenceTrackManagementConstants.MAX_EVENING_SESSION_TIME) {
					updateTalkListAndAddToList(listOfDay,
							combinationOfSession);
					listOfDay.get(dayIndex).setEveningSessionTalks(combinationOfSession);
					dayIndex++;
				}
				if (dayIndex == approxNumberOfDays) {
					break;
				}
			}
			// Break if got enough combinations
			
		}
		return listOfDay;
	}

	/**
	 * Read strings[Talk String] from file line by line and returns the strings
	 * as list of Strings.
	 * 
	 * @param filePath
	 *            the file path
	 * @return List of Strings from file
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	private List<String> readStringsFromFile(String filePath)
			throws FileNotFoundException {
		final Scanner scan = new Scanner(new File(filePath));
		final List<String> listOfStringsFromFile = new ArrayList<String>();
		while (scan.hasNextLine()) {
			final String line = scan.nextLine();
			// Eliminate Empty lines
			if (!line.trim().isEmpty()) {
				listOfStringsFromFile.add(line);
			}
		}
		return listOfStringsFromFile;
	}

}
