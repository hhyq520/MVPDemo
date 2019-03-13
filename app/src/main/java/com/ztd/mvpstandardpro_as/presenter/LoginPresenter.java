package com.ztd.mvpstandardpro_as.presenter;




import com.ztd.mvpstandardpro_as.base.BasePresenter;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.contract.LoginContract;
import com.ztd.mvpstandardpro_as.model.LoginModel;
import com.ztd.mvpstandardpro_as.mvp.IModel;
import com.ztd.mvpstandardpro_as.mvpactivity.LoginActivity;
import com.ztd.mvpstandardpro_as.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by GaoSheng on 2016/11/26.
 * 20:51
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.presenter
 */

public class LoginPresenter extends BasePresenter<LoginActivity> implements
        LoginContract.LoginPresenter {

    @Override
    public void login(String siteName,String name, String pwd) {
            ((LoginModel) getiModelMap().get("login")).login(siteName,name, pwd, new LoginModel
                    .InfoHint() {
                @Override
                public void successInfo(LoginBean str) {
                    getIView().loginSuccess(str);  //成功
                }

                @Override
                public void failInfo(String str) {
                    getIView().loginFail(str);  //失败

                }

                @Override
                public void onNext() {
                    getIView().hideLoading();
                }
            });
    }



    @Override
    public void saveUser(LoginBean loginBean) {
        loginBean.set_id(0L);
        getIView().getDaosession().getLoginBeanDao().insertOrReplace(loginBean);
    }


    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel(),new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }
}
