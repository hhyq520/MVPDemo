package com.ztd.mvpstandardpro_as.mvp;

/**
 * Created by GaoSheng on 2016/11/26.
 * 17:20
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.base
 * mvp之v
 */

public interface IView {
    /**
     * 显示加载框
     */
    void showLoading();

    void showLoading(String msg);

    /**
     * 隐藏加载框
     */
    void hideLoading();
}
