package com.example.globalresovlewws.Tests;

import java.util.List;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.globalresovlewws.DatabaseHandler;
import com.example.globalresovlewws.Weather;

import org.junit.Test;

public class DatabaseHandlerTest extends AndroidTestCase{

	private DatabaseHandler db;
	private Weather weather1;
	private Context context;
	@Override
	public void setUp() throws Exception{
		super.setUp();
		context = getContext();
		db = new DatabaseHandler(context);
		weather1 = new Weather("1", 57.2, 52.3, 75, 50, 50);
	}

	@Test
	public void testAddWeather() {
		db.addWeather(weather1);
		db.addWeather(new Weather("12", 65.3,86.2,100,87,50));
		assertNotNull(db.getWeather("57.2","52.3"));
	}
	
	@Test
	public void testGetWeather(){
		Weather testWeather = db.getWeather("65.3","86.2");
		assertEquals(50,testWeather.getChanceOfPrecipi());
		assertEquals("12",testWeather.getTime());
		assertEquals(65.3,testWeather.getLatitude());
		assertEquals(86.2,testWeather.getLongitude());
		assertEquals(100,testWeather.getMaxTemp());
		assertEquals(87,testWeather.getMinTemp());
	}
	
	@Test
	public void testDeleteWeather(){
		Weather testWeather = db.getWeather("57.2","52.3");
		db.deleteWeather(testWeather);
	}
	
	@Test
	public void testGetAllWeather(){
		List<Weather> allWeather = db.getAllWeather();
		 for (Weather cn : allWeather) {
	            String log = "Time: "+cn.getTime()+" ,Latitude: " + cn.getLatitude() + " ,Longitude: " + cn.getLongitude() + " ,MaxTemp: " + cn.getMaxTemp();
	                // Writing Contacts to log
	        Log.d("Name: ", log);
		 }
	}
	
	@Override
	public void tearDown() throws Exception{
		db.close();
        context.deleteDatabase(""+db.getWritableDatabase());
        super.tearDown();
    }

}
