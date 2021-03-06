package com.ultimanager.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;


/**
 * Wrapper around the application's database.
 *
 * This provides access to the DAO's used to interact with the application's models.
 */
@Database(version = 6, entities = {
        Game.class,
        Player.class,
        Point.class,
        PointPlayer.class,
        Possession.class,
        Throw.class
})
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
            instance = Room
                    .databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            "app-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    abstract public GameDao games();
    abstract public PlayerDao players();
    abstract public PointPlayerDao pointPlayers();
    abstract public PointDao points();
    abstract public PossessionDao possessions();
    abstract public ThrowDao throws_();
}
