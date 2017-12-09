package com.ultimanager.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * A possession contains a sequence of throws resulting in either a turn or a goal.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Player.class,
                    parentColumns = "id",
                    childColumns = "defensive_player_id",
                    onDelete = ForeignKey.SET_NULL),
        @ForeignKey(entity = Point.class,
                    parentColumns = "id",
                    childColumns = "point_id",
                    onDelete = ForeignKey.CASCADE)
})
public class Possession {
    public enum Reason {
        D,
        PULL,
        TURN
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "point_id")
    private long pointId;

    @TypeConverters({Converters.class})
    private Reason reason;

    /**
     * If the reason for the possession was a player getting a D, that player is stored in this
     * column.
     */
    @ColumnInfo(name = "defensive_player_id")
    private Long defensivePlayerId;

    public Possession() {

    }

    public Possession(long pointId, Reason reason, Long defensivePlayer) {
        this.pointId = pointId;
        this.reason = reason;
        this.defensivePlayerId = defensivePlayer;
    }

    public Long getDefensivePlayerId() {
        return defensivePlayerId;
    }

    public long getId() {
        return id;
    }

    public long getPointId() {
        return pointId;
    }

    public Reason getReason() {
        return reason;
    }

    public void setDefensivePlayerId(Long defensivePlayerId) {
        this.defensivePlayerId = defensivePlayerId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
}
