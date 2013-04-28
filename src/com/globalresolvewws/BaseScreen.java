package com.globalresolvewws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseScreen extends Activity {
	// Debugging
	private static final String TAG = "Weather Warning";

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mbtA = null;
	// Member object for the chat services
	private BluetoothConnectionHandler mConnectionHandler = null;

	private TextView mTitle;
	private Button mUpdateButton;
	private Button sUpdateButton;
	private TextView tempCurr;
	private TextView serviceValue;
	private ArrayList<Weather> WeatherList;
	private WeatherService WS;
	private ImageView risk;

	public boolean weatherFlag = false;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		// Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText(R.string.app_name);
		mTitle = (TextView) findViewById(R.id.title_right_text);
		setContentView(R.layout.activity_main);
		serviceValue = (TextView) findViewById(R.id.temp_service);
		tempCurr = (TextView) findViewById(R.id.temp_curr);
		risk = (ImageView) findViewById(R.id.imageView);

		// Bluetooth adapter
		mbtA = BluetoothAdapter.getDefaultAdapter();
		if (mbtA == null) {
			Toast.makeText(this, "Bluetooth not supported with this device",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		final Button btnMapView = (Button) findViewById(R.id.buttonMapView);
		setupServiceConnection();
		btnMapView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent mapScreen = new Intent(getApplicationContext(),
						MapScreen.class);
				startActivity(mapScreen);
				finish();
			}
		});

		// If BT is not on, request that it be enabled.
		mbtA = BluetoothAdapter.getDefaultAdapter();
		if (mbtA == null) {
			Toast.makeText(this, "Bluetooth not supported with this device",
					Toast.LENGTH_LONG).show();
		}
		else if (!mbtA.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mConnectionHandler == null)
				setupConnection();
		}
	}

	public String getLatLongValues()
	{
		LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		String longitude = cleanValues(Double.toString(location.getLongitude()));
		String latitude = cleanValues(Double.toString(location.getLatitude()));
		
		
		
		return latitude + "," + longitude;
	}
	
	public String cleanValues(String l)
	{
		l = l.replace(".", "");
		while(l.length() < 9)
			l = l.concat("0");
		if(l.contains("-"))
			l = l.substring(0, 9);
		else
			l = l.substring(0, 8);
		
		return l;
	}
	
	private void setupServiceConnection() {
		// Start the service
		sUpdateButton = (Button) findViewById(R.id.updateServiceValues);
		sUpdateButton.setOnClickListener(new OnClickListener() {
			public void onClick(final View view) {
				if (WS == null) {
					WS = new WeatherService();
				}
				try {
					if(weatherFlag == false)
					{
					WeatherList = WS.execute(getLatLongValues()).get();
					weatherFlag = true;
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				serviceValue.setText("Current Risk(1-10): "
						+ String.valueOf(quickDirtyRisk(WeatherList.get(0)))
						+ "\n" + String.valueOf(WeatherList.get(1).getTime())
						+ " Risk: "
						+ String.valueOf(quickDirtyRisk(WeatherList.get(1)))
						+ "\n" + String.valueOf(WeatherList.get(2).getTime())
						+ " Risk: "
						+ String.valueOf(quickDirtyRisk(WeatherList.get(2)))
						+ "\n" + String.valueOf(WeatherList.get(3).getTime())
						+ " Risk: "
						+ String.valueOf(quickDirtyRisk(WeatherList.get(3))));
				switch (quickDirtyRisk(WeatherList.get(0))) {
				case 1:
					risk.setBackgroundColor(Color.parseColor("#00CED1"));
					break;
				case 2:
					risk.setBackgroundColor(Color.parseColor("#00FA9A"));
					break;
				case 3:
					risk.setBackgroundColor(Color.parseColor("#00FF00"));
					break;
				case 4:
					risk.setBackgroundColor(Color.parseColor("#7CFC00"));
					break;
				case 5:
					risk.setBackgroundColor(Color.parseColor("#9ACD32"));
					break;
				case 6:
					risk.setBackgroundColor(Color.parseColor("#FFFF00"));
					break;
				case 7:
					risk.setBackgroundColor(Color.parseColor("#FFA500"));
					break;
				case 8:
					risk.setBackgroundColor(Color.parseColor("#FF4500"));
					break;
				case 9:
					risk.setBackgroundColor(Color.parseColor("#FF0000"));
					break;
				case 10:
					risk.setBackgroundColor(Color.parseColor("#FF0000"));
					break;
				}
			}
		});
	}

	private int quickDirtyRisk(Weather oneDay) {
		RiskModel rm = new RiskModel();
		return rm.calculateRiskForProvidedDay(90, 60, 80, oneDay); // currently
																	// sets risk
																	// to higher
																	// for a day
																	// over 90
																	// degrees,
																	// below 60,
																	// or with a
																	// humidity
																	// over 80
	}

	private void setupConnection() {
		// Initialize the BluetoothChatService to perform bluetooth connections
		mUpdateButton = (Button) findViewById(R.id.updateValues);
		mUpdateButton.setOnClickListener(new OnClickListener() {
			private String message;

			public void onClick(final View view) {
				message = "t";
				sendMessage(message);
			}
		});
		mConnectionHandler = new BluetoothConnectionHandler(this, mHandler);
	}

	// Parse the string returned by Arduino
	// Grab return value after '='
	private String arduinoReturnParse(String read) throws IOException {
		// Turn into stream
		InputStream is = new ByteArrayInputStream(read.getBytes());
		char c = '\0';
		// Iterate through until '='
		while (is.available() > 0 && c != '=') {
			c = Character.toChars(is.read())[0];
		}
		// Skip the '='
		is.skip(1);
		String val = "";
		// Grab until end of number
		while (is.available() > 0 && c != ' ' && c != '\n' && c != '\r') {
			val += c;
			c = Character.toChars(is.read())[0];
		}

		return val;
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothConnectionHandler.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);
					break;
				case BluetoothConnectionHandler.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothConnectionHandler.STATE_LISTEN:
				case BluetoothConnectionHandler.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					break;
				default:
					break;
				}
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				String value = "";
				// Parse returned string for sensor value. Led by '='
				try {
					value = new String(arduinoReturnParse(readMessage));
				} catch (IOException e) {
				}
				tempCurr.setText(value);
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void sendMessage(final String message) {
		// Check that we're actually connected before trying anything
		if (mConnectionHandler.getState() != BluetoothConnectionHandler.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			final byte[] send = message.getBytes(Charset.forName("UTF-8"));
			mConnectionHandler.write(send);
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mConnectionHandler != null
				&& mConnectionHandler.getState() == BluetoothConnectionHandler.STATE_NONE) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			// Start the Bluetooth chat services
			mConnectionHandler.start();
		}

	}

	@Override
	public void onDestroy() {
		if (mConnectionHandler != null) {
			mConnectionHandler.stop();
		}
		super.onDestroy();
	}

	private void ensureDiscoverable() {
		if (Config.LOGD) {
			Log.d(TAG, "ensure discoverable");
		}
		if (mbtA.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			final Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CONNECT_DEVICE) {
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				final String address = data.getExtras().getString(
						DeviceListActivity.extraDeviceAddress);
				// Get the BLuetoothDevice object
				final BluetoothDevice device = mbtA.getRemoteDevice(address);
				// Attempt to connect to the device
				mConnectionHandler.connect(device);
			}
		} else if (requestCode == REQUEST_ENABLE_BT) {
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupConnection();
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, "Bluetooth Not Enabled",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		boolean isValid = false;
		if (item.getItemId() == R.id.scan) {
			// Launch the DeviceListActivity to see devices and do scan
			final Intent serverIntent = new Intent(this,
					DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			isValid = true;
		} else if (item.getItemId() == R.id.discoverable) {
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			isValid = true;
		}
		return isValid;
	}

}
