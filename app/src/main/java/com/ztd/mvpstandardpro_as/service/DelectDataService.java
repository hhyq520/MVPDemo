package com.ztd.mvpstandardpro_as.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zhitengda.gen.DaoSession;
import com.ztd.mvpstandardpro_as.db.DbManger;
import com.ztd.mvpstandardpro_as.utils.CommonUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DelectDataService extends IntentService {

	DaoSession daoSession;
	public DelectDataService() {
		super(DelectDataService.class.getSimpleName());
		daoSession= DbManger.getInstance(this).getDaoSession();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		DeleteBeforeDays();
	}

	
	@SuppressLint("SimpleDateFormat")
	protected void DeleteBeforeDays() {

		long s=System.currentTimeMillis()-13*60*60*1000;
		String date= CommonUtils.convertString(s);
//		List<TruckloadBean> list = daoSession.getTruckloadBeanDao().queryBuilder().
//				where(TruckloadBeanDao.Properties.UpLoadDate.between("1900-01-01 00:00:01", date)).list();
//		for (TruckloadBean item:list){
//			daoSession.getTruckloadBeanDao().delete(item);
//			daoSession.getMainTruckLoadDao().deleteAll();
//			daoSession.getSubTruckLoadDao().deleteAll();
//		}
	}
}
