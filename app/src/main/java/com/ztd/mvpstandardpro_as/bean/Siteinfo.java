package com.ztd.mvpstandardpro_as.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-09-26.
 */
@Entity
public class Siteinfo {
    @Id(autoincrement = true)
    private Long _id;
    private String siteCode; // 站点编号
    private String siteName; // 站点名字
    public String getSiteName() {
        return this.siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    public String getSiteCode() {
        return this.siteCode;
    }
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    @Generated(hash = 1490305569)
    public Siteinfo(Long _id, String siteCode, String siteName) {
        this._id = _id;
        this.siteCode = siteCode;
        this.siteName = siteName;
    }
    @Generated(hash = 304428296)
    public Siteinfo() {
    }
}
