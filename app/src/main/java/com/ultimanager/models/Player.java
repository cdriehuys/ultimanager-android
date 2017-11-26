package com.ultimanager.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


/**
 * Entity to allow for storing a player's information in the database.
 */
@Entity
public class Player {
    // We auto-generate an ID so we have an easy way to reference players. It's much easier to pass
    // a player's ID around than the entire object.
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * The player's full name.
     */
    public String name;

    /**
     * The player's role on the field.
     */
    @TypeConverters({Converters.class})
    public PlayerRole role;
}
