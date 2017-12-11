package com.ultimanager.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;

import java.util.List;


/**
 * A container for the data displayed when listing players.
 *
 * This allows us to persist the data through events that trigger any UI re-renders such as
 * orientation changes.
 */
public class PlayerViewModel extends AndroidViewModel {
    private AppDatabase db;

    /**
     * Create a new view model to store a list of players.
     *
     * The list is populated using the current list of players in the application's database.
     *
     * @param app The application to tie the view model's lifecycle to.
     */
    public PlayerViewModel(Application app) {
        super(app);

        db = AppDatabase.getAppDatabase(this.getApplication());
    }

    public LiveData<Player> getPlayer(long id) {
        return db.players().getPlayerById(id);
    }

    /**
     * Get the list of players stored in the view model.
     *
     * @return The current stored list of players.
     */
    public LiveData<List<Player>> getPlayerList() {
        return db.players().getAllPlayers();
    }

    public LiveData<List<Player>> getPlayersForPoint(long pointId) {
        return db.players().getPlayersForPoint(pointId);
    }
}
