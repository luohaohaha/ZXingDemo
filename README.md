####ZXing简化版 基于zxing4.7.4修改 主要简化使用 支持横竖屏 可自己修改

需要横竖屏切换的可自行在AndroidManifest.xml设置Activity的screenOrientation属性

横竖屏切换需要的改动的代码已经加上注释

由于摄像头支持的分辨率不一样 建议扫码界面全屏避免拉伸

####使用

```
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DecodeManager.getInstance().attachToActivity(this, IDecodeResultListener);
		
	}

  protected void onResume() {
		DecodeManager.getInstance().onResume();
		super.onResume();
	}
	
	protected void onPause() {
		DecodeManager.getInstance().onPause();
		super.onPause();

	}

	protected void onDestroy() {
		DecodeManager.getInstance().onDestroy();
		super.onDestroy();

	}
	
	重新预览
	DecodeManager.getInstance().restartPreviewAfterDelay(0L);
```
