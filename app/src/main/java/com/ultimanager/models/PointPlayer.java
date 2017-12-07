package com.ultimanager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


@Entity(foreignKeys = {
        @ForeignKey(entity = Player.class,
                    parentColumns = "id",
                    childColumns = "player_id",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Point.class,
                    parentColumns = "id",
                    childColumns = "point_id",
                    onDelete = ForeignKey.CASCADE)
})
public class PointPlayer {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "player_id")
    public long playerId;

    @ColumnInfo(name = "point_id")
    public long pointId;
}
