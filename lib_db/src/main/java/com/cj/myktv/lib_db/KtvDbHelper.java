package com.cj.myktv.lib_db;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.Utils;
import com.cj.myktv.lib_db.database.DaoMaster;
import com.cj.myktv.lib_db.database.DaoSession;
import com.cj.myktv.lib_db.database.TblSongDao;

import org.greenrobot.greendao.database.Database;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 下午 5:32:36
 */
public class KtvDbHelper {

    public static String DB_PATH = PathUtils.getExternalAppDataPath() + "/files/databases/myktv.db";

    private DaoMaster.DevOpenHelper mDevOpenHelper;

    public KtvDbHelper() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(), "myktv.db");
    }

    public static KtvDbHelper getInstance(){
        return KtvDbHelper.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static KtvDbHelper INSTANCE = new KtvDbHelper();
    }

    public interface IDao<T>{
        void onGetDao(T t);
    }

    public void getTblSongDao(IDao<TblSongDao> iDao){
        Database db = mDevOpenHelper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        TblSongDao dao = daoSession.getTblSongDao();
        if (iDao != null) {
            iDao.onGetDao(dao);
        }
        db.close();
    }
}
