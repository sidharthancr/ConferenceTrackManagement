package com.thoughtworks.conferencetrack.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.thoughtworks.conferencetrack.beans.Day;
import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.constants.ConferenceTrackManagementConstants;
/**
 * The Class ConferenceUtil has utility needed by conference scheduler 
 * 
 * @author sidharthan.r
 */
public class ConferenceUtil {
	
	/**
	 * Calculate the total duration of task list.
	 * 
	 * @param talkList
	 *            the talk list
	 * @return totalMins the total duration of task list
	 */
	public int calculateTotalDurationOfTaskList(List<Talk> talkList) {
		int totalMins=0;
		if(talkList!=null){
			for (Talk talk : talkList) {
				totalMins += talk.getTalkDuration();
			}
		}
		return totalMins;
	}
	
	/**
	 * This method update day with given time format 
	 *  
	 * @param day
	 * 			day object
	 * @param lunchTime
	 * 			lunch time duration
	 * @param timeFormat
	 * 			time format in string
	 */
	public void updateDayObject(Day day,int lunchTime,String timeFormat)
	{
		//Date with time Nine O'clock 
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		// Date formatter to format its time alone
		final SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
		List<Talk> listOfTalk = day.getMorningSessionTalks();
		// Print the Morning track
		for (Talk talk : listOfTalk) {
			talk.setScheduledTime(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, talk.getTalkDuration());
		}
		//Add 60 mins to calendar
		calendar.add(Calendar.MINUTE, lunchTime);
		//Add lunch to evening session
		Talk lunch= new Talk(60,"Lunch");
		lunch.setScheduledTime(dateFormat.format(calendar.getTime()));
		listOfTalk.add(lunch);
		day.setMorningSessionTalks(listOfTalk);
		listOfTalk = day.getEveningSessionTalks();
		// Print the Evening track
		for (Talk talk : listOfTalk) {
			talk.setScheduledTime(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, talk.getTalkDuration());
		}
		//Add networkEvent to evening session
		Talk networkEvent= new Talk(60,ConferenceTrackManagementConstants.NETWORKING_EVENT+"\n");
		networkEvent.setScheduledTime(dateFormat.format(calendar.getTime()));
		listOfTalk.add(networkEvent);
		day.setEveningSessionTalks(listOfTalk);
	}
	
	/**
	 * This method gets input as listOfDay and prints track
	 * 
	 * @param listOfDay
	 * 			List of days 
	 */
	public void printTracks(List<Day> listOfDay)
	{
		int tractNumber=1;
		for(Day day:listOfDay)
		{
			System.out.println("Track "+(tractNumber++)+":");
			//Print morning Session
			for(Talk talk : day.getMorningSessionTalks())
			{
				System.out.println(talk.getScheduledTime()+talk.getTalkTitle());
			}
			//Print evening sessions
			for(Talk talk : day.getEveningSessionTalks())
			{
				System.out.println(talk.getScheduledTime()+talk.getTalkTitle());
			}
		}
	}
}
