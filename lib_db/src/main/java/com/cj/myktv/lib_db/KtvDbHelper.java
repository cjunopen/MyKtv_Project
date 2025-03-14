package com.cj.myktv.lib_db;

import android.text.TextUtils;

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

    public long querySongCount(){
        return querySongCountBySpell("");
    }

    public long querySongCountBySpell(String spell){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(spell)){
            queryBuilder.where(TblSongDao.Properties.Spell.like("%" + spell + "%"));
        }
        return queryBuilder.count();
    }

    public List<TblSong> querySongList(int startIndex, int size){
        return querySongListBySpell("", startIndex, size);
    }

    public List<TblSong> querySongListBySpell(String word, int startIndex, int size){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(word)){
            queryBuilder.where(TblSongDao.Properties.Spell.like("%" + word + "%"));
        }
        return queryBuilder
                .offset(startIndex)
                .limit(size)
                .list();
    }
}
