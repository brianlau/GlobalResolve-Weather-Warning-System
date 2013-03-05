package com.globalresolvewws;

import java.util.Set;
import com.globalresolvewws.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.bluetooth.*;

public class BaseScreen extends Activity {
	// MediaPlayer mp = new MediaPlayer();
	// AssetFileDescriptor siren;
	// boolean flag = false;
	ArrayAdapter mAA;
	BluetoothAdapter mbtA;
	private BluetoothSocket mmServerSocket;
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// Add the name and address to an array adapter to show in a
				// ListView
				mAA.add(device.getName() + "\n" + device.getAddress());
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Bluetooth adapter
		mbtA = BluetoothAdapter.getDefaultAdapter();
		if (mbtA != null) {
			if (!mbtA.isEnabled()) {
				Intent discoverableIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(
						BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
				startActivity(discoverableIntent);
			}
			Set<BluetoothDevice> pairedDevices = mbtA.getBondedDevices(); // Gets
																			// list
																			// of
																			// bonded
																			// devices
			// If there are paired devices
			if (pairedDevices.size() > 0) {
				// Loop through paired devices
				for (BluetoothDevice device : pairedDevices) {
					// Add the name and address to an array adapter to show in a
					// ListView
					mAA.add(device.getName() + "\n" + device.getAddress());
				}
			}
			if (mbtA.isDiscovering()) {
				mbtA.cancelDiscovery();
			}
			mbtA.startDiscovery();
			// Register the BroadcastReceiver
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mReceiver, filter); // Don't forget to unregister
													// during onDestroy
			mbtA.cancelDiscovery();
			AcceptThread AT = new AcceptThread(mmServerSocket, mbtA);
			AT.run();
			mmServerSocket = AT.getSocket();
			
		} else {
			Toast.makeText(this, "Bluetooth not supported with this device",
					Toast.LENGTH_LONG).show();
		}

		// Opens a database on the Android device
		DatabaseHandler db = new DatabaseHandler(this);

		// db.addWeather(new Weather("12", 65.3, 86.2, 100, 87, 50));

		final SimulateData sim = new SimulateData(); // Data Simulator
		final int[] imageArray = { R.drawable.sunny_icon, R.drawable.night_rain };// Image
																					// Array

		// siren = getResources().openRawResourceFd(R.raw.siren);

		final TextView textView = (TextView) findViewById(R.id.temp_curr);// Current
																			// Temperature
		final ImageView imageView = (ImageView) findViewById(R.id.imageView);// Image
																				// view
																				// for
																				// weather
		final Handler handler = new Handler();

		final Runnable runnable = new Runnable() {
			int i = 0;

			// Starts weather simulation
			public void run() {
				sim.TemperatureForecast(); // run dummyTemp
				// try {
				// mp.setDataSource(siren.getFileDescriptor(),
				// siren.getStartOffset(), siren.getLength());
				// mp.prepare();
				// } catch (IllegalArgumentException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IllegalStateException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				if (sim.CurrentTemperature() <= 30) { // sets to rain
					i = 1;
					// mp.start();

				} else if (sim.CurrentTemperature() > 30) { // sets to sun
					i = 0;
					// if (mp != null && mp.isPlaying()) {
					// mp.stop();
					// mp.reset();
					// }
				}
				imageView.setImageResource(imageArray[i]); // sets image
				textView.setText(Integer.toString(sim.CurrentTemperature())
						+ "F"); // sets temp
				handler.postDelayed(this, 5000); // for interval 5 sec delay
													// before switching
			}
		};
		handler.postDelayed(runnable, 2000); // for initial delay..
		Button btnMapView = (Button) findViewById(R.id.buttonMapView);

		btnMapView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mapScreen = new Intent(getApplicationContext(),
						MapScreen.class);
				startActivity(mapScreen);
				handler.removeCallbacks(runnable);
				finish();
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		// if(mp != null){
		// mp.release();
		// }

	}

	@Override
	public void onResume() {
		super.onResume();
		// if(mp == null)
		// initializeMediaPlayer();
	}

	// private void initializeMediaPlayer() {
	// mp = new MediaPlayer();
	//
	// }
	@Override
	public void onDestroy() {
		unregisterReceiver(mReceiver);
		if (mbtA.isDiscovering()) {
			mbtA.cancelDiscovery();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
