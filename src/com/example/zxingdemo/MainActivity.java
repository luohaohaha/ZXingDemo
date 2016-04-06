package com.example.zxingdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.DecodeManager;
import com.google.zxing.Result;
import com.google.zxing.DecodeManager.IDecodeResultListener;
import com.google.zxing.ZXingConfigManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DecodeManager.getInstance().attchToActivity(this, resultListener);
		
	}

	private IDecodeResultListener resultListener = new IDecodeResultListener() {

		@Override
		public void handleDecode(Result<?> obj, Bitmap barcode) {

			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "result===========" + obj.getText().toString(), Toast.LENGTH_SHORT)
					.show();
			canScan=false;
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
	
	private boolean canScan=true;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(canScan){
				return super.onKeyDown(keyCode, event);
			}
			DecodeManager.getInstance().restartPreviewAfterDelay(0L);
			canScan=true;
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
