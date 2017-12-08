package com.ultimanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ultimanager.activities.DPointActivity;
import com.ultimanager.activities.GameAddActivity;
import com.ultimanager.activities.GameListActivity;
import com.ultimanager.activities.PlayerListActivity;
import com.ultimanager.activities.SetTeamnameActivity;


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
            case R.id.btn_list_games:
                launchGameList();
                break;
            case R.id.btn_list_players:
                launchPlayerList();
                break;
            case R.id.btn_new_game:
                launchNewGameActivity();
                break;
            case R.id.btn_view_stats:
                launch();
                break;
            case R.id.btn_teamname:
                launchSetTeamnameActivity();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView teamname_tv = findViewById(R.id.tv_teamname_text);
        teamname_tv.setText(getTeamnameText());
    }

    private void launchGameList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }

    private void launchNewGameActivity() {
        Intent intent = new Intent(this, GameAddActivity.class);
        startActivity(intent);
    }

    private void launchPlayerList() {
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }

    private void launch(){
        Intent intent = new Intent(this, DPointActivity.class);
        startActivity(intent);
    }

    private void launchSetTeamnameActivity(){
        Intent intent = new Intent(this, SetTeamnameActivity.class);
        startActivity(intent);
    }

    private String getTeamnameText(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString("teamname","");
        if(name.length() < 1) {
            return "\nYou haven't set your teamname yet. Click on 'EDIT TEAMNAME' to do so.";
        }
        return "Your team is:\n" + name;
    }

}
