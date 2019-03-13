package com.ztd.mvpstandardpro_as.base;
import com.ztd.mvpstandardpro_as.Http;
import com.ztd.mvpstandardpro_as.HttpService;
import com.ztd.mvpstandardpro_as.mvp.IModel;

/**
 * Created by gaosheng on 2016/12/1.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
