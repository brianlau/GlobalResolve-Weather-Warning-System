package com.example.globalresovlewws.Tests;

import com.example.globalresovlewws.BaseScreen;
import com.example.globalresovlewws.R;
import com.example.globalresovlewws.R.id;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseScreenTest extends
		ActivityInstrumentationTestCase2<BaseScreen> {

	private BaseScreen mActivity;
	private Button myButton;
	private ImageView mImageView;
	private TextView mTextView;
	public static final boolean INITIAL_POSITION = false;

	public BaseScreenTest() {
		super("com.example.globalresovlewws", BaseScreen.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		mActivity = getActivity();
		myButton = (Button) mActivity
				.findViewById(com.example.globalresovlewws.R.id.button1);
		mImageView = (ImageView) mActivity
				.findViewById(com.example.globalresovlewws.R.id.imageView);
		mTextView = (TextView) mActivity
				.findViewById(com.example.globalresovlewws.R.id.temp_curr);
	}

	public void testPresetUp() {
		assertTrue(myButton != null);
		assertTrue(mImageView != null);
		assertTrue(mTextView != null);
	}

	public void testMainActivityUI() {
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				myButton.requestFocus();
				myButton.setSelected(INITIAL_POSITION);
				;
			}
		});
	}
}
