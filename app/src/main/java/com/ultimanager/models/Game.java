package com.ultimanager.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;


/**
 * Entity for storing information about a specific game.
 */
@Entity
public class Game {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @TypeConverters({Converters.class})
    public Date startTime;

    @TypeConverters({Converters.class})
    public GamePosition startPosition;

    public String opposingTeam;
}
