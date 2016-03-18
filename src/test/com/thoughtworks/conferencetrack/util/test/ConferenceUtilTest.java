package com.thoughtworks.conferencetrack.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.conferencetrack.beans.Day;
import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.util.ConferenceUtil;

public class ConferenceUtilTest {
	static ConferenceUtil conferenceUtil;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		conferenceUtil= new ConferenceUtil();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		conferenceUtil=null;
	}

	@Test
	public void testCalculateTotalDurationOfTaskList() {
		List<Talk> listOfTalk= new ArrayList<Talk>();
		listOfTalk.add(new Talk(60,"Ruby on Rails"));
		listOfTalk.add(new Talk(120,"Why Rails"));
		listOfTalk.add(new Talk(180,"Python"));
		Assert.assertEquals(conferenceUtil.calculateTotalDurationOfTaskList(listOfTalk),360);
	}

	@Test
	public void testUpdateDayObject() {
		Day day= new Day();
		List<Talk> listOfTalk= new ArrayList<Talk>();
		listOfTalk.add(new Talk(60,"Ruby on Rails"));
		listOfTalk.add(new Talk(120,"Why Rails"));
		day.setMorningSessionTalks(listOfTalk);
		listOfTalk= new ArrayList<Talk>();
		listOfTalk.add(new Talk(180,"Python"));
		listOfTalk.add(new Talk(60,"Java"));
		day.setEveningSessionTalks(listOfTalk);
		conferenceUtil.updateDayObject(day, 60, "hh:mma");
		for(Talk talk:day.getMorningSessionTalks())
		{
			Assert.assertNotNull(talk.getScheduledTime());
		}
		for(Talk talk:day.getEveningSessionTalks())
		{
			Assert.assertNotNull(talk.getScheduledTime());
		}
		Assert.assertEquals(day.getMorningSessionTalks().get(0).getScheduledTime(), "09:00AM");
		//Check Date format change 
		conferenceUtil.updateDayObject(day, 60, "hhmma");
		Assert.assertEquals(day.getMorningSessionTalks().get(0).getScheduledTime(), "0900AM");
	}

}
