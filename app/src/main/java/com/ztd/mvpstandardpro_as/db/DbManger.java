package com.ztd.mvpstandardpro_as.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhitengda.gen.DaoMaster;
import com.zhitengda.gen.DaoSession;

/**
 * Created by Administrator on 2018/5/23.
 */

public class DbManger {
    private static volatile  DbManger instance;
    private DaoSession dao;

    public DbManger(Context context) {
        DbOpenHelper helper = new DbOpenHelper(context, "kds.db");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        dao = daoMaster.newSession();


    }
    public  static DbManger getInstance(Context context){
        if (instance==null){
            synchronized (DbManger.class){
                if (null==instance){
                    instance = new DbManger(context);
                }
            }
        }
        return instance;
    }
    public DaoSession getDaoSession(){
        return dao;
    }


}
