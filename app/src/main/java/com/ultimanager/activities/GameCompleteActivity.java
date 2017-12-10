package com.ultimanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ultimanager.R;
import com.ultimanager.WelcomeActivity;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameCompleteActivity extends AppCompatActivity {

    public final static String EXTRA_WHO_WON = "com.ultimanager.extras.WHO_WON";
    public final static String EXTRA_HOME_SCORE = "com.ultimanager.extras.HOME_SCORE";
    public final static String EXTRA_AWAY_SCORE = "com.ultimanager.extras.AWAY_SCORE";
    public final static String EXTRA_OPP_TEAM = "com.ultimanager.extras.AWAY_NAME";



    int score_us, score_them;
    String winningTeamname;
    String otherTeamName;
    String homeTeamName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);

        Intent intent = getIntent();


        winningTeamname = intent.getStringExtra(EXTRA_WHO_WON);

        otherTeamName = intent.getStringExtra(EXTRA_OPP_TEAM);

        homeTeamName = getTeamnameText();

        score_us = intent.getIntExtra(EXTRA_HOME_SCORE, -1);
        score_them = intent.getIntExtra(EXTRA_AWAY_SCORE, -1);

        setDisplay();
    }

    private void setDisplay(){
        TextView winner = findViewById(R.id.tv_winner);
        TextView score = findViewById(R.id.tv_score);

        winner.setText(winningTeamname + "\nwon the game\n");
        score.setText("The final score was\n"+homeTeamName+"'s: " + score_us + "\n" + otherTeamName + "'s Score: "+score_them);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                launchWelcomeActivity();
                break;
        }
    }

    private void launchWelcomeActivity(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    private String getTeamnameText(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString("teamname","");
        if(name.length() < 1) {
            return "Your Team";
        }
        return "" + name;
    }
}

