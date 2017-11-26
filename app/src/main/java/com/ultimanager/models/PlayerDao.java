package com.ultimanager.models;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface PlayerDao {

    @Query("SELECT * from Player")
    public LiveData<List<Player>> getAllPlayers();

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertPlayers(Player... players);
}
