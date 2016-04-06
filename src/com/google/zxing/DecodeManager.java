/**
 * Project Name:ZXingDemo
 * File Name:DecodeManager.java
 * Package Name:com.google.zxing
 * Date:2015-12-15下午3:33:44
 *
 * Copyright (c) 2015, luohaohaha@gmail.com All Rights Reserved.
 */

package com.google.zxing;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.example.zxingdemo.R;
import com.google.zxing.ActivityHandler.IScanListener;
import com.google.zxing.ZXingConfigManager.Orientation;
import com.google.zxing.camera.CameraManager;
import com.google.zxing.view.ViewfinderResultPointCallback;
import com.google.zxing.view.ViewfinderView;

/**
 * ClassName: DecodeManager <br/>
 * Description: TODO . <br/>
 * date: 2015-12-15 下午3:33:44 <br/>
 * 
 * @author �?
 * @version
 * @since JDK 1.6
 */

public class DecodeManager implements Callback {

	private static DecodeManager instance = null;
	private Activity currAct;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;
	private boolean hasSurface;
	private ActivityHandler handler;
	private Collection<BarcodeFormat> decodeFormats;
	private String characterSet;// 编码类型
	private Map<DecodeHintType, ?> decodeHints;
	private ViewfinderView viewfinderView;
	private CameraManager cameraManager;

	private IDecodeResultListener resultListener = null;

	private DecodeManager() {
		super();
	}

	/**
	 * instance.
	 * 
	 * @return the instance
	 * @since JDK 1.6
	 */

	public static DecodeManager getInstance() {

		if (null == instance)
			instance = new DecodeManager();
		return instance;
	}

	public void attchToActivity(Activity act, IDecodeResultListener resultListener) {
		this.currAct = act;
		this.resultListener = resultListener;
		onCreate();
	}

	private void onCreate() {
		if (currAct.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			currAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			ZXingConfigManager.getInstance().setOrientation(Orientation.PORTRAIT);
		} else {
			currAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			ZXingConfigManager.getInstance().setOrientation(Orientation.LANDSCAPE);
		}
		initOnCreat();

	}

	private void initOnCreat() {
		hasSurface = false;
		inactivityTimer = new InactivityTimer(currAct);
		beepManager = new BeepManager(currAct);
	}

	public void onResume() {

		initOnResume();
	}

	private void initOnResume() {

		handler = null;
		decodeFormats = null;
		characterSet = null;

		cameraManager = new CameraManager(currAct);
		viewfinderView = (ViewfinderView) currAct.findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);

		if (null != viewfinderView) {
			cameraManager.setViewfinderView(viewfinderView);
		}
		beepManager.updatePrefs();

		inactivityTimer.onResume();

		SurfaceView surfaceView = (SurfaceView) currAct.findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			// The activity was paused but not stopped, so the surface still
			// exists. Therefore
			// surfaceCreated() won't be called, so init the camera here.
			initCamera(surfaceHolder);
		} else {
			// Install the callback and wait for surfaceCreated() to init the
			// camera.
			surfaceHolder.addCallback(this);
		}
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Creating the handler starts the preview, which can also throw a
		// RuntimeException.
		if (handler == null) {
			handler = new ActivityHandler(currAct, decodeFormats, decodeHints, characterSet, cameraManager,
					mScanListener);
		}
	}

	private IScanListener mScanListener = new IScanListener() {

		@Override
		public void handleDecode(Result<?> obj, Bitmap barcode) {

			// TODO Auto-generated method stub
			if (ZXingConfigManager.getInstance().isPlayBeeSound()) {
				beepManager.playBeepSoundAndVibrate();
			}
			if (null == resultListener)
				return;
			resultListener.handleDecode(obj, barcode);
		}

		@Override
		public ViewfinderResultPointCallback getViewfinderResultPointCallback() {

			return new ViewfinderResultPointCallback(viewfinderView);

		}

		@Override
		public void drawViewfinder() {

			viewfinderView.drawViewfinder();
		}
	};

	public void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		beepManager.close();
		cameraManager.closeDriver();
		// historyManager = null; // Keep for onActivityResult
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) currAct.findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
	}

	public void onDestroy() {
		inactivityTimer.shutdown();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}

	/**
	 * TODO �?单描述该方法的实现功能（可�?�）.
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 * @param holder
	 */

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		// TODO Auto-generated method stub
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	/**
	 * TODO �?单描述该方法的实现功能（可�?�）.
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder,
	 *      int, int, int)
	 * @param holder
	 * @param format
	 * @param width
	 * @param height
	 */

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		// TODO Auto-generated method stub

	}

	/**
	 * TODO �?单描述该方法的实现功能（可�?�）.
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 * @param holder
	 */

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		// TODO Auto-generated method stub
		hasSurface = false;
	}

	public interface IDecodeResultListener {

		void handleDecode(Result<?> obj, Bitmap barcode);
	}
}
