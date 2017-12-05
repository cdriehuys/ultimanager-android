package com.ultimanager.models;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Interface for interacting with game objects from the database.
 */
@Dao
public interface GameDao {

    @Query("SELECT * FROM Game ORDER BY startTime ASC")
    LiveData<List<Game>> getAllGames();

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertGames(Game... games);
}
