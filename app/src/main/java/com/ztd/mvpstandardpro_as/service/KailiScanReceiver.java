package com.ztd.mvpstandardpro_as.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.ztd.mvpstandardpro_as.bean.eventbus.ScanEnity;
import com.ztd.mvpstandardpro_as.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/5/23.
 */

public class KailiScanReceiver extends BroadcastReceiver {
    // 凯立枪扫描广播
    public static final String ACTION_SCAN_RECEIVER = "com.android.receive_scan_action";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_SCAN_RECEIVER.equals(intent.getAction())){
            String dataStr = intent.getStringExtra("data");
//            LogUtils.e(KailiScanReceiver.class, "扫描收到的内容:"+dataStr);
            ScanEnity scanEnity=new ScanEnity();
            scanEnity.setCode(dataStr);
            EventBus.getDefault().post(scanEnity);
        }
    }

}
