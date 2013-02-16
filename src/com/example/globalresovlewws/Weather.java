package com.example.globalresovlewws;

/**
 * Weather object that stores relevant data types of a weather
 * Includes Latitude, longitude, max temperature, minimum temperature
 * time of query and chance of precipitation
 * @author Brian
 * 
 */
public class Weather {
	
	private double latitude;
	private double longitude;
	private String time;
	private int	maxTemp;
	private int minTemp;
	private int chanceOfPrecipi;
	
	
	/**
	 * Default constructor
	 */
	public Weather() {

	}
/**
 * Constructor used to make a weather object
 * @param time	The time that this weather was recorded
 * @param latitude	Recorded Latitude of this weather object
 * @param longitude	Recorded Longitude of this weather object
 * @param maxTemp	The maximum predicted temperature at time of recording
 * @param minTemp	The minimnum predicted temperature at time of recording
 * @param cOP		The predicted chance of precipitation
 */
	public Weather(String time, double latitude, double longitude, int maxTemp, int minTemp, int cOP){
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.chanceOfPrecipi = cOP;
	}

/**
 * 
 * @return Returns the latitude of the weather object in double type
 */
	public double getLatitude() {
		return latitude;
	}


/**
 * 
 * @param latitude	Sets the latitude of the weather object must be double type
 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


/**
 * 
 * @return	Returns a double of the longitude of the weather object
 */
	public double getLongitude() {
		return longitude;
	}


/**
 * 
 * @param longitude	Sets the longitude of the weather object type must be double
 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


/**
 * 
 * @return Returns a string of the time this weather object was recorded
 */
	public String getTime() {
		return time;
	}

/**
 * 
 * @param time Sets the time of the weather object, type must be String
 */

	public void setTime(String time) {
		this.time = time;
	}


/**
 * 
 * @return Returns an int of the maximum predicted temperature
 */
	public int getMaxTemp() {
		return maxTemp;
	}


/**
 * 
 * @param maxTemp Accepts an int as the parameter to set the temperature of the weather object
 */
	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}


/**
 * 
 * @return Returns an int of the minimum temperature of the weather object
 */
	public int getMinTemp() {
		return minTemp;
	}


/**
 * 
 * @param minTemp Accepts an int as a parameter and sets the minimum temperature of the weather object
 */
	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}


/**
 * 
 * @return Returns the predicted chance of precipitation as an int
 */
	public int getChanceOfPrecipi() {
		return chanceOfPrecipi;
	}

/**
 * 
 * @param chanceOfPrecipi Accepts an int as the parameter and sets the weather objects chance of precipitation
 */

	public void setChanceOfPrecipi(int chanceOfPrecipi) {
		this.chanceOfPrecipi = chanceOfPrecipi;
	}
}
