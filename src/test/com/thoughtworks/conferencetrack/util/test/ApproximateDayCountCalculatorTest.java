package com.thoughtworks.conferencetrack.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.util.ApproximateDayCountCalculator;

public class ApproximateDayCountCalculatorTest {

	@Test
	public void testCalculateNumberOfPossibleDays() {
		ApproximateDayCountCalculator approximateDayCountCalculator = new ApproximateDayCountCalculator(180,180);
		List<Talk> listOfTalk= new ArrayList<Talk>();
		listOfTalk.add(new Talk(60,"Ruby on Rails"));
		listOfTalk.add(new Talk(120,"Why Rails"));
		listOfTalk.add(new Talk(180,"Python"));
		listOfTalk.add(new Talk(60,"Java"));
		listOfTalk.add(new Talk(180,"JVM"));
		listOfTalk.add(new Talk(120,"JRuby"));
		Assert.assertEquals(2,approximateDayCountCalculator.calculateNumberOfPossibleDays(listOfTalk));
		listOfTalk.add(new Talk(60,"MonogDB"));
		listOfTalk.add(new Talk(180,"Elasticsaerch"));
		listOfTalk.add(new Talk(120,"Cordova"));
		listOfTalk.add(new Talk(120,"PhoneGap"));
		Assert.assertEquals(3,approximateDayCountCalculator.calculateNumberOfPossibleDays(listOfTalk));
	}

}
