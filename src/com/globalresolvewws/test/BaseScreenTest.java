package com.globalresolvewws.test;

import com.globalresolvewws.R;
import com.globalresolvewws.R.id;
import com.globalresolvewws.BaseScreen;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseScreenTest extends
		ActivityInstrumentationTestCase2<BaseScreen> {

	private BaseScreen mActivity;
	private ImageView mImageView;
	private TextView mTextView;
	public static final boolean INITIAL_POSITION = false;

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
	}

	public void testPresetUp() {
		assertTrue(mImageView != null);
		assertTrue(mTextView != null);
	}

	public void testMainActivityUI() {
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
			}
		});
	}
}
