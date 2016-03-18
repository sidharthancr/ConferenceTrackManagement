package com.thoughtworks.conferencetrack.util;

import java.util.List;

import com.thoughtworks.conferencetrack.beans.Talk;

/**
 * 
 * @author sidharthan.r
 *
 */
public class ApproximateDayCountCalculator {
	
	/** Morning Session Time in minutes */
	private int morningSessionTime;
	/** Evening Session Time in minutes */
	private int eveningSessionTime;

	/**
	 * Getter for morning Session Time
	 * 
	 * @return morningSessionTime
	 */
	public int getMorningSessionTime() {
		return morningSessionTime;
	}

	/**
	 * Setter for morning Session Time
	 * 
	 * @param listOfStrings
	 */
	public void setMorningSessionTime(int morningSessionTime) {
		this.morningSessionTime = morningSessionTime;
	}

	/**
	 * Getter for evening Session Time
	 * 
	 * @return listOfStrings
	 */
	public int getEveningSessionTime() {
		return eveningSessionTime;
	}

	/**
	 * Setter for evening Session Time
	 * 
	 * @param eveningSessionTime
	 */

	public void setEveningSessionTime(int eveningSessionTime) {
		this.eveningSessionTime = eveningSessionTime;
	}

	/**
	 * Constructor to initialize morning and evening Time
	 * 
	 * @param morningSessionTime
	 * @param eveningSessionTime
	 */
	public ApproximateDayCountCalculator(int morningSessionTime,
			int eveningSessionTime) {
		this.morningSessionTime = morningSessionTime;
		this.eveningSessionTime = eveningSessionTime;
	}

	/**
	 * Calculate number of possible days.
	 * 
	 * @param talkList
	 *            the talk list
	 * @return the int Total number of needed
	 */
	public int calculateNumberOfPossibleDays(List<Talk> talkList) {
		// Minimum minutes for a day is 6 hrs , so 360 mins
		final int minimunMinutesPerDay = morningSessionTime
				+ eveningSessionTime;
		if (talkList == null || talkList.size() < 1) {
			return 0;
		}
		int totalMins = 0;
		ConferenceUtil calculateTotalTimeInOfTalks = new ConferenceUtil();
		totalMins = calculateTotalTimeInOfTalks
				.calculateTotalDurationOfTaskList(talkList);
		final int totalNumberOfDays = totalMins / minimunMinutesPerDay;
		return totalNumberOfDays;
	}

}
