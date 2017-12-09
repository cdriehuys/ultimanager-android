package com.ultimanager.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;

import java.lang.ref.WeakReference;


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
        int playerId = intent.getIntExtra(EXTRA_PLAYER_ID, -1);
        new LoadPlayerTask(this).execute(playerId);
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

        String statText = "Total Games Played In: 3\nPercentage of Completed Throws: 87%\nTotal Completed Throws 158\nTotal Incomplete Throws: 24\n\n";
        statText += "Percentage of Completed Catches: 91%\nTotal Catches: 167\nTotal Drops: 15";

//        TextView sampleStats = findViewById(R.id.tv_sample_player_stats);
//        sampleStats.setText(statText);

    }

    /**
     * Asynchronous task to load a player's information.
     */
    private static class LoadPlayerTask extends AsyncTask<Integer, Void, Player> {
        private WeakReference<PlayerDetailActivity> activityReference;

        /**
         * Create a new task to load a player.
         *
         * @param activity A reference to the activity that launched the task.
         */
        LoadPlayerTask(PlayerDetailActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        /**
         * Load the player's information from the database.
         *
         * @param integers An array whose only element is the ID of the player to load.
         *
         * @return The player with the given ID.
         */
        @Override
        protected Player doInBackground(Integer... integers) {
            int id = integers[0];

            PlayerDetailActivity activity = activityReference.get();
            if (activity == null) { cancel(true); }

            AppDatabase db = AppDatabase.getAppDatabase(activity);

            return db.players().getPlayerById(id);
        }

        /**
         * Populate the UI after the player has been loaded.
         *
         * @param result The player loaded from the database.
         */
        @Override
        protected void onPostExecute(Player result) {
            // We only update the UI if the activity is still present
            PlayerDetailActivity activity = activityReference.get();
            if (activity == null) { return; }

            activity.showPlayerDetails(result);
        }
    }
}
