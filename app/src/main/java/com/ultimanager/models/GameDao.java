package com.ultimanager.models;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;


/**
 * Interface for interacting with game objects from the database.
 */
@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertGames(Game... games);
}
