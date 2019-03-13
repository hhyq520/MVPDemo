package com.ztd.mvpstandardpro_as.service;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.utils.DownloadListener;
import com.ztd.mvpstandardpro_as.utils.DownloadManager;

import java.io.File;

public class DownloadApkService extends IntentService {
	private static final String TAG = DownloadApkService.class.getSimpleName();
	private static final String savePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/mvpwine";
	private static final String saveFileName = savePath + "/mvpwine.apk";
	public DownloadApkService() {
		super(TAG);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onHandleIntent(Intent intent) {
		String apkUrl = intent.getStringExtra("url");
		DownloadManager.downloadAPK(this, apkUrl, Environment.getExternalStorageDirectory() + "/AllenVersionPath", BaseActivity.class, new DownloadListener() {
			@Override
			public void onCheckerDownloading(int progress) {

			}

			@Override
			public void onCheckerDownloadSuccess(File file) {
				DownloadManager.installApk(DownloadApkService.this, file);
			}

			@Override
			public void onCheckerDownloadFail() {

			}
		});
	}
}
