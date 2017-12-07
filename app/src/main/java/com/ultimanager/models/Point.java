package com.ultimanager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = Game.class,
                                  parentColumns = "id",
                                  childColumns = "game_id",
                                  onDelete = ForeignKey.CASCADE))
public class Point {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "game_id")
    public long gameId;
}
