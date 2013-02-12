package com.example.globalresovlewws;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATA_BASE_VERSION = 1;

	private static final String DATA_BASE_NAME = "weatherManager";

	private static final String TABLE_WEATHER = "Weather";

	private static final String KEY_LAT = "Latitude";
	private static final String KEY_LONG = "Longitude";
	private static final String KEY_TIME = "Time";
	private static final String KEY_MAX_TEMP = "MaxTemp";
	private static final String KEY_MIN_TEMP = "MinTemp";
	private static final String KEY_CHANCE_PREC = "ChanceofPrecipitation";

	public DatabaseHandler(Context context) {
		super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
	}
/**
 * Overridden method to create the SQLiteDatabase 
 * Database will look like
 * _id     |Time               |Latitude|Longitude|MaxTemp|MinTemp|ChanceofPrecipitation|
 * 1		1200:12:20 11:12:12  52.369   53.214    52		6		56
 * 
 * Time is a string written in TIMESTAMP format
 * Latitude is double type
 * Longitude is a double type
 * MaxTemp is an integer
 * MinTemp is an integer
 * ChanceofPrecipitation is an integer
 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TIME
				+ " TEXT, " + KEY_LAT + " TEXT, " + KEY_LONG + " TEXT, "
				+ KEY_MAX_TEMP + " int not null, " + KEY_MIN_TEMP
				+ " int not null, " + KEY_CHANCE_PREC + " int not null" + ");";
		db.execSQL(CREATE_WEATHER_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Upgrading..." + TABLE_WEATHER);
		onCreate(db);
	}

	/**
	 * addWeather method creates an entry in the SQLite database
	 * @param weather Weather object to be added to the database
	 */
	public void addWeather(Weather weather) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LAT, weather.getLatitude());
		values.put(KEY_LONG, weather.getLongitude());
		values.put(KEY_TIME, weather.getTime());
		values.put(KEY_CHANCE_PREC, weather.getChanceOfPrecipi());
		values.put(KEY_MAX_TEMP, weather.getMaxTemp());
		values.put(KEY_MIN_TEMP, weather.getMinTemp());

		db.insert(TABLE_WEATHER, null, values);
		db.close();
	}
/**
 * Method returns a Weather object when given a time
 * @param time String in which the weather object was recorded
 * @return a Weather object of the corresponding time
 */
	public Weather getWeather(String time) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_WEATHER,
				new String[] { KEY_TIME, KEY_LAT, KEY_LONG, KEY_MAX_TEMP,
						KEY_MIN_TEMP, KEY_CHANCE_PREC, }, KEY_TIME + "=?",
				new String[] { time }, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Weather weather = new Weather(cursor.getString(0),
				Double.parseDouble(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor
						.getString(4)), Integer.parseInt(cursor.getString(5)));
		return weather;

	}

	public List<Weather> getAllWeather() {
		List<Weather> weatherList = new ArrayList<Weather>();

		String selectQuery = "SELECT * FROM" + TABLE_WEATHER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Weather weather = new Weather();
				weather.setTime(cursor.getString(0));
				weather.setLatitude(Double.parseDouble(cursor.getString(1)));
				weather.setLongitude(Double.parseDouble(cursor.getString(2)));
				weather.setMaxTemp(Integer.parseInt(cursor.getString(3)));
				weather.setMinTemp(Integer.parseInt(cursor.getString(4)));
				weather.setChanceOfPrecipi(Integer.parseInt(cursor.getString(5)));
			} while (cursor.moveToNext());
		}
		return weatherList;

	}

	public void deleteWeather(Weather weather) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WEATHER, KEY_TIME + "=?",
				new String[] { String.valueOf(weather.getTime()) });
		db.close();
	}
}
