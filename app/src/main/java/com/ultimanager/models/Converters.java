package com.ultimanager.models;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;


/**
 * Converters used to help with storing data in a database.
 *
 * The converters are used to translate between complex objects and their flat representation that
 * can be stored in a database.
 */
@SuppressWarnings("WeakerAccess")
public class Converters {

    /**
     * Convert a timestamp into a {@link Date} object.
     *
     * @param timestamp The timestamp to create a date from.
     *
     * @return A new date object representing the given timestamp.
     */
    @TypeConverter
    public Date dateFromTimestamp(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * Convert a date to its value in milliseconds.
     *
     * @param date The data to convert to a timestamp.
     *
     * @return The timestamp (in milliseconds) of the provided date.
     */
    @TypeConverter
    public long dateToTimestamp(Date date) {
        return date.getTime();
    }

    /**
     * Convert a player role into a string.
     *
     * @param role The role to convert to a string.
     *
     * @return The provided role's string name.
     */
    @TypeConverter
    public String playerRoleToString(PlayerRole role) {
        return role.name();
    }

    /**
     * Convert the name of a player role back to its Enum form.
     *
     * @param roleName The string name of the role to convert.
     *
     * @return The player role constant that matches the provided name.
     */
    @TypeConverter
    public PlayerRole roleNameFromString(String roleName) {
        return PlayerRole.valueOf(roleName);
    }
}
