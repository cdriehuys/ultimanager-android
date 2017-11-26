package com.ultimanager.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ultimanager.views.PlayerRecyclerViewAdapter;
import com.ultimanager.R;
import com.ultimanager.models.Player;
import com.ultimanager.viewmodels.PlayerListViewModel;

import java.util.ArrayList;
import java.util.List;


public class PlayerListActivity extends AppCompatActivity {

    private PlayerListViewModel viewModel;
    private PlayerRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        recyclerView = findViewById(R.id.recycler_players);
        recyclerViewAdapter = new PlayerRecyclerViewAdapter(new ArrayList<Player>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(PlayerListViewModel.class);

        viewModel.getPlayerList().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                recyclerViewAdapter.addItems(players);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_player);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerListActivity.this, PlayerAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
