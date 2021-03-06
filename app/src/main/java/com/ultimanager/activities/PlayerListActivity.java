package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ultimanager.R;
import com.ultimanager.models.Player;
import com.ultimanager.viewmodels.PlayerViewModel;
import com.ultimanager.views.PlayerRecyclerViewAdapter;

import java.util.ArrayList;


/**
 * Activity for listing the players in the database.
 */
public class PlayerListActivity extends AppCompatActivity implements View.OnClickListener {

    private PlayerViewModel viewModel;
    private PlayerRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        // Set up the recycler view
        recyclerView = findViewById(R.id.recycler_players);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setLayoutManager(layoutManager);

        // Use our custom adapter
        recyclerViewAdapter = new PlayerRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Retrieve our view model, which is basically a long living container for our list of
        // players, and listen for changes. If the list changes, the recycler view is updated.
        viewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        viewModel.getPlayerList().observe(this, players -> recyclerViewAdapter.setPlayers(players));

        // Set up the floating action button to launch an activity to add a new player.
        FloatingActionButton fab = findViewById(R.id.fab_add_player);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerListActivity.this, PlayerAddActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Launch the detail activity for the player who was clicked.
     *
     * @param view The view containing the information of the player that was clicked.
     */
    @Override
    public void onClick(View view) {
        Player player = (Player) view.getTag();

        Intent intent = new Intent(this, PlayerDetailActivity.class);
        intent.putExtra(PlayerDetailActivity.EXTRA_PLAYER_ID, player.id);

        startActivity(intent);
    }
}
