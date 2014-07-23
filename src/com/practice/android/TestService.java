package com.practice.android;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class TestService extends IntentService {
	public static final String TAG = TestService.class.getSimpleName();
	private String url;

	public TestService() {
		super("MyService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		VolleySingleTon volleySingleTon = VolleySingleTon.getInstance(this);
		volleySingleTon.addJsonArrayRequest("");
		volleySingleTon.getJsonArray();
		Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
		LocalBroadcastManager.getInstance(TestService.this).sendBroadcast(
				localIntent);
	}
}
