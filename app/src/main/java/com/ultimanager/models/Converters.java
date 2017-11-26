package com.ultimanager.models;


import android.arch.persistence.room.TypeConverter;

public class Converters {
    @TypeConverter
    public String playerRoleToInt(PlayerRole role) {
        return role.name();
    }

    @TypeConverter
    public PlayerRole roleNameFromString(String roleName) {
        return PlayerRole.valueOf(roleName);
    }
}
