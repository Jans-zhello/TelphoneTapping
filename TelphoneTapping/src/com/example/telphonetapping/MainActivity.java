package com.example.telphonetapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// 开始服务
	public void start(View view) {
		i = new Intent(MainActivity.this, PhoneListenerService.class);
		startService(i);
	}

	// 结束服务
	public void stop(View view) {
        stopService(i);
	}
}
