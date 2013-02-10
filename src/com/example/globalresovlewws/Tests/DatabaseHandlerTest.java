package com.example.globalresovlewws.Tests;

import static org.junit.Assert.*;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
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
		weather1 = new Weather("011212", 57.2, 52.3, 75, 50, 50);
	}

	@Test
	public void testAddWeather() {
		db.addWeather(weather1);
		assertEquals("011212",db.getWeather("011212"));
	}
	
	public void tearDown() throws Exception{
        db.close(); 
        super.tearDown();
    }

}
