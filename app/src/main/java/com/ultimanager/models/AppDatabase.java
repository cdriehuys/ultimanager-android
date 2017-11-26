package com.ultimanager.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Wrapper around the application's database.
 *
 * This provides access to the DAO's used to interact with the application's models.
 */
@Database(version = 1, entities = {Player.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    /**
     * Get an instance of the application's database.
     *
     * This is a singleton, so the database is only constructed a single time.
     *
     * @param context The context to construct the database with.
     *
     * @return An instance giving access to the application's database.
     */
    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "app-db").build();
        }

        return instance;
    }

    abstract public PlayerDao playerDao();
}
