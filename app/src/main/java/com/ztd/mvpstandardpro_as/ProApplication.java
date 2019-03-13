package com.ztd.mvpstandardpro_as;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.caimuhao.rxpicker.RxPicker;
import com.ztd.mvpstandardpro_as.service.DelectDataService;
import com.ztd.mvpstandardpro_as.service.KailiScanReceiver;
import com.ztd.mvpstandardpro_as.service.XiaobangScanReceiver;
import com.ztd.mvpstandardpro_as.widget.GlideImageLoader;


/**
 * Created by GaoSheng on 2016/9/13.
 * 应用,主要用来做一下初始化的操作
 */

public class ProApplication extends Application {
    private static Context mContext;
    public static int[] userFunctions = new int[5];
    private KailiScanReceiver receiver;
    private XiaobangScanReceiver receiverX;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerKailiScanReceiver();
        registerXiaobangScanReceiver();
        Intent intentData=new Intent(this,DelectDataService.class);
        startService(intentData);
        RxPicker.init(new GlideImageLoader());
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getAppContext() {
        return mContext;
    }

    public void registerKailiScanReceiver(){
        IntentFilter scanfliter = new IntentFilter(KailiScanReceiver.ACTION_SCAN_RECEIVER);
        if(receiver==null) {
            receiver = new KailiScanReceiver();
            registerReceiver(receiver, scanfliter);
        }
    }

    public void registerXiaobangScanReceiver(){
        IntentFilter scanfliter = new IntentFilter(XiaobangScanReceiver.SCN_CUST_ACTION_SCODE);
        if(receiverX==null) {
            receiverX = new XiaobangScanReceiver();
            registerReceiver(receiverX, scanfliter);
        }
    }
}
