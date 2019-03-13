package com.ztd.mvpstandardpro_as.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ztd.mvpstandardpro_as.widget.ProgressLoadingDialog;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class CommonUtils {
    public static ProgressLoadingDialog showLoadingDialog(Context context, String message) {
        ProgressLoadingDialog progressDialog = new ProgressLoadingDialog(context);
        progressDialog.show();
        if (TextUtils.isEmpty(message)) {
//            progressDialog.setMessage(context.getString(R.string.loding));
            progressDialog.setMessage("正在加载中...");
        } else {
            progressDialog.setMessage(message);
        }
        return progressDialog;
    }


    public static String convertString(long timestamps){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamps);
        return sdf.format(date);
    }

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        // 不转换没有 @Expose 注解的字段
//		builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        builder.registerTypeHierarchyAdapter(byte[].class,
                new ByteArrayTypeAdapter()).create();
        Gson gson = builder.create();
        return gson;
    }
}
