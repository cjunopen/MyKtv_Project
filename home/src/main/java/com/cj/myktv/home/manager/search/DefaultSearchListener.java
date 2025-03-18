package com.cj.myktv.home.manager.search;

import android.widget.TextView;

import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/17 21:00
 */
public class DefaultSearchListener extends BaseSearchListener {

    @Override
    public int getDataCount() {
        return (int) KtvDbHelper.getInstance().querySongCount();
    }

    @Override
    public List<Song> loadData(int pos, int size) {
        return Song.getSongList(KtvDbHelper.getInstance().querySongList(pos, size));
    }

    @Override
    public boolean highlightName(TextView textView, Song song) {
        return false;
    }
}
