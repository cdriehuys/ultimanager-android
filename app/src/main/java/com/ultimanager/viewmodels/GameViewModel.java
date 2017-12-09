package com.ultimanager.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;


public class GameViewModel extends AndroidViewModel {
    private final MutableLiveData<Game> game = new MutableLiveData<>();

    private AppDatabase db;

    public GameViewModel(Application app) {
        super(app);

        db = AppDatabase.getAppDatabase(getApplication());
    }

    public LiveData<Game> getGame() {
        return game;
    }

    public void loadGame(long gameId) {
        new LoadGameTask(db, game).execute(gameId);
    }

    private static class LoadGameTask extends AsyncTask<Long, Void, Game> {
        private final static String TAG = LoadGameTask.class.getSimpleName();

        private AppDatabase db;
        private MutableLiveData<Game> gameLiveData;

        LoadGameTask(AppDatabase db, MutableLiveData<Game> gameLiveData) {
            this.db = db;
            this.gameLiveData = gameLiveData;
        }

        @Override
        protected Game doInBackground(Long... gameIds) {
            long id = gameIds[0];

            Log.v(TAG, "Loading game with ID: " + id);

            return db.games().getById(id);
        }

        @Override
        protected void onPostExecute(Game game) {
            gameLiveData.setValue(game);
        }
    }
}
