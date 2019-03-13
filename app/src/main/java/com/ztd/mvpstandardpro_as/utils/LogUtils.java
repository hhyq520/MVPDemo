package com.ztd.mvpstandardpro_as.utils;

import android.util.Log;
import com.ztd.mvpstandardpro_as.BuildConfig;


/**
 * Created by gaosheng on 2016/7/29.
 */

public class LogUtils {

    public static final boolean isDebug = BuildConfig.DEBUG;

    /**
     * 打印一个debug等级的 log
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d("zs_" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e("zs_" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(Class cls, String msg) {
        if (isDebug) {
            Log.e("zs_" + cls.getSimpleName(), msg);
        }
    }


}
