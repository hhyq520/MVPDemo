package com.ztd.mvpstandardpro_as.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.zhitengda.gen.DaoSession;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.bean.VersionEntity;
import com.ztd.mvpstandardpro_as.bean.eventbus.ScanEnity;
import com.ztd.mvpstandardpro_as.db.DbManger;
import com.ztd.mvpstandardpro_as.mvp.IView;
import com.ztd.mvpstandardpro_as.mvpactivity.HomeActivity;
import com.ztd.mvpstandardpro_as.service.DownloadApkService;
import com.ztd.mvpstandardpro_as.utils.CommonUtils;
import com.ztd.mvpstandardpro_as.utils.DownloadListener;
import com.ztd.mvpstandardpro_as.utils.DownloadManager;
import com.ztd.mvpstandardpro_as.utils.LogUtils;
import com.ztd.mvpstandardpro_as.utils.SoundManager;
import com.ztd.mvpstandardpro_as.widget.BaseDialog;
import com.ztd.mvpstandardpro_as.widget.ProgressLoadingDialog;
import com.ztd.mvpstandardpro_as.widget.RotateTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.ButterKnife;
/**
 * Created by GaoSheng on 2016/9/13.
 */

public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity implements
        IView {
    protected View view;
    public static int SCAN_QUEST_CODE=0x00;
    protected P mPresenter;
    public SoundManager sm;
    private ProgressLoadingDialog mProgressDialog;
    public DaoSession daoSession;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        EventBus.getDefault().register(this);
        daoSession= DbManger.getInstance(this).getDaoSession();
        ButterKnife.bind(this);
        mPresenter = loadPresenter();
        sm= SoundManager.getInstance(this);
        initCommonData();
        initView();
        initListener();
        initData();

    }

    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void receiveScancode(String billcode);
    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, "");
    }

    @Override
    public void showLoading(String msg) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, msg);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        Toast.makeText(this, contentId, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass(), str);
    }



    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceiveBillcodeEvent(ScanEnity event) {
        receiveScancode(event.getCode());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceiveVersionEvent(VersionEntity event) {
        showNoticeDialog(event);
    }


    private Dialog versionDialog;
    public void showNoticeDialog(final VersionEntity ve)
    {
//        Intent downIntent = new Intent(BaseActivity.this, DownloadApkService.class);
//                downIntent.putExtra("url", ve.getUrl());
//                startService(downIntent);
        versionDialog = new BaseDialog(this, R.style.BaseDialog, R.layout.version_dialog_layout);
        RotateTextView rotateTextView= (RotateTextView) versionDialog.findViewById(R.id.tv_versionCode);
        Button btnUpdate = (Button) versionDialog.findViewById(R.id.tv_update);
        final ContentLoadingProgressBar bar=(ContentLoadingProgressBar) versionDialog.findViewById(R.id.pb);
        ImageView tvCancel = (ImageView) versionDialog.findViewById(R.id.tv_cancel);
        rotateTextView.setText(ve.getVersion());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.downloadAPK(BaseActivity.this, ve.getUrl(), Environment.getExternalStorageDirectory() + "/AllenVersionPath", BaseActivity.class, new DownloadListener() {
                    @Override
                    public void onCheckerDownloading(int progress) {
                        bar.setProgress(progress);
                    }

                    @Override
                    public void onCheckerDownloadSuccess(File file) {
                        DownloadManager.installApk(BaseActivity.this, file);
                    }

                    @Override
                    public void onCheckerDownloadFail() {

                    }
                });
            }
        });
        if (!isFinishing() && versionDialog != null && !versionDialog.isShowing())
        {
            versionDialog.show();
        }

    }

    protected void zxing(){
        Intent intent=new Intent(BaseActivity.this,CaptureActivity.class);
        startActivityForResult(intent,SCAN_QUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        if(versionDialog!=null){
            versionDialog.dismiss();
            versionDialog=null;
        }
        EventBus.getDefault().unregister(this);
    }

}

