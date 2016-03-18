package com.thoughtworks.conferencetrack.beans;

import java.util.List;

/**
 * The Class Day bean to store and fetch talk schedule of a day
 * 
 * @author sidharthan.r
 */
public class Day {

	/**Morning Talks List	 */
	private List<Talk> morningSessionTalks;
	/**Evening Talks List	 */
	private List<Talk> eveningSessionTalks;
	
	
	/**
	 * Get the morning session talk list
	 * 
	 * @return morningSessionTalks
	 * 			morning session talks list
	 */
	public List<Talk> getMorningSessionTalks() {
		return morningSessionTalks;
	}
	/**
	 * Sets morning session talk list
	 * 
	 * @param morningSessionTalks
	 * 			morning session talk list
	 */
	public void setMorningSessionTalks(List<Talk> morningSessionTalks) {
		this.morningSessionTalks = morningSessionTalks;
	}
	/**
	 * Sets the morning session talk list
	 * 
	 * @return eveningSessionTalks
	 * 			evening session talks list
	 */
	public List<Talk> getEveningSessionTalks() {
		return eveningSessionTalks;
	}
	/**
	 * Sets evening session talk list
	 * 
	 * @param eveningSessionTalks
	 * 			list of evening session talks
	 */
	public void setEveningSessionTalks(List<Talk> eveningSessionTalks) {
		this.eveningSessionTalks = eveningSessionTalks;
	}

	
	
}
