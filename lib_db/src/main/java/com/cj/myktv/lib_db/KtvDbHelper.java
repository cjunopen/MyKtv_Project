package com.cj.myktv.lib_db;

import android.text.TextUtils;

import com.cj.myktv.lib_db.database.DaoMaster;
import com.cj.myktv.lib_db.database.DaoSession;
import com.cj.myktv.lib_db.database.TblSong;
import com.cj.myktv.lib_db.database.TblSongDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
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
        try {
            return getTblSongDao().queryBuilder().count();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询歌曲列表
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongList(int startIndex, int size){
        try {
            return getTblSongDao().queryBuilder().offset(startIndex).limit(size).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据关键字搜索歌曲
     * @param word
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongListByKeyword(String word, int startIndex, int size){
        if (TextUtils.isEmpty(word)){
            return querySongList(startIndex, size);
        }
        try {
            return getTblSongDao().queryBuilder()
                    .where(TblSongDao.Properties.Name.like("%" + word + "%"))
                    .offset(startIndex)
                    .limit(size)
                    .list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 关键字搜索，歌曲总数
     * @return
     */
    public long querySongCountByKeyword(String keyword){
        try {
            return getTblSongDao().queryBuilder().where(TblSongDao.Properties.Name.like("%" + keyword + "%")).count();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 首拼搜索，歌曲列表
     * @param spell
     * @param startIndex
     * @param size
     * @return
     */
    public List<TblSong> querySongListBySpell(String spell, int startIndex, int size){
        if (TextUtils.isEmpty(spell)){
            return querySongList(startIndex, size);
        }
        try {
            return getTblSongDao().queryBuilder()
                    .where(TblSongDao.Properties.Spell.like("%" + spell + "%"))
                    .offset(startIndex)
                    .limit(size)
                    .list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 首拼搜索，歌曲总数
     * @return
     */
    public long querySongCountBySpell(String spell){
        try {
            return getTblSongDao().queryBuilder().where(TblSongDao.Properties.Spell.like("%" + spell + "%")).count();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
