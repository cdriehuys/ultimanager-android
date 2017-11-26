package com.ultimanager.activities;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;
import com.ultimanager.models.PlayerRole;


public class PlayerAddActivity extends AppCompatActivity {

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

    private void createPlayer() {
        final Player player = new Player();

        EditText nameInput = findViewById(R.id.input_player_name);
        player.name = nameInput.getText().toString();

        RadioButton radCutter = findViewById(R.id.radio_role_cutter);
        RadioButton radHandler = findViewById(R.id.radio_role_handler);
        RadioButton radOther = findViewById(R.id.radio_role_other);

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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getAppDatabase(PlayerAddActivity.this);
                db.playerDao().insertPlayers(player);
            }
        });

        finish();
    }
}
