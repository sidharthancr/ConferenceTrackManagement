package com.thoughtworks.conferencetrack.management.test;

/**
 * 
 * @author sidharthan.r
 * 
 */
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.conferencetrack.exception.InvalidTalkException;
import com.thoughtworks.conferencetrack.management.ConferenceScheduler;

public class ConferenceSchedularTest {

	static ConferenceScheduler conferenceSchedular;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		conferenceSchedular= new ConferenceScheduler();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		conferenceSchedular=null;
	}

	@Test
	public void testScheduleConference() throws Exception {
		URL url = getClass().getResource("/resource/input.txt");
		conferenceSchedular.scheduleConference(url.getPath());
	}
	@Test(expected=InvalidTalkException.class)
	public void testScheduleConferenceException() throws Exception {
		URL url = getClass().getResource("/resource/InvalidInput.txt");
		conferenceSchedular.scheduleConference(url.getPath());
	}

}
