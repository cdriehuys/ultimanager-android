package com.ultimanager.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;
import com.ultimanager.models.PlayerRole;


/**
 * Activity for adding a new player to the database.
 */
public class PlayerAddActivity extends AppCompatActivity {

    public static int DEFAULT_PLAYER_NUMBER = 999;

    /**
     * Handle button clicks for the activity.
     *
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_player:
                createPlayer();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_add);
    }

    /**
     * Pull the player's information from the form and add them to the database.
     */
    private void createPlayer() {
        final Player player = new Player();

        // Pull the player's name from the text input
        EditText nameInput = findViewById(R.id.input_player_name);
        String n =nameInput.getText().toString();
        if (n.length() < 1 ){
            Toast.makeText(this, "Please specify the player's name.", Toast.LENGTH_SHORT).show();
            return;
        }
        player.name = n;

        EditText numberInput = findViewById(R.id.input_player_number);
        String numString = numberInput.getText().toString();
        if (numString.length() < 1 ){
           player.number = DEFAULT_PLAYER_NUMBER;
        }else {
            player.number = Integer.parseInt(numString);
        }





        RadioButton radCutter = findViewById(R.id.radio_role_cutter);
        RadioButton radHandler = findViewById(R.id.radio_role_handler);
        RadioButton radOther = findViewById(R.id.radio_role_other);

        // Map the currently selected radio button to a player role
        if (radCutter.isChecked()) {
            player.role = PlayerRole.CUTTER;
        } else if (radHandler.isChecked()) {
            player.role = PlayerRole.HANDLER;
        } else if (radOther.isChecked()) {
            player.role = PlayerRole.OTHER;
        } else {
            Toast.makeText(this, "Please specify the player's role.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Database tasks can't be performed on the main thread, so we perform it asynchronously
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Get a reference to the database and store the player's info
                AppDatabase db = AppDatabase.getAppDatabase(PlayerAddActivity.this);
                db.playerDao().insertPlayers(player);

                // Then we finish the task which navigates back to the task that this one was
                // launched from.
                PlayerAddActivity.this.finish();
            }
        });
    }
}
