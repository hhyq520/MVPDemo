package com.ztd.mvpstandardpro_as.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipmentInfo {

	private static final String TAG = EquipmentInfo.class.getSimpleName();
	private static String mSn="";
	private static String imei = "";
	
	public static String getIMEI(Activity act){
		if("".equals(imei)){
			TelephonyManager mTm=(TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
//			imei =mTm.getDeviceId();
			return imei;
		} else{
			return imei;
		}
	}

	public static String getEquipmentSN(){
		if("".equals(mSn)){
			String []propertys = {"ro.boot.serialno", "ro.serialno"};
			for(String key:propertys){
				String s = getEquipmentSN(key);
				if(s!=null && "".equals(s)){
					mSn = s;
					Log.i(TAG, mSn);
					return mSn;
				}
			}
			return "";
		} else{
			return mSn;
		}
	}
	
	private static String getEquipmentSN(String key){
		
		if("".equals(mSn)){
			try {
				Method sysProperties = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
				return (String) sysProperties.invoke(null, key);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		} else{
			return mSn;
		}
	}
	
	/**
	 * 获取版本号
	 * @return
	 *
	 */
	public static String getVersionName(Context context) {
		PackageManager pm=context.getPackageManager();
		try {
			PackageInfo packgeInfo = pm.getPackageInfo(context.getPackageName(), 0);
		    String versionName=packgeInfo.versionName;
		    return versionName;
		
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	
		return "1.0";
	}
	/**
	 * 获取版本号
	 * @return
	 *
	 */
	public static int getVersionCode(Context context) {
		PackageManager pm=context.getPackageManager();
		try {
			PackageInfo packgeInfo = pm.getPackageInfo(context.getPackageName(), 0);
			int versionName=packgeInfo.versionCode;
			return versionName;
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	 public static boolean isCellphone(String str) {
		 Pattern pattern = Pattern.compile("1[0-9]{10}");
		 Matcher matcher = pattern.matcher(str); 
		 if (matcher.matches()) {
		 return true;
		 }else {
		 return false;
		 }  
	 }
}
