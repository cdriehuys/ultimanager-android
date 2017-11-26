package com.ultimanager.models;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertPlayers(Player... players);
}
