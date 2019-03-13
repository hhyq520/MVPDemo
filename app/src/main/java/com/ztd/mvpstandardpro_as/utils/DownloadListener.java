package com.ztd.mvpstandardpro_as.utils;

import java.io.File;

/**
 * Created by allenliu on 2017/8/16.
 */

public interface DownloadListener {
    void onCheckerDownloading(int progress);
    void onCheckerDownloadSuccess(File file);
    void onCheckerDownloadFail();
}
