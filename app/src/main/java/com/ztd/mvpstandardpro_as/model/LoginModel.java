package com.ztd.mvpstandardpro_as.model;

import android.support.annotation.NonNull;

import com.ztd.mvpstandardpro_as.ProApplication;
import com.ztd.mvpstandardpro_as.base.BaseHttpResult;
import com.ztd.mvpstandardpro_as.base.BaseModel;
import com.ztd.mvpstandardpro_as.base.BaseObserver;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.exception.ApiException;
import com.ztd.mvpstandardpro_as.transformer.CommonTransformer;

import java.util.List;

import io.reactivex.schedulers.Schedulers;


/**
 * Created by GaoSheng on 2016/11/26.
 * 20:53
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.model
 * 主要做一些数据处理呀,网路请求呀
 */

public class LoginModel extends BaseModel {
    private boolean isLogin = false;

    public boolean login(@NonNull String sitename,@NonNull String username, @NonNull String pwd,@NonNull final InfoHint
            infoHint) {
        httpService.login(sitename,username,pwd,"司机")
                .compose(CommonTransformer.<BaseHttpResult<LoginBean>>compose())
                .subscribe(new BaseObserver<LoginBean>(ProApplication.getAppContext()) {

                    @Override
                    protected void onError(ApiException e) {

                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        if(infoHint!=null){
                            infoHint.successInfo(loginBean);
                        }
                    }


                    @Override
                    protected void onFail(int status,String t) {
                        if(infoHint!=null){
                            infoHint.failInfo(t);
                        }
                    }

                    @Override
                    protected void onFinish() {
                        if(infoHint!=null){
                            infoHint.onNext();
                        }
                    }
                });
        return true;
    }


    //通过接口产生信息回调
    public interface InfoHint {
        void successInfo(LoginBean str);

        void failInfo(String str);

        void onNext();

    }



}
