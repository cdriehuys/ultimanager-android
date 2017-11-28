package com.ultimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultimanager.activities.GameAddActivity;
import com.ultimanager.activities.PlayerListActivity;


/**
 * This is the activity that is launched when the app is started.
 *
 * It allows the user to choose whether they want to record a new game or browse previously recorded
 * statistics.
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * Handle click events for the activity's buttons.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_list_players:
                launchPlayerList();
                break;
            case R.id.btn_new_game:
                launchNewGameActivity();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    private void launchNewGameActivity() {
        Intent intent = new Intent(this, GameAddActivity.class);
        startActivity(intent);
    }

    private void launchPlayerList() {
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }
}
