package com.cj.myktv.lib_business.bean;

import com.cj.myktv.lib_db.database.TblSong;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/6 19:33
 */
public class Song extends TblSong {
    public Song(TblSong tblSong) {
        setId(tblSong.getId());
        setName(tblSong.getName());
    }
}
