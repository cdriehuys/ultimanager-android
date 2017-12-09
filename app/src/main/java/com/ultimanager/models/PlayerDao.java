package com.ultimanager.models;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Interface for interacting with player objects.
 *
 * This handles mapping SQL queries to actual {@link Player} objects.
 */
@Dao
public interface PlayerDao {

    /**
     * List all the players in the database.
     *
     * @return A list of all the players in the database.
     */
    @Query("SELECT * from Player ORDER BY name ASC")
    LiveData<List<Player>> getAllPlayers();

    @Query("SELECT Player.* FROM PointPlayer " +
           "LEFT JOIN Point ON PointPlayer.point_id = Point.id " +
           "JOIN Player ON PointPlayer.player_id = Player.id " +
           "WHERE Point.id = :pointId")
    LiveData<List<Player>> getPlayersForPoint(long pointId);

    /**
     * Get a player by ID.
     *
     * @param id The ID of the player to search for.
     *
     * @return The player with the given ID.
     */
    @Query("SELECT * FROM Player WHERE id = :id")
    LiveData<Player> getPlayerById(long id);

    /**
     * Insert new players in the database.
     *
     * @param players A variable number of players to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertPlayers(Player... players);
}
