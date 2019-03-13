package com.ztd.mvpstandardpro_as.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ztd.mvpstandardpro_as.bean.eventbus.ScanEnity;
import com.ztd.mvpstandardpro_as.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/5/23.
 */

public class XiaobangScanReceiver extends BroadcastReceiver {
    //// 销邦把枪
     public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(SCN_CUST_ACTION_SCODE.equals(intent.getAction())){
            String message = intent.getStringExtra("scannerdata");
//            LogUtils.e(XiaobangScanReceiver.class, "扫描收到的内容:"+message);
            ScanEnity scanEnity=new ScanEnity();
            scanEnity.setCode(message);
            EventBus.getDefault().post(scanEnity);
        }
    }

}
