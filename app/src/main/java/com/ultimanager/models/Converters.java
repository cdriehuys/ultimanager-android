package com.ultimanager.models;

import android.arch.persistence.room.TypeConverter;


/**
 * Converters used to help with storing data in a database.
 *
 * The converters are used to translate between complex objects and their flat representation that
 * can be stored in a database.
 */
class Converters {

    /**
     * Convert a player role into a string.
     *
     * @param role The role to convert to a string.
     *
     * @return The provided role's string name.
     */
    @TypeConverter
    String playerRoleToString(PlayerRole role) {
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
    PlayerRole roleNameFromString(String roleName) {
        return PlayerRole.valueOf(roleName);
    }
}
