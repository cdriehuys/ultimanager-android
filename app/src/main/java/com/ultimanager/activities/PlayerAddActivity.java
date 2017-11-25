package com.ultimanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultimanager.R;


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
        finish();
    }
}
