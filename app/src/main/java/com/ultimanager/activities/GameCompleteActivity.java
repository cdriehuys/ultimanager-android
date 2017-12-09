package com.ultimanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ultimanager.R;
import com.ultimanager.WelcomeActivity;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameCompleteActivity extends AppCompatActivity {

    int score_us, score_them;
    String winningTeamname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);

        winningTeamname = "welp";

        score_us = 5;
        score_them = 4;
    }

    private void setDisplay(){
        TextView winner = findViewById(R.id.tv_winner);
        TextView score = findViewById(R.id.tv_score);

        winner.setText(winningTeamname + "\nwon the game\n");
        score.setText("The final score was\n"+"Our Score: " + score_us + "\n"+winningTeamname+"'s Score: "+score_them);
    }


    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                goToHome();
                break;
        }
    }

    private void goToHome(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}

