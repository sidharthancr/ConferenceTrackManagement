package com.thoughtworks.conferencetrack;

import java.util.Scanner;

import com.thoughtworks.conferencetrack.management.ConferenceScheduler;

/**
 * ConferenceTrackerManagement class used to handle conference schedule using
 * ConferenceScheduler class
 * 
 * @author sidharthan.r
 * 
 */
public class ConferenceTrackManagement {
	/**
	 * @param args
	 */
	public static void main(String[] args)  		{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the path of file with its name: ");
		String fileName = scan.nextLine();
		try {
			ConferenceScheduler conferenceSchedular = new ConferenceScheduler();
			conferenceSchedular.scheduleConference(fileName);

		} catch (Exception e) {
			System.err.print("Error while executing ConferenceTrackerManagement: ");
			e.printStackTrace();
		}
	}
}
