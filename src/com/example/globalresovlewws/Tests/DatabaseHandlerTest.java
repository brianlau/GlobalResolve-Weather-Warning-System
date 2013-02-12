package com.example.globalresovlewws.Tests;

import android.content.Context;
import android.test.AndroidTestCase;
import com.example.globalresovlewws.DatabaseHandler;
import com.example.globalresovlewws.Weather;

import org.junit.Test;

public class DatabaseHandlerTest extends AndroidTestCase{

	private DatabaseHandler db;
	private Weather weather1;
	
	@Override
	public void setUp(){
		Context context = getContext();
		db = new DatabaseHandler(context);
		weather1 = new Weather("1", 57.2, 52.3, 75, 50, 50);
	}

	@Test
	public void testAddWeather() {
		db.addWeather(weather1);
		assertNotNull(db.getWeather("1"));
	}
	
	@Test
	public void testGetWeather(){
		Weather testWeather = db.getWeather("1");
		assertEquals(50,testWeather.getChanceOfPrecipi());
		assertEquals("1",testWeather.getTime());
		assertEquals(57.2,testWeather.getLatitude());
		assertEquals(52.3,testWeather.getLongitude());
		assertEquals(75,testWeather.getMaxTemp());
		assertEquals(50,testWeather.getMinTemp());
	}
	
	@Override
	public void tearDown() throws Exception{
        db.close(); 
        super.tearDown();
    }

}
