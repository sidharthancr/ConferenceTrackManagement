package com.thoughtworks.conferencetrack.beans;


/**
 * The Class Talk bean to store and fetch its title and time.
 * It implements comparable to sort talks in descending order
 * 
 * @author sidharthan.r
 */
public class Talk implements Comparable<Talk> {

	/** The duration of talk. */
	private int talkDuration;

	/** The title of talk. */
	private String talkTitle;

	/** Flag to refer the talk is scheduled or not. */
	private boolean isScheduled;
	
	/** Scheduled time of talk */
	private String scheduledTime;

	/**
	 * Gets the scheduled Time.
	 * 
	 * @return scheduledTime
	 * 				scheduled Time as String
	 */
	public String getScheduledTime() {
		return scheduledTime;
	}

	/**
	 * Sets the Schedule time .
	 * 
	 * @param scheduledTime
	 *            scheduled Time as String
	 */
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	/**
	 * Gets the talk duration.
	 * 
	 * @return talkDuration
	 * 				the talk's duration
	 */
	public int getTalkDuration() {
		return talkDuration;
	}

	/**
	 * Sets the talk's duration.
	 * 
	 * @param talkDuration
	 *            the talk's duration
	 */
	public void setTalkDuration(int talkDuration) {
		this.talkDuration = talkDuration;
	}

	/**
	 * Gets the talk's title.
	 * 
	 * @return talkTitle
	 * 			 the talk's title
	 */
	public String getTalkTitle() {
		return talkTitle;
	}

	/**
	 * Sets the talk's title.
	 * 
	 * @param talkTitle
	 *            the talk's title
	 */
	public void setTalkTitle(String talkTitle) {
		this.talkTitle = talkTitle;
	}

	/**
	 * Checks if talk is scheduled or not.
	 * 
	 * @return isScheduled 
	 *  			true, if the talk is scheduled (or) false, if the talk is not scheduled
	 */
	public boolean isScheduled() {
		return isScheduled;
	}

	/**
	 * Update the scheduled flag.
	 * 
	 * @param isScheduled
	 *            the scheduled flag
	 */
	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	/**
	 * Instantiates a new talk.
	 * 
	 * @param talkDuration
	 *            the talk's duration time
	 * @param talkTitle
	 *            the talk's title
	 */
	public Talk(int talkDuration, String talkTitle) {
		super();
		this.talkDuration = talkDuration;
		this.talkTitle = talkTitle;
	}

	/**
	 * To sort list of talks in descending order according to Talk's time
	 * implementing compareTo of comparable interface
	 * 
	 * @param Talk
	 * @return integer
	 */
	@Override
	public int compareTo(Talk talk) {
		if (this.talkDuration > talk.talkDuration) {
			return 1;
		} else if (this.talkDuration < talk.talkDuration) {
			return -1;
		} else {
			return 0;
		}
	}
}
