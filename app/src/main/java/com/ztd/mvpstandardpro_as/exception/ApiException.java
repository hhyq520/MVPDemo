package com.ztd.mvpstandardpro_as.exception;

/**
 * Created by gaosheng on 2016/11/6.
 * 21:58
 * com.example.gaosheng.myapplication.exception
 * 自定义的异常,处理解析网络时的错误
 */

public class ApiException {

    public int code;
    public String message;

    public ApiException(String message) {
       this.message=message;
    }
}
