package com.ultimanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ultimanager.R;


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

        int playerId = intent.getIntExtra(EXTRA_PLAYER_ID, -1);

        TextView nameTextView = findViewById(R.id.tv_player_name);
        nameTextView.setText("Loading Player With ID: " + playerId);
    }
}
