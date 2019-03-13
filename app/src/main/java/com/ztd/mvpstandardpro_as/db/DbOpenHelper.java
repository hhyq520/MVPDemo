package com.ztd.mvpstandardpro_as.db;

import android.content.Context;

import com.zhitengda.gen.DaoMaster;
import com.zhitengda.gen.LoginBeanDao;
import com.zhitengda.gen.SiteinfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2018/5/23.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper{
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.getInstance().migrate(db,LoginBeanDao.class, SiteinfoDao.class);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
