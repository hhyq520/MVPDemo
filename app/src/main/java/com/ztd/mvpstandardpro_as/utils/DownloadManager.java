package com.ztd.mvpstandardpro_as.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.content.pm.PackageInfo;
import com.ztd.mvpstandardpro_as.BuildConfig;
import com.ztd.mvpstandardpro_as.R;

import java.io.File;


/**
 * Created by allenliu on 2017/8/16.
 */

public class DownloadManager {
    private static int lastProgress = 0;
    private static DownloadListener listener;
    private static Context mContext;
    private static Class<?> mcls;
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int progress = msg.arg2;
                    int totalSize = msg.arg1;
                    Log.e("pppppppp", totalSize + "/totalSize");
                    Log.e("aaaaaaaa", progress + "/progress");
                    listener.onCheckerDownloading((int) (((double) (progress * 1.00) / totalSize) * 100));
                    progress = (int) (((double) (progress * 1.00) / totalSize) * 100);
                    if (progress - lastProgress >= 5) {
                        lastProgress = progress;
                    }
                    break;
                case 1:
                    int status = msg.arg1;
                    String targetPath = msg.obj.toString();
                    if (status == 0 || status == 3) {
                        File file = new File(targetPath);
                        listener.onCheckerDownloadSuccess(file);
                    } else if (status != 2) {
                        Log.e("version","file download failed");
                        listener.onCheckerDownloadFail();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public static void downloadAPK(Context context, String url, String filedir, Class<?> cls, DownloadListener mlistener) {
        if (url == null || url.isEmpty()) {
            return;
        }
        mcls=cls;
        mContext = context;
        listener = mlistener;
        lastProgress = 0;
        DownloadUtils.getInstance().downloadAppAsync(context,
                url,filedir,
                context.getString(R.string.versionchecklib_download_apkname, context.getPackageName() + BuildConfig.VERSION_NAME),
                "1", null, true, null, new DownloadUtils.DownloadListener() {
                    @Override
                    public void onProgress(long totalSize, long progress, String url, String downloadId, Object object, String targetPath, String tempPath, String ext) {
                        Message message = handler.obtainMessage(0);
                        message.arg1 = (int) totalSize;
                        message.arg2 = (int) progress;
                        message.sendToTarget();
                    }

                    @Override
                    public void onFinish(int status, String url, String downloadId, Object object, String targetPath, String tempPath, String error, Throwable throwable, String ext) {
                        Message message = handler.obtainMessage(1);
                        message.arg1 = status;
                        message.obj = targetPath;
                        message.sendToTarget();
                    }
                });
    }



    public static boolean checkAPKIsExists(Context context, String downloadPath) {
        File file = new File(downloadPath);
        boolean result = false;
        if (file.exists()) {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(downloadPath,
                        PackageManager.GET_ACTIVITIES);
                //判断安装包存在并且包名一样并且版本号不一样
                Log.e("version","本地安装包版本号：" + info.versionCode + "\n 当前app版本号：" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
                if (info != null && context.getPackageName().equals(info.packageName) && context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode != info.versionCode) {
                    result = true;
                }
            } catch (Exception e) {
                result = false;
            }
        }
        return result;
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            uri= VersionFileProvider.getUriForFile(context,context.getPackageName()+".versionProvider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            uri= Uri.fromFile(file);
        }
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
