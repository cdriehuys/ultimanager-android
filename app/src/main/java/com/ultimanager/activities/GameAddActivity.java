package com.ultimanager.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Locale;


/**
 * Activity for creating a new game.
 */
public class GameAddActivity extends AppCompatActivity {

    /**
     * Handle button clicks for the activity.
     *
     * @param view The button that was clicked.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_game:
                saveGame();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_add);
    }

    /**
     * Display a message and finish the activity once the game is saved.
     *
     * @param game The game that was saved.
     */
    private void handleGameSaved(Game game) {
        String msg = String.format(Locale.US, "Saved game against %s", game.opposingTeam);

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Pull data from the UI and initiate the creation of a new game.
     */
    private void saveGame() {
        EditText opposingTeamTextView = findViewById(R.id.input_opposing_team);
        Date startTime = new Date();

        Game game = new Game();
        game.opposingTeam = opposingTeamTextView.getText().toString();
        game.startTime = startTime;

        new CreateGameAsyncTask(this).execute(game);
    }

    /**
     * Asynchronous task to save a new game.
     */
    private static class CreateGameAsyncTask extends AsyncTask<Game, Void, Game> {
        private WeakReference<GameAddActivity> activityReference;

        /**
         * Create a new instance of the task.
         *
         * @param activityReference A reference to the activity that launched the task.
         */
        CreateGameAsyncTask(GameAddActivity activityReference) {
            this.activityReference = new WeakReference<>(activityReference);
        }

        /**
         * Save the provided game in the application's database.
         *
         * @param games The games to save. Only the first game is actually saved to the database.
         *
         * @return The game that was saved to the database.
         */
        @Override
        protected Game doInBackground(Game... games) {
            // If the activity no longer exists, we abort the task.
            GameAddActivity activity = activityReference.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity);
            db.gameDao().insertGames(games[0]);

            return games[0];
        }

        /**
         * Attempt to notify the calling activity that the game was successfully saved.
         *
         * @param game The game that was saved.
         */
        @Override
        protected void onPostExecute(Game game) {
            // If the game doesn't exist, nothing new was saved, so we return
            if (game == null) {
                return;
            }

            // If the activity doesn't exist anymore, it can't handle the post-save event
            GameAddActivity activity = activityReference.get();
            if (activity == null) {
                return;
            }

            activity.handleGameSaved(game);
        }
    }
}
