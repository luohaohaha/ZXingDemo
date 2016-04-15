package com.example.zxingdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.DecodeManager;
import com.google.zxing.Result;
import com.google.zxing.DecodeManager.IDecodeResultListener;
import com.google.zxing.ZXingConfigManager;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DecodeManager.getInstance().attachToActivity(this, resultListener);

	}

	private IDecodeResultListener resultListener = new IDecodeResultListener() {

		@Override
		public void handleDecode(Result<?> obj, Bitmap barcode) {

			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "result===========" + obj.getText().toString(), Toast.LENGTH_SHORT)
					.show();
			canScan = false;
		}
	};

	@Override
	protected void onResume() {

		// TODO Auto-generated method stub
		DecodeManager.getInstance().onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {

		// TODO Auto-generated method stub
		DecodeManager.getInstance().onPause();
		super.onPause();

	}

	@Override
	protected void onDestroy() {

		// TODO Auto-generated method stub
		DecodeManager.getInstance().onDestroy();
		super.onDestroy();

	}

	private boolean canScan = true;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (canScan) {
				return super.onKeyDown(keyCode, event);
			}
			DecodeManager.getInstance().restartPreviewAfterDelay(0L);
			canScan = true;
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	
	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		if (DecodeManager.getInstance().isFlashLightOpen()) {
			((Button)v).setText("Open FlashLight");
			DecodeManager.getInstance().closeFlashLight();
			return;
		}
		DecodeManager.getInstance().openFlashLight();
		((Button)v).setText("Close FlashLight");
	}

}
