package com.ultimanager.activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.PlayerRole;

public class StartingOnActivity extends AppCompatActivity {

    RadioButton offenseB;
    RadioButton defenseB;
    EditText pointText;
    int pointNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_on);
    }

    public void onClickSwitch(View v){
        offenseB = findViewById(R.id.radio_offense);
        defenseB = findViewById(R.id.radio_defense);

        if (offenseB.isChecked()) {
            //todo game starts on offense
        } else if (defenseB.isChecked()) {
            //todo game starts on defense
        } else {
            Toast.makeText(this, "Please specify offense or defense.", Toast.LENGTH_SHORT).show();
        }

//        pointText =  findViewById(R.id.point_number_edittextfield);
//        pointNumber = Integer.parseInt(pointText.getText().toString());
//
//        if(0 < pointNumber){
//            //Changes activity
//            Intent X = new Intent(this, GameListActivity.class);
//            startActivity(X);
//        }else {
//            Toast.makeText(this, "Please specify point number.", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }
}
