package com.ultimanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ultimanager.R;

public class DPointActivity extends AppCompatActivity {

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_throwaway:
                launchOPoint();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpoint);

        String[] names = {"Alex", "Chathan", "Elijah", "Marc", "Matt", "Nate", "Sam"};


        setPlayerNames(names);
    }

    private void launchOPoint() {
        Intent intent = new Intent(this, ThrowEventActivity.class);
        startActivity(intent);
    }

    private void setPlayerNames(String[] names){
        Button p1 = findViewById(R.id.player1);
        Button p2 = findViewById(R.id.player2);
        Button p3 = findViewById(R.id.player3);
        Button p4 = findViewById(R.id.player4);
        Button p5 = findViewById(R.id.player5);
        Button p6 = findViewById(R.id.player6);
        Button p7 = findViewById(R.id.player7);

        p1.setText(names[0]);
        p2.setText(names[1]);
        p3.setText(names[2]);
        p4.setText(names[3]);
        p5.setText(names[4]);
        p6.setText(names[5]);
        p7.setText(names[6]);
    }
}