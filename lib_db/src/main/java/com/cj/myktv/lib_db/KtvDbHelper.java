package com.cj.myktv.lib_db;

import android.text.TextUtils;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.cj.myktv.lib_db.database.DaoMaster;
import com.cj.myktv.lib_db.database.DaoSession;
import com.cj.myktv.lib_db.database.TblSong;
import com.cj.myktv.lib_db.database.TblSongDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 下午 5:32:36
 */
public class KtvDbHelper {

    private DaoMaster.DevOpenHelper mDevOpenHelper;

    private TblSongDao mTblSongDao;

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

    public TblSongDao getTblSongDao(){
        if (mTblSongDao == null) {
            Database db = mDevOpenHelper.getWritableDb();
            DaoSession daoSession = new DaoMaster(db).newSession();
            mTblSongDao = daoSession.getTblSongDao();
        }
        return mTblSongDao;
    }

    public long getSongCount(){
        return getSongCountByWord("");
    }

    public long getSongCountByWord(String word){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(word)){
            queryBuilder.where(TblSongDao.Properties.Spell.like("%" + word + "%"));
        }
        return queryBuilder.count();
    }

    public List<TblSong> getSongList(int startIndex, int size){
        return querySongListByWord("", startIndex, size);
    }

    public List<TblSong> querySongListByWord(String word, int startIndex, int size){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(word)){
            queryBuilder.where(TblSongDao.Properties.Spell.like("%" + word + "%"));
        }
        return queryBuilder.limit(size)
                .list();
    }
}
