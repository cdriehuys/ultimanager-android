package com.ultimanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ultimanager.R;
import android.view.View;
import android.widget.TextView;

/*
    This activity will be accessed between points.
    Goal: to display both team's scores,
        have a button to choose the 7 players on the field,
        have a button to begin the next point

        Handles which screen (DPointActivity or ThrowEventActivity) to go to.
        Handles half time.
 */
public class GameScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);

        setDisplay();
    }

    private void setDisplay() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPref.getString("teamname","");
        if(name.length() < 1) {
            // home team name has not been set yet.
            return;
        }

        //todo get scores and opponent team names

        TextView yourTeam = findViewById(R.id.tv_home_team);
        yourTeam.setText(name);
    }

    private void onClick(View view){
        switch(view.getId()){
            case R.id.btn_next_point:
                startNextPoint();
                break;
            case R.id.btn_mod_players:
                modifyPlayers();
                break;
        }
    }

    private void modifyPlayers() {

        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }

    private void startNextPoint() {

        Boolean onOffense = false; //check if darkside is receiving or not.

        //todo check with the score. and account for if it is the point after halftime.
        if(onOffense){
            Intent intent = new Intent(this, ThrowEventActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DPointActivity.class);
            startActivity(intent);
        }
    }


}
