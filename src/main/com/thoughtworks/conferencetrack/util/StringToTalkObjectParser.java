package com.thoughtworks.conferencetrack.util;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.constants.ConferenceTrackManagementConstants;
import com.thoughtworks.conferencetrack.exception.InvalidTalkException;
/**
 * The Class StringToTalkObjectParser handles list of Strings into List of Talk object
 * 
 * @author sidharthan.r
 */
public class StringToTalkObjectParser {

	/** List Of Strings*/
	private List<String> listOfStrings;
	
	/**
	 * Constructor to get initial value as List of strings
	 * 
	 * @param listOfString
	 */
	public StringToTalkObjectParser(List<String> listOfString)
	{
		this.listOfStrings=listOfString;
	}
	
	/**
	 *Getter for listOFString
	 * 
	 * @return listOfStrings
	 */
	public List<String> getListOfStrings() {
		return listOfStrings;
	}

	/**
	 *Setter for listOFString
	 * 
	 * @param listOfStrings
	 */
	public void setListOfStrings(List<String> listOfStrings) {
		this.listOfStrings = listOfStrings;
	}

	/**
	 * Validate and create talk's informations[Title and Time].
	 * 
	 * @param listOfStrings
	 *            the list of strings
	 * @return the list
	 * @throws InvalidTalkException
	 *             the invalid talk exception
	 */
	public List<Talk> validateAndCreateTalk()
			throws InvalidTalkException {
		final List<Talk> listOfTalks = new ArrayList<Talk>();
		// Checks whether list strings are empty or not
		if (listOfStrings == null || listOfStrings.size() < 1) {
			throw new InvalidTalkException("Empty Talk list");
		}
		for (String talkString : listOfStrings) {
			talkString = talkString.trim();
			final int indexOfLastSpace = talkString.lastIndexOf(" ");
			// talk should mentioned time at last with space in between them
			if (indexOfLastSpace == -1) {
				throw new InvalidTalkException(
						"Should mention talk with its's time");
			}
			final String talkTitle = talkString.substring(0, indexOfLastSpace);
			final String talkTime = talkString.substring(indexOfLastSpace + 1);
			// Title should not contains numbers
			if (talkTitle.matches(".*\\d.*")) {
				throw new InvalidTalkException(
						"Talk title should not contain numbers");
			}
			// Parse and convert the time from string to integer
			final int time = parseAndValidateTimeFromString(talkTime);
			listOfTalks.add(new Talk(time, talkTitle));
		}
		return listOfTalks;
	}
	

	/**
	 * Parses the and validate time of Talk.
	 * 
	 * @param talkTime
	 *            the talk's time
	 * @return the valid time
	 * @throws InvalidTalkException
	 *             the invalid talk exception
	 */
	private int parseAndValidateTimeFromString(String talkTime)
			throws InvalidTalkException {
		if (talkTime.equalsIgnoreCase(ConferenceTrackManagementConstants.LIGHTINING_STRING_FORMAT)) {
			//lightingTime is equal to 5mins
			return 5;
		} else if (talkTime.endsWith(ConferenceTrackManagementConstants.MINUTE_FORMAT)) {
			talkTime = talkTime.replace(ConferenceTrackManagementConstants.MINUTE_FORMAT, "");
			try {
				// check the time format is valid or not
				Integer.parseInt(talkTime.trim());
			} catch (NumberFormatException e) {
				throw new InvalidTalkException(
						"Invalid number format [Example : 60min or lighting].");
			}
			// check time is more than 0 and less than 240 minutes if not it
			// will raise Invalid Talk Exception
			if (Integer.parseInt(talkTime.trim()) == 0) {
				throw new InvalidTalkException("Talk time Should Not be Zero");
			} else if (Integer.parseInt(talkTime.trim()) > ConferenceTrackManagementConstants.MAX_EVENING_SESSION_TIME) {
				throw new InvalidTalkException(
						"Talk time should be less than or equal to 240min");
			}
			return Integer.parseInt(talkTime.trim());
		} else {
			throw new InvalidTalkException("Invalid talk time Format :"
					+ talkTime);
		}
	}


}
