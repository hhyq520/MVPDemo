package com.ztd.mvpstandardpro_as.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 
 * @author Administrator
 * 
 */
public class SoundManager {
	private MediaPlayer player;
	private PlaySoundPool playSoundPool;


	private static SoundManager soundManager;

	public SoundManager(Context context) {
		playSoundPool = new PlaySoundPool(context);
	}

	public static SoundManager getInstance(Context context) {
		if (soundManager == null) {
			soundManager = new SoundManager(context);
		}
		return soundManager;
	}

	
	public void playSuccessSound() {
		playSoundPool.playSounds(PlaySoundPool.RIGHT, 0);
	}

	
	public void playFailureSound() {
		playSoundPool.playSounds(PlaySoundPool.WRONG, 0);

	}
	public void playqrCodeSound() {
		playSoundPool.playSounds(PlaySoundPool.QRCODE, 0);
	}

	public void releases() {
		if (player != null) {
			player.release();
		}
	}

}
