package com.globalresolvewws.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.globalresolvewws.MapScreen;
import com.globalresolvewws.BaseScreen;

import android.bluetooth.BluetoothAdapter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BaseScreen.class, MapScreen.class, BluetoothAdapter.class})
public class BaseScreenTest extends
		ActivityInstrumentationTestCase2<BaseScreen> {

	private BaseScreen mActivity = null;
	private ImageView mImageView = null;
	private TextView mTextView = null;
	private Button mMapViewButton = null;

	public BaseScreenTest() {
		super("com.globalresovlewws", BaseScreen.class);
	}

	@Before
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		mActivity = getActivity();
		mImageView = (ImageView) mActivity
				.findViewById(com.globalresolvewws.R.id.imageView);
		mTextView = (TextView) mActivity
				.findViewById(com.globalresolvewws.R.id.temp_curr);
		mMapViewButton = (Button) mActivity.findViewById(com.globalresolvewws.R.id.buttonMapView);
		assertNotNull("Should Not Be Null",mActivity);
		assertNotNull("Should not be null",mImageView);
		assertNotNull("Should not be null",mTextView);
		assertNotNull("Should not be null",mMapViewButton);
	}
	
	@UiThreadTest
	public void testMainActivityUI() {
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mMapViewButton.requestFocus();

			}
		});
	}
}
