package com.thoughtworks.conferencetrack.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.conferencetrack.beans.Talk;
import com.thoughtworks.conferencetrack.exception.InvalidTalkException;
import com.thoughtworks.conferencetrack.util.StringToTalkObjectParser;

public class StringToTalkObjectParserTest {

	@Test
	public void testValidateAndCreateTalk() throws InvalidTalkException {
		List<String> listOfString= new ArrayList<String>();
		listOfString.add("Ruby On Rails 50min");
		StringToTalkObjectParser stringToTalkObjectParser= new StringToTalkObjectParser(listOfString);
		List<Talk> listOfTalk = stringToTalkObjectParser.validateAndCreateTalk();
			Talk talk= listOfTalk.get(0);
			Assert.assertEquals(talk.getTalkTitle(),"Ruby On Rails");
			Assert.assertEquals(talk.getTalkDuration(),50);
		
		}
		
		@Test(expected=InvalidTalkException.class)
		public void testValidateAndCreateTalkNegative() throws InvalidTalkException{
			List<String> listOfString= new ArrayList<String>();
			listOfString.add("Ruby On 4Rails 50min");
			StringToTalkObjectParser stringToTalkObjectParser= new StringToTalkObjectParser(listOfString);
			stringToTalkObjectParser.validateAndCreateTalk();
	}

}
