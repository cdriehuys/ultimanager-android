package com.ultimanager.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(version = 1, entities = {Player.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    abstract public PlayerDao playerDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                            AppDatabase.class,
                                            "app-db").build();
        }

        return instance;
    }
}
