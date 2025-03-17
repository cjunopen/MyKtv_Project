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

    /**
     * 歌曲总数
     * @return
     */
    public long querySongCount(){
        return getTblSongDao().queryBuilder().count();
    }

    /**
     * 查询歌曲列表
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongList(int startIndex, int size){
        return getTblSongDao().queryBuilder().offset(startIndex).limit(size).list();
    }

    /**
     * 根据关键字搜索歌曲
     * @param word
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongListByKeyword(String word, int startIndex, int size){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(word)){
            queryBuilder.where(TblSongDao.Properties.Name.like("%" + word + "%"));
        }
        return queryBuilder
                .offset(startIndex)
                .limit(size)
                .list();
    }

    /**
     * 关键字搜索，歌曲总数
     * @return
     */
    public long querySongCountByKeyword(String keyword){
        return getTblSongDao().queryBuilder().where(TblSongDao.Properties.Name.like("%" + keyword + "%")).count();
    }

    /**
     * 首拼搜索，歌曲列表
     * @param spell
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongListBySpell(String spell, int startIndex, int size){
        QueryBuilder queryBuilder = getTblSongDao().queryBuilder();
        if (!TextUtils.isEmpty(spell)){
            queryBuilder.where(TblSongDao.Properties.Spell.like("%" + spell + "%"));
        }
        return queryBuilder
                .offset(startIndex)
                .limit(size)
                .list();
    }

    /**
     * 首拼搜索，歌曲总数
     * @return
     */
    public long querySongCountBySpell(String spell){
        return getTblSongDao().queryBuilder().where(TblSongDao.Properties.Spell.like("%" + spell + "%")).count();
    }
}
