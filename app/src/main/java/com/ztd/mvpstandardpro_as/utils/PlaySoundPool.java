package com.ztd.mvpstandardpro_as.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import com.ztd.mvpstandardpro_as.R;

import java.util.HashMap;


/**
 * @author noone
 */
public final class PlaySoundPool {
	
	public static final String RIGHT = "right";
	public static final String WRONG = "wrong";
	public static final String ERROR = "error";
	public static final String QRCODE = "qrcode";
	
	private Context activity;
	HashMap<String,Integer> soundMap = null;
	SoundPool soundPool = null;
	public PlaySoundPool(Context activity) {
		this.activity = activity;
		initDatas();
	}

	@SuppressLint("UseSparseArrays")
	public void initDatas(){
		soundPool = new SoundPool(0,AudioManager.STREAM_MUSIC,0);
		soundMap = new HashMap<String, Integer>();
		soundMap.put(RIGHT, soundPool.load(activity, R.raw.scanok, 1));
		soundMap.put(WRONG, soundPool.load(activity, R.raw.scanrequired, 1));
		soundMap.put(ERROR, soundPool.load(activity, R.raw.error, 1));
		soundMap.put(QRCODE, soundPool.load(activity, R.raw.beep, 1));
		
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				LogUtils.e(getClass(), "sampleId:"+sampleId);
			}
		});
	}
	
	@SuppressWarnings("static-access")
	public void playSounds(String sound, int number) {
		
		AudioManager am = (AudioManager) activity.getSystemService(activity.AUDIO_SERVICE);
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
		soundPool.play(soundMap.get(sound), volumnRatio, volumnRatio, 1, number, 1);
	}
}
