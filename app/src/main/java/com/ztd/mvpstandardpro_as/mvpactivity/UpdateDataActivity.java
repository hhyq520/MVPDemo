package com.ztd.mvpstandardpro_as.mvpactivity;

import android.content.Intent;
import android.view.View;


import com.ztd.mvpstandardpro_as.Http;
import com.ztd.mvpstandardpro_as.HttpService;
import com.ztd.mvpstandardpro_as.ProApplication;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.annotation.LayoutId;
import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.base.BaseHttpResult;
import com.ztd.mvpstandardpro_as.base.BaseObserver;
import com.ztd.mvpstandardpro_as.base.BasePresenter;
import com.ztd.mvpstandardpro_as.bean.Siteinfo;
import com.ztd.mvpstandardpro_as.exception.ApiException;
import com.ztd.mvpstandardpro_as.transformer.CommonTransformer;
import com.ztd.mvpstandardpro_as.utils.SpUtils;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018-09-26.
 */
@LayoutId(id = R.layout.updata_activity)
public class UpdateDataActivity extends BaseActivity {
    private HttpService httpService;
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
         httpService= Http.getHttpService();
         Intent intent=getIntent();
         if(intent!=null){
             boolean isLogin=intent.getBooleanExtra("is_login",false);
             if(isLogin){
                 showLoading();
                 updateSite(true);
             }
         }

    }

    @Override
    protected int getLayoutId() {
        return this.getClass().getAnnotation(LayoutId.class).id();
    }

    @Override
    protected void receiveScancode(String billcode) {

    }

    private void goHome(){
        Intent intent = new Intent(UpdateDataActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.img_back, R.id.tv_all, R.id.tv_site, R.id.tv_carcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                goHome();
                break;
            case R.id.tv_all:
                showLoading();
                updateSite(true);
                break;
            case R.id.tv_site:
                showLoading();
                updateSite(false);
                break;
            case R.id.tv_carcode:
//                showLoading();
//                updateCarcode(false);
                break;
        }
    }

    private void updateSite(final boolean isAll) {
        httpService.initSite()
                .compose(CommonTransformer.<BaseHttpResult<List<Siteinfo>>>compose())
                .subscribe(new BaseObserver<List<Siteinfo>>(ProApplication.getAppContext()) {

                    @Override
                    protected void onError(ApiException e) {

                    }

                    @Override
                    protected void onSuccess(List<Siteinfo> siteinfos) {
                        daoSession.getSiteinfoDao().deleteAll();
                        for (Siteinfo item:siteinfos) {
                            daoSession.getSiteinfoDao().insert(item);
                        }
                        SpUtils.put("isfirst_download",false);
                    }


                    @Override
                    protected void onFail(int status,String t) {

                    }

                    @Override
                    protected void onFinish() {
                        hideLoading();
                        goHome();
                    }
                });
    }




}
