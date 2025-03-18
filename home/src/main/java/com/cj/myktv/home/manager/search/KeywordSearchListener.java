package com.cj.myktv.home.manager.search;

import android.widget.TextView;

import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;
import com.cj.myktv.lib_db.database.TblSong;

import java.util.List;

/**
 * @Description: 关键字搜索
 * @Author: CJ
 * @CreateDate: 2025/3/17 20:47
 */
public class KeywordSearchListener extends BaseSearchListener {

    @Override
    public int getDataCount() {
        return (int) KtvDbHelper.getInstance().querySongCountByKeyword(mInput);
    }

    @Override
    public List<Song> loadData(int pos, int size) {
        return Song.getSongList(KtvDbHelper.getInstance().querySongListByKeyword(mInput, pos, size));
    }

    @Override
    public boolean highlightName(TextView textView, Song song) {
        return false;
    }
}
