package com.ultimanager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * Represents a single point in a game.
 *
 * If the point is not in progress, the result indicates whether the opponent or home team scored.
 */
@Entity(foreignKeys = @ForeignKey(entity = Game.class,
                                  parentColumns = "id",
                                  childColumns = "game_id",
                                  onDelete = ForeignKey.CASCADE))
public class Point {
    public enum Result {
        HOME_SCORED,
        IN_PROGRESS,
        OPPONENT_SCORED
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "game_id")
    private long gameId;

    @ColumnInfo(name = "scored_by_home")
    @TypeConverters({Converters.class})
    private Result result;

    public Point(long gameId, Result result) {
        this.gameId = gameId;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public long getGameId() {
        return gameId;
    }

    public Result getResult() {
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
