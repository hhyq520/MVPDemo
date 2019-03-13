package com.ztd.mvpstandardpro_as;

import com.ztd.mvpstandardpro_as.base.BaseHttpResult;
import com.ztd.mvpstandardpro_as.bean.LoginBean;
import com.ztd.mvpstandardpro_as.bean.Siteinfo;
import com.ztd.mvpstandardpro_as.bean.VersionEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Huanghao on 2018/10/13.
 * 网络请求的接口都在这里
 */

public interface HttpService {
    /**
     * 登录接口
     * @param siteName
     * @param empCode
     * @param barPassword
     * @return
     */
    @FormUrlEncoded
    @POST("login.do")
    Observable<BaseHttpResult<LoginBean>> login(@Field("siteCode") String siteName, @Field
            ("empCode") String empCode, @Field("barPassword") String barPassword,@Field("empType") String empType);

    /**
     * 检查版本
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST("checkVersion.do")
    Observable<BaseHttpResult<VersionEntity>> checkVersion(@Field("version") String version);

    /**
     * 网点下载
     * @return
     */
    @POST("initSite.do")
    Observable<BaseHttpResult<List<Siteinfo>>> initSite();

}
