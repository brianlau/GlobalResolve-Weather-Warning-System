package com.globalresolvewws;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ServiceHandler extends DefaultHandler {

	// Create three array lists to store the data
	public ArrayList<Integer> tempLows = new ArrayList<Integer>();
	public ArrayList<Integer> tempHighs = new ArrayList<Integer>();
	public ArrayList<String> days = new ArrayList<String>();

	// Make sure that the code in DefaultHandler's
	//   constructor is called:
	public ServiceHandler() {
		super();
	}

	/*** Below are the three methods that we are extending ***/

	public void startDocument() {
		System.out.println("Start document");
    }

	public void endDocument() {
		System.out.println("End document");
	}

	// This is where all the work is happening:
	public void startElement (String uri, String name, String qName, Attributes atts) {
		if (qName.compareTo("day_of_week") == 0) {
			String day = atts.getValue(0);
			System.out.println("Day: " + day);
			days.add(day);
		}
		if(qName.compareToIgnoreCase("low") == 0) {
			int low = Integer.parseInt(atts.getValue(0));
			System.out.println("Low: " + low);
			tempLows.add(low);
		}
		if(qName.compareToIgnoreCase("high") == 0) {
			int high = Integer.parseInt(atts.getValue(0));
			System.out.println("High: " + high);
			tempHighs.add(high);
		}
	}
}
