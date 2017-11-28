package com.ultimanager.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Player;

import java.util.List;


/**
 * A container for the data displayed when listing games.
 *
 * This allows us to persist the data through events that trigger any UI re-renders such as
 * orientation changes.
 */
public class GameListViewModel extends AndroidViewModel {
    private final LiveData<List<Game>> gameList;

    /**
     * Create a new view model to store a list of players.
     *
     * The list is populated using the current list of players in the application's database.
     *
     * @param app The application to tie the view model's lifecycle to.
     */
    public GameListViewModel(Application app) {
        super(app);

        AppDatabase db = AppDatabase.getAppDatabase(getApplication());
        gameList = db.gameDao().getAllGames();
    }

    /**
     * Get the list of games stored in the view model.
     *
     * @return The current stored list of games.
     */
    public LiveData<List<Game>> getGameList() {
        return gameList;
    }
}
