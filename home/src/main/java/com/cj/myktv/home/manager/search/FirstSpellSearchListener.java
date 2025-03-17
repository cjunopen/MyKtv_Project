package com.cj.myktv.home.manager.search;

import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;

import java.util.List;

/**
 * @Description: 首拼搜索
 * @Author: CJ
 * @CreateDate: 2025/3/17 21:05
 */
public class FirstSpellSearchListener extends BaseSearchListener{
    @Override
    public int getDataCount() {
        return (int) KtvDbHelper.getInstance().querySongCountBySpell(mInput);
    }

    @Override
    public List<Song> loadData(int pos, int size) {
        return Song.getSongList(KtvDbHelper.getInstance().querySongListBySpell(mInput, pos, size));
    }
}
