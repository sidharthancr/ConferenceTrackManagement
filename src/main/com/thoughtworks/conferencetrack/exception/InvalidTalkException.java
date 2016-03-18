package com.thoughtworks.conferencetrack.exception;

/**
 * InvalidTalkException class is used to handle the invalid talk's time or
 * invalid title exceptions
 * 
 * 
 * @author sidharthan.r
 * 
 */
public class InvalidTalkException extends Exception {

	private static final long serialVersionUID = 8449773575098087733L;

	/**
	 * Instantiates a new invalid Talk exception.
	 * 
	 * @param message
	 *            Message describes why Talk is invalid
	 */
	public InvalidTalkException(String message) {
		super(message);
	}

}
