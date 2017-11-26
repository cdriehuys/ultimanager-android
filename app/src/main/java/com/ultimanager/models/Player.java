package com.ultimanager.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * Entity to allow for storing a player's information in the database.
 */
@Entity
@TypeConverters({Converters.class})
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public PlayerRole role;
}
