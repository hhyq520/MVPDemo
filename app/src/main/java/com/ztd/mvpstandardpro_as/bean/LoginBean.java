package com.ztd.mvpstandardpro_as.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gaosheng on 2016/12/1.
 * 22:52
 * com.example.gs.mvpdemo.bean
 */
@Entity
public class LoginBean{
    @Id(autoincrement = true)
    private Long _id;

    private String empCode;
    private String empName;
    private String siteName;
    private String siteCode;
    private String barPassword;
    private String deptName;
    private String systemDate;
    private String superiorFinanceCenter;
    public String getSuperiorFinanceCenter() {
        return this.superiorFinanceCenter;
    }
    public void setSuperiorFinanceCenter(String superiorFinanceCenter) {
        this.superiorFinanceCenter = superiorFinanceCenter;
    }
    public String getSystemDate() {
        return this.systemDate;
    }
    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }
    public String getDeptName() {
        return this.deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getBarPassword() {
        return this.barPassword;
    }
    public void setBarPassword(String barPassword) {
        this.barPassword = barPassword;
    }
    public String getSiteCode() {
        return this.siteCode;
    }
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
    public String getSiteName() {
        return this.siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    public String getEmpName() {
        return this.empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public String getEmpCode() {
        return this.empCode;
    }
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    @Generated(hash = 1361997563)
    public LoginBean(Long _id, String empCode, String empName, String siteName,
            String siteCode, String barPassword, String deptName,
            String systemDate, String superiorFinanceCenter) {
        this._id = _id;
        this.empCode = empCode;
        this.empName = empName;
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.barPassword = barPassword;
        this.deptName = deptName;
        this.systemDate = systemDate;
        this.superiorFinanceCenter = superiorFinanceCenter;
    }
    @Generated(hash = 1112702939)
    public LoginBean() {
    }
}
