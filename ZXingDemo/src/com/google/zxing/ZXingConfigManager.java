/**
 * Project Name:ZXingDemo
 * File Name:OrientationManager.java
 * Package Name:com.example.zxingdemo
 * Date:2015-12-14…œŒÁ11:21:33
 *
 */

package com.google.zxing;

/**
 * ClassName: OrientationManager <br/>
 * Description: ∫· ˙∆¡…Ë÷√ . <br/>
 * date: 2015-12-14 …œŒÁ11:21:33 <br/>
 * 
 * @author Íª
 * @version
 * @since JDK 1.6
 */

public class ZXingConfigManager {

	private ZXingConfigManager() {
		super();
	}

	private static ZXingConfigManager instance = null;

	/**
	 * instance.
	 * 
	 * @return the instance
	 * @since JDK 1.6
	 */

	public static ZXingConfigManager getInstance() {

		if (null == instance)
			instance = new ZXingConfigManager();
		return instance;
	}

	private Orientation orientation = Orientation.PORTRAIT;

	public void setOrientation(Orientation orientation) {

		this.orientation = orientation;
	}

	public boolean isPortrait() {
		return Orientation.PORTRAIT == orientation;
	}

	public boolean isLanscape() {
		return Orientation.LANDSCAPE == orientation;
	}

	public enum Orientation {
		PORTRAIT, LANDSCAPE
	}

	private boolean showRedLine = false;

	/**
	 * showRedLine.
	 * 
	 * @param showRedLine
	 *            the showRedLine to set
	 * @since JDK 1.6
	 */

	public void setShowRedLine(boolean showRedLine) {

		this.showRedLine = showRedLine;
	}

	/**
	 * showRedLine.
	 * 
	 * @return the showRedLine
	 * @since JDK 1.6
	 */

	public boolean isShowRedLine() {

		return showRedLine;
	}

	private boolean playBeeSound = true;

	/**
	 * playBeeSound.
	 * 
	 * @param playBeeSound
	 *            the playBeeSound to set
	 * @since JDK 1.6
	 */

	public void setPlayBeeSound(boolean playBeeSound) {

		this.playBeeSound = playBeeSound;
	}

	/**
	 * playBeeSound.
	 * 
	 * @return the playBeeSound
	 * @since JDK 1.6
	 */

	public boolean isPlayBeeSound() {

		return playBeeSound;
	}
}
