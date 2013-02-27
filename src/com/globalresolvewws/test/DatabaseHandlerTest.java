package com.globalresolvewws.test;

import java.util.List;
import android.content.Context;
import android.test.AndroidTestCase;

import com.globalresolvewws.DatabaseHandler;
import com.globalresolvewws.Weather;

import org.junit.Test;

public class DatabaseHandlerTest extends AndroidTestCase {

	private DatabaseHandler db;
	private Weather weather1;
	private Context context;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		context = getContext();
		db = new DatabaseHandler(context);
		weather1 = new Weather("1", 57.2, 52.3, 75, 50, 50);
	}

	@Test
	public void testAddWeather() {
		db.addWeather(weather1);
		db.addWeather(new Weather("12", 65.3, 86.2, 100, 87, 50));
		assertNotNull(db.getWeather("65.3", "86.2"));
		assertNotNull(db.getWeather("57.2", "52.3"));
	}

	@Test
	public void testGetWeather() {
		db.addWeather(new Weather("12", 65.3, 86.2, 100, 87, 50));
		Weather testWeather = db.getWeather("65.3", "86.2");
		assertEquals(50, testWeather.getChanceOfPrecipi());
		assertEquals("12", testWeather.getTime());
		assertEquals(65.3, testWeather.getLatitude());
		assertEquals(86.2, testWeather.getLongitude());
		assertEquals(100, testWeather.getMaxTemp());
		assertEquals(87, testWeather.getMinTemp());
	}

	@Test
	public void testDeleteWeather() {
		db.addWeather(weather1);
		Weather testWeather = db.getWeather("57.2", "52.3");
		db.deleteWeather(testWeather);
	}

	@Test
	public void testGetAllWeather() {
		db.addWeather(weather1);
		db.addWeather(new Weather("12", 65.3, 86.2, 100, 87, 50));
		List<Weather> allWeather = db.getAllWeather();
		Weather testWeather1 = allWeather.get(0);
		Weather testWeather2 = allWeather.get(1);
		assertEquals(50, testWeather2.getChanceOfPrecipi());
		assertEquals("12", testWeather2.getTime());
		assertEquals(65.3, testWeather2.getLatitude());
		assertEquals(86.2, testWeather2.getLongitude());
		assertEquals(100, testWeather2.getMaxTemp());
		assertEquals(87, testWeather2.getMinTemp());
		
		assertEquals(50, testWeather1.getChanceOfPrecipi());
		assertEquals("1", testWeather1.getTime());
		assertEquals(57.2, testWeather1.getLatitude());
		assertEquals(52.3, testWeather1.getLongitude());
		assertEquals(75, testWeather1.getMaxTemp());
		assertEquals(50, testWeather1.getMinTemp());
	}

	@Override
	public void tearDown() throws Exception {
		context.deleteDatabase("weatherManager");
		db.close();
		super.tearDown();
	}

}
