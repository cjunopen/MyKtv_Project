package com.cj.myktv.lib_db.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;

import lombok.experimental.Accessors;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 上午 10:43:22
 */
@Accessors(chain = true)
@Entity(nameInDb = "tblSong",createInDb = false)
public class TblSong {
    @Property(nameInDb = "id")
    private int id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "spell")
    private String spell;

    @Generated(hash = 742081358)
    public TblSong(int id, String name, String spell) {
        this.id = id;
        this.name = name;
        this.spell = spell;
    }

    @Generated(hash = 411822875)
    public TblSong() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return this.spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}
