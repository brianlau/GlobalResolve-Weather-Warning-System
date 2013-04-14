package com.globalresolvewws.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.globalresolvewws.BluetoothConnectionHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.test.AndroidTestCase;

public class BluetoothConnectionHandlerTest extends AndroidTestCase {

	private BluetoothConnectionHandler mConnectionHandler;
	private Handler mHandler;
	private Context context;
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		context = getContext();
		mConnectionHandler = new BluetoothConnectionHandler(context, mHandler);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					switch (msg.arg1) {
					case BluetoothConnectionHandler.STATE_CONNECTED:
						break;
					case BluetoothConnectionHandler.STATE_CONNECTING:
						break;
					case BluetoothConnectionHandler.STATE_LISTEN:
					case BluetoothConnectionHandler.STATE_NONE:
						break;
					}
					break;
				case 4:
					break;
				}
			}
		};
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test() {
		assertNotNull(mConnectionHandler);
		assertNotNull(mHandler);
		assertNotNull(context);
	}

}
