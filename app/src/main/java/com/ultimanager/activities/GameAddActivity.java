package com.ultimanager.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.GamePosition;

import java.lang.ref.WeakReference;
import java.util.Date;


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
        finish();

        Intent intent = new Intent(this, GameTrackerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(GameTrackerActivity.EXTRA_GAME_ID, game.id);
        intent.putExtra(GameTrackerActivity.EXTRA_START_POSITION, game.startPosition.name());

        startActivity(intent);
    }

    /**
     * Pull data from the UI and initiate the creation of a new game.
     */
    private void saveGame() {
        EditText opposingTeamTextView = findViewById(R.id.input_opposing_team);

        RadioButton defenseButton = findViewById(R.id.radio_defense);
        RadioButton offenseButton = findViewById(R.id.radio_offense);

        GamePosition position;
        if (defenseButton.isChecked()) {
            position = GamePosition.DEFENSE;
        } else if (offenseButton.isChecked()) {
            position = GamePosition.OFFENSE;
        } else {
            String message = "Please select a starting position.";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            return;
        }

        if(opposingTeamTextView.getText().toString().length() < 1){
            String message = "Please add the opposing team name.";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            return;
        }

        Date startTime = new Date();

        Game game = new Game();
        game.opposingTeam = opposingTeamTextView.getText().toString();
        game.startPosition = position;
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
            Game game = games[0];

            // If the activity no longer exists, we abort the task.
            GameAddActivity activity = activityReference.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity);
            game.id = db.games().addGame(game);

            return game;
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
