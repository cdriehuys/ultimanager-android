package com.ultimanager.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * Entity to allow for storing a player's information in the database.
 */
@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    
    @TypeConverters({Converters.class})
    public PlayerRole role;
}
