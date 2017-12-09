package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.Player;
import com.ultimanager.viewmodels.PlayerViewModel;


/**
 * Activity for viewing a specific player's details.
 */
public class PlayerDetailActivity extends AppCompatActivity {
    public final static String EXTRA_PLAYER_ID = "com.ultimanager.extras.PLAYER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        Intent intent = getIntent();

        // Use the ID given when starting the activity to load the player's information
        long playerId = intent.getLongExtra(EXTRA_PLAYER_ID, -1);

        PlayerViewModel playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.getPlayer(playerId).observe(this, this::showPlayerDetails);
    }

    /**
     * Display the information for a particular player.
     *
     * @param player The player whose information should be displayed.
     */
    private void showPlayerDetails(Player player) {
        TextView nameTextView = findViewById(R.id.tv_player_name);
        TextView roleTextView = findViewById(R.id.tv_player_role);

        nameTextView.setText(player.name);
        roleTextView.setText(player.role.humanName());
    }
}
