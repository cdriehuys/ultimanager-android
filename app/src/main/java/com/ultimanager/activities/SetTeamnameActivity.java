package com.ultimanager.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.WelcomeActivity;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;
import com.ultimanager.models.PlayerRole;

public class SetTeamnameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_teamname);
    }


    /**
     * Handle button clicks for the activity.
     *
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_teamname:
                setTeamname();
                break;
        }
    }

    /**
     * Pull the player's information from the form and add them to the database.
     */
    private void setTeamname() {
       String teamName;

        // Pull the player's name from the text input
        EditText nameInput = findViewById(R.id.input_team_name);
        teamName = nameInput.getText().toString();

        if( teamName.length() < 1 ){
            Toast.makeText(this,"Please enter your team name",Toast.LENGTH_SHORT).show();
            return;
        }

        saveTeamname(teamName);


        /**
         * Return to welcome screen.
         */
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

    public void saveTeamname( String s ) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor spe = sp.edit();
        spe.putString("teamname", s);
        spe.commit();

        Toast.makeText(this, "Team name saved", Toast.LENGTH_SHORT).show();
    }


}
