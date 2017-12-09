package com.ultimanager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * A throw tracks a throw between 2 players and its result.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Possession.class,
                    parentColumns = "id",
                    childColumns = "possession_id",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Player.class,
                    parentColumns = "id",
                    childColumns = "receiver_id",
                    onDelete = ForeignKey.SET_NULL),
        @ForeignKey(entity = Player.class,
                    parentColumns = "id",
                    childColumns = "thrower_id",
                    onDelete = ForeignKey.SET_NULL)
})
public class Throw {
    public enum Result {
        COMPLETE,
        SCORE,
        TURN
    }

    public enum Type {
        BACKHAND,
        FOREHAND,
        OTHER
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "possession_id")
    private long possessionId;

    @ColumnInfo(name = "receiver_id")
    private Long receiverId;

    @TypeConverters({Converters.class})
    private Result result;

    @ColumnInfo(name = "thrower_id")
    private Long throwerId;

    @TypeConverters({Converters.class})
    private Type type;

    public Throw(long possessionId, Long throwerId, Long receiverId, Type type, Result result) {
        this.possessionId = possessionId;
        this.throwerId = throwerId;
        this.receiverId = receiverId;
        this.type = type;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public long getPossessionId() {
        return possessionId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public Result getResult() {
        return result;
    }

    public Long getThrowerId() {
        return throwerId;
    }

    public Type getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPossessionId(long possessionId) {
        this.possessionId = possessionId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setThrowerId(Long throwerId) {
        this.throwerId = throwerId;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
