package com.globalresolvewws.test;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static org.easymock.EasyMock.expect;

import com.globalresolvewws.R;
import com.globalresolvewws.R.id;
import com.globalresolvewws.BaseScreen;

import android.bluetooth.BluetoothDevice;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BluetoothDevice.class})
public class BaseScreenTest extends
		ActivityInstrumentationTestCase2<BaseScreen> {

	private BaseScreen mActivity;
	private ImageView mImageView;
	private TextView mTextView;
	private Button mMapViewButton;

	public BaseScreenTest() {
		super("com.globalresovlewws", BaseScreen.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		mActivity = getActivity();
		mImageView = (ImageView) mActivity
				.findViewById(com.globalresolvewws.R.id.imageView);
		mTextView = (TextView) mActivity
				.findViewById(com.globalresolvewws.R.id.temp_curr);
		mMapViewButton = (Button) mActivity.findViewById(com.globalresolvewws.R.id.map);
	}

	public void testPresetUp() {
		assertTrue(mImageView != null);
		assertTrue(mTextView != null);
		assertTrue(mMapViewButton != null);
	}

	public void testMainActivityUI() {
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
			}
		});
	}
}
