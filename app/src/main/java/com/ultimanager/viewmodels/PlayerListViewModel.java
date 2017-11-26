package com.ultimanager.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;

import java.util.List;


public class PlayerListViewModel extends AndroidViewModel {

    private final LiveData<List<Player>> playerList;

    private AppDatabase appDatabase;

    public PlayerListViewModel(Application app) {
        super(app);

        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        playerList = appDatabase.playerDao().getAllPlayers();
    }

    public LiveData<List<Player>> getPlayerList() {
        return playerList;
    }
}
