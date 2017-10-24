package com.ultimanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * This is the activity that is launched when the app is started.
 *
 * It allows the user to choose whether they want to record a new game or browse previously recorded
 * statistics.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}
