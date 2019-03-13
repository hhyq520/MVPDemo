package com.ztd.mvpstandardpro_as.mvpactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.ztd.mvpstandardpro_as.BuildConfig;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.annotation.LayoutId;
import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.base.BasePresenter;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.service.CheckVersionService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-09-26.
 */
@LayoutId(id = R.layout.fra_setting)
public class SettingActivity extends BaseActivity {
    @Bind(R.id.text_set_empname)
    TextView textSetEmpname;
    @Bind(R.id.text_set_sitename)
    TextView textSetSitename;
    @Bind(R.id.text_set_sitecode)
    TextView textSetSitecode;
    @Bind(R.id.text_check_version)
    TextView textCheckVersion;
    @Bind(R.id.tv_setQuitLogin)
    TextView tvSetQuitLogin;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        List<LoginBean> list = daoSession.getLoginBeanDao().queryBuilder().list();
        textSetEmpname.setText("当前用户：" + list.get(0).getEmpName());
        textSetSitecode.setText("网点编号：" + list.get(0).getSiteCode());
        textSetSitename.setText("网点名称：" + list.get(0).getSiteName());
        textCheckVersion.setText("版本更新v"+ BuildConfig.VERSION_NAME);
    }

    @Override
    protected int getLayoutId() {
        return this.getClass().getAnnotation(LayoutId.class).id();
    }

    @Override
    protected void receiveScancode(String billcode) {

    }

    @OnClick({R.id.text_check_version, R.id.tv_setQuitLogin,R.id.text_update_data,R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_update_data:
                Intent i = new Intent(SettingActivity.this, UpdateDataActivity.class);
                startActivity(i);
                break;
            case R.id.text_check_version:
                Intent verService = new Intent(this, CheckVersionService.class);
                verService.putExtra("fromSource", 1);
                startService(verService);
                break;
            case R.id.tv_setQuitLogin:
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
