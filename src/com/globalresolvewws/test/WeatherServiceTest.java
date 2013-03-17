package com.globalresolvewws.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.globalresolvewws.*;

import org.junit.Test;

public class WeatherServiceTest {

	@Test
	public void test() {
		WeatherService WS = new WeatherService();
		ArrayList<Weather> al = WS.Weather();
		assertNotNull(al);
	}

}
