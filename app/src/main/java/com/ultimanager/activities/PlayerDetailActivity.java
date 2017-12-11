package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;
import com.ultimanager.tasks.PlayerStatTask;
import com.ultimanager.viewmodels.PlayerViewModel;


/**
 * Activity for viewing a specific player's details.
 */
public class PlayerDetailActivity extends AppCompatActivity implements PlayerStatTask.EventListener {
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


        new PlayerStatTask(this, player, this).execute();
    }

    private void showPlayerStats(int[] stats) {
        LinearLayout statHolder = findViewById(R.id.ll_stat_holder);

        TextView stat1 = new TextView(this);
        TextView stat2 = new TextView(this);
        TextView stat3 = new TextView(this);
        TextView stat4 = new TextView(this);
        TextView stat5 = new TextView(this);

        int num_throws = stats[0];
        int num_turns = stats[1];
        double points_played = stats [2];
        double game_total = stats[3];

        double perc_completions = ((double) (num_throws - num_turns)) / num_throws * 100;
        double points_played_avg = ((points_played)) / game_total;

        stat1.setText(String.format("PERCENT COMPLETIONS: %.2f%%", perc_completions));
        stat2.setText("NUMBER OF THROWS: " + num_throws);
        stat3.setText("NUMBER OF TURNOVERS: " + num_turns);
        stat4.setText("AVG. COMPLETED THROWS PER POINT: " + num_throws);
        stat5.setText("AVG. POINTS PLAYED PER GAME: " + points_played_avg);


        statHolder.addView(stat1);
        statHolder.addView(stat2);
        statHolder.addView(stat3);
        statHolder.addView(stat4);
        statHolder.addView(stat5);
    }

    @Override
    public void onStatsLoaded(int[] arr) {
        showPlayerStats(arr);
    }
}
