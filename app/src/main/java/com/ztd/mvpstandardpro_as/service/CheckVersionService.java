package com.ztd.mvpstandardpro_as.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ztd.mvpstandardpro_as.BuildConfig;
import com.ztd.mvpstandardpro_as.Http;
import com.ztd.mvpstandardpro_as.HttpService;
import com.ztd.mvpstandardpro_as.ProApplication;
import com.ztd.mvpstandardpro_as.base.BaseHttpResult;
import com.ztd.mvpstandardpro_as.base.BaseObserver;
import com.ztd.mvpstandardpro_as.bean.VersionEntity;
import com.ztd.mvpstandardpro_as.exception.ApiException;
import com.ztd.mvpstandardpro_as.transformer.CommonTransformer;
import com.ztd.mvpstandardpro_as.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


public class CheckVersionService extends IntentService {

	private static final String  TAG = CheckVersionService.class.getSimpleName();
	public static String RECEIVER_CHECK_VERSION = "com.zhitengda.fengtong.checkversion";
	
	public CheckVersionService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		HttpService httpService= Http.getHttpService();
		final int fromSource = intent.getIntExtra("fromSource", 0);
		String version = BuildConfig.VERSION_NAME;
		httpService.checkVersion(version)
				.compose(CommonTransformer.<BaseHttpResult<VersionEntity>>compose())
				.subscribe(new BaseObserver<VersionEntity>(ProApplication.getAppContext()) {

					@Override
					protected void onError(ApiException e) {

					}

					@Override
					protected void onSuccess(VersionEntity versionEntity) {
						versionEntity.setFromSource(fromSource);
						EventBus.getDefault().post(versionEntity);
					}


					@Override
					protected void onFail(int status,String t) {
						if(fromSource!=0){
							ToastUtil.setToast("已是最新版！");
						}
					}

					@Override
					protected void onFinish() {

					}
				});
	}

}
