package com.ztd.mvpstandardpro_as.utils;

import android.content.Context;
import android.os.SystemClock;
import java.io.RandomAccessFile;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Network request tool
 */
public class DownloadUtils {
	private final static int CONN_TIMEOUT = 1000 * 15;
	private static DownloadUtils manager = new DownloadUtils();

	private DownloadUtils(){}
	public static DownloadUtils getInstance() {
		if (manager == null) {
			manager = new DownloadUtils();
		}
		return manager;
	}
	
	private ArrayList<String> downloadingList = new ArrayList<String>();
	/**
	 * Download App
	 * @param context
	 * @param url Download address
	 * @param downloadListener Download the callback listener
	 * @param isNeedProgress Whether to need to download progress callback
	 */
	public void downloadAppAsync(final Context context, final String url, final String fileDir, final String fileName, final String downloadId, final Object object, final boolean isNeedProgress, final String ext, final DownloadListener downloadListener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				downloadAppSync(context, url, fileDir, fileName, downloadId, object, isNeedProgress, ext, downloadListener);
			}
		}).start();
	}
	
	public void downloadAppSync(final Context context, final String url, final String fileDir, final String fileName, final String downloadId, final Object object, final boolean isNeedProgress, final String ext, final DownloadListener downloadListener){
		
		if(context!=null && !isNetworkAvailable(context)){
			if(downloadListener!=null){
				downloadListener.onFinish(DownloadStatus.STATUS_NOT_NETWORK, url, downloadId, object, "", "", "There is no network.", null, ext);
			}
			return;
		}
		
		if(downloadingList!=null && downloadingList.size()>0){
			if(downloadingList.contains(fileName)){
				if(downloadListener!=null){
					downloadListener.onFinish(DownloadStatus.STATUS_DOWNLOADING, url, downloadId, object, "", "", "The file is downloading.", null, ext);
				}
				return;
			}
		}
		downloadingList.add(fileName);
		int times = 3;
		while(times > 0){ // Try again failed more than two times
			RandomAccessFile raf = null;
			InputStream is = null;
			String targetPath = "", tempPath = "";
			HttpURLConnection conn = null;
			try {
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				File tmp = null;
				File apk = null;
				tmp = new File(dir.getAbsolutePath(), fileName + ".temp");
				apk = new File(dir.getAbsolutePath(), fileName);
				targetPath = apk.getAbsolutePath();
				tempPath = tmp.getAbsolutePath();
				if (apk.exists()) {
					times = 0;
					downloadingList.remove(fileName);
					if(downloadListener!=null){
						downloadListener.onFinish(DownloadStatus.STATUS_FILE_EXISTS, url, downloadId, object, targetPath, tempPath, "File already exists", null, ext);
					}
					return;
				}
				URL urls = new URL(url);
				conn = (HttpURLConnection) urls.openConnection();
				conn.setConnectTimeout(CONN_TIMEOUT);
				conn.setRequestMethod("GET");
				long position = 0;
				if (tmp.exists()) {
					position = tmp.length();
					if (position > 0)
						conn.addRequestProperty("Range", String.format("bytes=%s-", position));
				} else {
					tmp.createNewFile();
				}
				raf = new RandomAccessFile(tmp, "rwd");
				int statusCode = conn.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_PARTIAL) {
					long totalSize = conn.getContentLength() + position;
					is = conn.getInputStream();
					byte[] bytes = new byte[1024];
					int len = -1;
					while ((len = is.read(bytes)) != -1) {
						raf.seek(position);
						position += len;
						raf.write(bytes, 0, len);
						
						if(isNeedProgress && downloadListener!=null){
							downloadListener.onProgress(totalSize, position, url, downloadId, object, targetPath, tempPath, ext);
						}
					}
					is.close();
					is = null;
					raf.close();
					raf = null;
					bytes = null;
					if (tmp.renameTo(apk)) {
//						tmp.delete();
					}
					times = 0;
					if(conn!=null){
						conn.disconnect();
						conn = null;
					}
					downloadingList.remove(fileName);
					if(downloadListener!=null){
						downloadListener.onFinish(DownloadStatus.STATUS_SUCCESS, url, downloadId, object, targetPath, tempPath, "Download successful.", null, ext);
					}
				} else {
					try {
						if(raf!=null){
							raf.close();
							raf = null;
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					times = 0;
					if(conn!=null){
						conn.disconnect();
						conn = null;
					}
					downloadingList.remove(fileName);
					if(downloadListener!=null){
						downloadListener.onFinish(DownloadStatus.STATUS_SERVER_ERROR, url, downloadId, object, targetPath, tempPath, "Server access is abnormal, statusCode is " + statusCode, null, ext);
					}
				}
			} catch (Exception e) {
				try {
					if(is!=null){
						is.close();
						is = null;
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				try {
					if(raf!=null){
						raf.close();
						raf = null;
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				if(conn!=null){
					conn.disconnect();
					conn = null;
				}
				times--;
				if(times > 0){
					SystemClock.sleep(3000);
					continue;
				}else{
					downloadingList.remove(fileName);
					if(downloadListener!=null){
						if(e instanceof IOException){
							downloadListener.onFinish(DownloadStatus.STATUS_WRITE_ERROR, url, downloadId, object, targetPath, tempPath, e.getMessage(), e, ext);
						}else{
							downloadListener.onFinish(DownloadStatus.STATUS_FAILED, url, downloadId, object, targetPath, tempPath, e.getMessage(), e, ext);
						}
					}
				}
			}
		}
	}
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}

	public interface DownloadListener {

		/**
		 * Download progress callback
		 * @param totalSize File total size
		 * @param progress The current download size
		 */
		public void onProgress(long totalSize, long progress, String url, String downloadId, Object object, String targetPath, String tempPath, String ext);
		/**
		 * Download the back end
		 * @param status Download status
		 * @param url Download url address
		 * @param targetPath Download the file path
		 * @param tempPath Temporary path to download files
		 * @param error Error content
		 * @param throwable Exception information
		 */
		public void onFinish(int status, String url, String downloadId, Object object, String targetPath, String tempPath, String error, Throwable throwable, String ext);

	}
}