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

    @TypeConverter
    public GamePosition gamePositionFromString(String name) {
        return GamePosition.valueOf(name);
    }

    @TypeConverter
    public String gamePositionToString(GamePosition position) {
        return position.name();
    }

    @TypeConverter
    public PlayerRole playerRoleFromString(String roleName) {
        return PlayerRole.valueOf(roleName);
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

    @TypeConverter
    public Point.Result pointResultFromString(String resultName) {
        return Point.Result.valueOf(resultName);
    }

    @TypeConverter
    public String pointResultToString(Point.Result result) {
        return result.name();
    }

    @TypeConverter
    public Possession.Reason posessionReasonFromString(String name) {
        return Possession.Reason.valueOf(name);
    }

    @TypeConverter
    public String posessionReasonToString(Possession.Reason reason) {
        return reason.name();
    }

    @TypeConverter
    public Throw.Result throwResultFromString(String name) {
        return Throw.Result.valueOf(name);
    }

    @TypeConverter
    public String throwResultToString(Throw.Result result) {
        return result.name();
    }

    @TypeConverter
    public Throw.Type throwTypeFromString(String name) {
        return Throw.Type.valueOf(name);
    }

    @TypeConverter
    public String throwTypeToString(Throw.Type type) {
        return type.name();
    }
}
