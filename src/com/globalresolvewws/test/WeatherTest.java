package com.globalresolvewws.test;


import org.junit.Test;

import android.test.AndroidTestCase;

import com.globalresolvewws.Weather;

public class WeatherTest extends AndroidTestCase {

	
	@Test
	public void testConstructor() throws Exception {
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		assertNotNull(weather1);
		assertEquals("12:32:01", weather1.getTime());
		assertEquals(45.2, weather1.getLatitude(),.01);
		assertEquals(78.1, weather1.getLongitude(),.01);
		assertEquals(90, weather1.getMaxTemp());
		assertEquals(78, weather1.getMinTemp());
		assertEquals(12, weather1.getHumidity());
		assertEquals(1.23, weather1.getPressure());
		assertNotNull(weather2);
	}

	@Test
	public void testSetandGetTime(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setTime("02:56:32");
		weather2.setTime("05:36:59");
		assertEquals("02:56:32",weather1.getTime());
		assertEquals("05:36:59",weather2.getTime());
	}
	
	@Test
	public void testSetandGetLatitude(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setLatitude(58.2);
		weather2.setLatitude(74.1);
		assertEquals(58.2,weather1.getLatitude(),.1);
		assertEquals(74.1,weather2.getLatitude(),.1);
	}
	
	@Test public void testSetandGetLongitude(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setLongitude(87.6);
		weather2.setLongitude(98.5);
		assertEquals(87.6,weather1.getLongitude(),.1);
		assertEquals(98.5,weather2.getLongitude(),.1);
	}
	
	@Test public void testSetAndGetMaxTemp(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setMaxTemp(100);
		weather2.setMaxTemp(98);
		assertEquals(100,weather1.getMaxTemp(),.1);
		assertEquals(98,weather2.getMaxTemp(),.1);
	}
	
	@Test public void testSetAndGetMinTemp(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setMinTemp(20);
		weather2.setMinTemp(-1);
		assertEquals(20,weather1.getMinTemp(),.1);
		assertEquals(-1,weather2.getMinTemp(),.1);
	}
	
	@Test public void testSetAndGetHumidity(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setHumidity(50);
		weather2.setHumidity(2);
		assertEquals(50,weather1.getHumidity());
		assertEquals(2,weather2.getHumidity());
	}
	
	@Test public void testSetandGetPressure(){
		Weather weather1 = new Weather("12:32:01", 45.2, 78.1, 90, 78, 12, 1.23);
		Weather weather2 = new Weather("01:23:75", 101.2, 45.3, 1, -3, 1, 1.52);
		weather1.setPressure(2.5);
		weather2.setPressure(.9);
		assertEquals(2.5,weather1.getPressure(),.1);
		assertEquals(.9,weather2.getPressure(),.1);
	}


}
