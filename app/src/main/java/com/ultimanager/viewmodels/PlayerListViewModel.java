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
public class PlayerListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Player>> playerList = new MutableLiveData<>();

    private AppDatabase db;

    /**
     * Create a new view model to store a list of players.
     *
     * The list is populated using the current list of players in the application's database.
     *
     * @param app The application to tie the view model's lifecycle to.
     */
    public PlayerListViewModel(Application app) {
        super(app);

        db = AppDatabase.getAppDatabase(this.getApplication());
    }

    /**
     * Get the list of players stored in the view model.
     *
     * @return The current stored list of players.
     */
    public LiveData<List<Player>> getPlayerList() {
        return playerList;
    }

    public void loadAllPlayers() {
        new LoadPlayersTask(db, playerList).execute();
    }

    public void loadPlayersForPoint(long pointId) {
        new LoadPointPlayersTask(db, playerList).execute(pointId);
    }

    private static class LoadPlayersTask extends AsyncTask<Void, Void, List<Player>> {
        private final static String TAG = LoadPlayersTask.class.getSimpleName();

        private AppDatabase db;
        private MutableLiveData<List<Player>> playerLiveData;

        LoadPlayersTask(AppDatabase db, MutableLiveData<List<Player>> playerLiveData) {
            this.db = db;
            this.playerLiveData = playerLiveData;
        }

        @Override
        protected List<Player> doInBackground(Void... args) {
            Log.v(TAG, "Loading all players.");

            return db.players().getAllPlayers();
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            playerLiveData.setValue(players);
        }
    }

    private static class LoadPointPlayersTask extends AsyncTask<Long, Void, List<Player>> {
        private final static String TAG = LoadPlayersTask.class.getSimpleName();

        private AppDatabase db;
        private MutableLiveData<List<Player>> playerLiveData;

        LoadPointPlayersTask(AppDatabase db, MutableLiveData<List<Player>> playerLiveData) {
            this.db = db;
            this.playerLiveData = playerLiveData;
        }

        @Override
        protected List<Player> doInBackground(Long... pointIds) {
            long id = pointIds[0];

            Log.v(TAG, "Loading players for point with ID: " + id);

            return db.players().getPlayersForPoint(id);
        }

        @Override
        protected void onPostExecute(List<Player> players) {
            playerLiveData.setValue(players);
        }
    }
}
