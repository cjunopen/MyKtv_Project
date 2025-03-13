package com.cj.myktv.lib_business.bean;

import com.cj.myktv.lib_db.database.TblSong;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Song> getSongList(List<TblSong> tblSongs){
        List<Song> songs = new ArrayList<>();
        for (TblSong tblSong : tblSongs){
            songs.add(new Song(tblSong));
        }
        return songs;
    }
}
