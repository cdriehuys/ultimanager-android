package com.ultimanager.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ultimanager.R;
import com.ultimanager.models.Game;
import com.ultimanager.viewmodels.GameListViewModel;
import com.ultimanager.views.GameRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Activity for listing the games that exist in the database.
 */
public class GameListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_games);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        this, layoutManager.getOrientation()));
        recyclerView.setLayoutManager(layoutManager);

        final GameRecyclerViewAdapter adapter = new GameRecyclerViewAdapter(new ArrayList<Game>());
        recyclerView.setAdapter(adapter);

        final GameListViewModel viewModel = ViewModelProviders.of(this)
                .get(GameListViewModel.class);
        viewModel.getGameList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                adapter.setGames(games);
            }
        });
    }
}
