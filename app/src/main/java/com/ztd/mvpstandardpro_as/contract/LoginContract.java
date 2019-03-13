package com.ztd.mvpstandardpro_as.contract;
import com.zhitengda.gen.DaoSession;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.mvp.IView;

import java.util.List;

/**
 * Created by GaoSheng on 2016/11/26.
 * 18:28
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.contract
 * 契约类,定义登录用到的一些接口方法
 */

public class LoginContract {

    public interface LoginView extends IView {
        void loginSuccess(LoginBean str);
        void loginFail(String failMsg);
        DaoSession getDaosession();
    }


    public interface LoginPresenter {
        void login(String siteName, String name, String pwd);
        void saveUser(LoginBean loginBean);
    }
}
