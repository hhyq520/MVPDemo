package com.ztd.mvpstandardpro_as.mvpactivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitengda.gen.DaoSession;
import com.ztd.mvpstandardpro_as.BuildConfig;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.annotation.LayoutId;
import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.contract.LoginContract;
import com.ztd.mvpstandardpro_as.presenter.LoginPresenter;
import com.ztd.mvpstandardpro_as.service.CheckVersionService;
import com.ztd.mvpstandardpro_as.utils.LogUtils;
import com.ztd.mvpstandardpro_as.utils.SpUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/8.
 */
@LayoutId(id = R.layout.activity_login_main)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @Bind(R.id.edit_site_code)
    EditText editSiteCode;
    @Bind(R.id.edit_employ_code)
    EditText editEmployCode;
    @Bind(R.id.edit_password)
    EditText editPassword;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMERA"};
    @Override
    public void loginSuccess(final LoginBean str) {
        SpUtils.put("login_sitename", str.getSiteName());
        SpUtils.put("login_sitecode", str.getSiteCode());
        SpUtils.put("login_empcode", str.getEmpCode());
        SpUtils.put("login_empname", str.getEmpName());
        mPresenter.saveUser(str);
        boolean isFirstDownload = SpUtils.getBoolean(this, "isfirst_download", true);
        if (isFirstDownload) {
            Intent intent = new Intent(this, UpdateDataActivity.class);
            intent.putExtra("is_login", true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

    }


    @Override
    public void loginFail(String failMsg) {
        toast(failMsg);
    }

    @Override
    public DaoSession getDaosession() {
        return daoSession;
    }


    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        editSiteCode.setText((String) SpUtils.get("login_sitecode", ""));
        editEmployCode.setText((String) SpUtils.get("login_empcode", ""));
        tvVersion.setText("版本号：" + BuildConfig.VERSION_NAME);
        checkVersion();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        requestRuntimePermissions(PERMISSIONS_STORAGE);
    }

    @Override
    protected int getLayoutId() {
        return this.getClass().getAnnotation(LayoutId.class).id();
    }

    @Override
    protected void receiveScancode(String billcode) {
        LogUtils.e("zs", billcode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour>=6&&hour<12) {
            imageView.setImageResource(R.drawable.good_morning_img);
            textView.setText("Morning");
        } else if(hour>=12&&hour<18){
            imageView.setImageResource(R.drawable.good_morning_img);
            textView.setText("Afternoon");
        } else {
            imageView.setImageResource(R.drawable.good_night_img);
            textView.setText("Night");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(editSiteCode.getText())) {
            toast("网点编号为空！");
            return;
        }
        if (TextUtils.isEmpty(editEmployCode.getText())) {
            toast("员工编号为空！");
            return;
        }
        if (TextUtils.isEmpty(editPassword.getText())) {
            toast("密码为空！");
            return;
        }
        showLoading();
        mPresenter.login(editSiteCode.getText().toString(), editEmployCode.getText().toString().trim(), editPassword.getText().toString().trim());
    }

    private void checkVersion() {
        Intent verService = new Intent(this, CheckVersionService.class);
        verService.putExtra("fromSource", 0);
        startService(verService);
    }

    public  void requestRuntimePermissions(String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        // 遍历每一个申请的权限，把没有通过的权限放在集合中
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
            }
        }
        // 申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1);
        }
    }
}
