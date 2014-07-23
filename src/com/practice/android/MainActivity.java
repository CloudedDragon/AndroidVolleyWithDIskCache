package com.practice.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	private ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		IntentFilter mStatusIntentFilter = new IntentFilter(
				Constants.BROADCAST_ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(
				myBroadCastReceiver, mStatusIntentFilter);
		Log.e("oncreate", "oncreate()");
		bar = (ProgressBar) this.findViewById(R.id.progressBar);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				bar.setVisibility(View.VISIBLE);

				Intent intent = new Intent(MainActivity.this, TestService.class);
				startService(intent);
			}
		});
	}

	private BroadcastReceiver myBroadCastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("Inside BroadCastReceiver", "Hi I am in broadCast Receiver");
			bar.setVisibility(View.GONE);
		}
	};
}
