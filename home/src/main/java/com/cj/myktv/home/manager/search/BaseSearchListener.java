package com.cj.myktv.home.manager.search;

import com.cj.lib_tools.interfaces.IDataListener;
import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.database.TblSong;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/17 20:57
 */
public abstract class BaseSearchListener implements IDataListener<Song> {
    protected String mInput;

    public void setInput(String input) {
        mInput = input;
    }
}
