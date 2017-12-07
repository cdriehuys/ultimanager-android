package com.ultimanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ultimanager.R;
import com.ultimanager.models.PlayerRole;

public class ThrowEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_event);
    }


/*
Throws
	PlayerId: INT, primary key
	ReceiverId: INT, foreign key to players table
	ThrowType: enum ('flick', 'backhand', 'other')
	ExtraThrowInfo: enum ('io', 'oi', 'flat', 'scoober', 'hammer', 'offhand')
	ThrowDirection: enum ( 'under', 'deep', 'dump_or_lateral')
	Throw Result: enum ( 'completion', 'turnover', 'score')
 */

private void handleThrowInformation(){

    RadioButton radflick = findViewById(R.id.radio_flick);
    RadioButton radbackhand = findViewById(R.id.radio_backhand);
    RadioButton radOther = findViewById(R.id.radio_throw_other);

    RadioButton rad_1 = findViewById(R.id.radio_io);
    RadioButton rad_2 = findViewById(R.id.radio_oi);
    RadioButton rad_3 = findViewById(R.id.radio_flat);

    RadioButton rad_under = findViewById(R.id.radio_under);
    RadioButton rad_deep = findViewById(R.id.radio_deep);
    RadioButton rad_dump = findViewById(R.id.radio_dump);

    RadioButton rad_completion = findViewById(R.id.completion);
    RadioButton rad_turnover = findViewById(R.id.turnover);
    RadioButton rad_score = findViewById(R.id.score);



    // throw info
    if (radflick.isChecked()) {
        if (rad_1.isChecked()) {
            // io flick
        } else if (rad_2.isChecked()) {
            // oi flick
        } else if (rad_3.isChecked()) {
            // flat flick
        }
    } else if (radbackhand.isChecked()) {
        if (rad_1.isChecked()) {
            // io backhand
        } else if (rad_2.isChecked()) {
            // oi backhand
        } else if (rad_3.isChecked()) {
            // flat backhand
        }
    } else if (radOther.isChecked()) {
        if (rad_1.isChecked()) {
            // scoober
        } else if (rad_2.isChecked()) {
            // hammer
        } else if (rad_3.isChecked()) {
            // offhand
        }
    } else {
        Toast.makeText(this, "Please specify more throw information.", Toast.LENGTH_SHORT).show();
        return;
    }


    // distance
    if (rad_under.isChecked()) {
        // under
    } else if (rad_deep.isChecked()) {
        // deep
    } else if (rad_dump.isChecked()) {
        // dump
    }

    //result
    if (rad_completion.isChecked()) {
        // complete pass- not a score
    } else if (rad_turnover.isChecked()) {
        // turnover
    } else if (rad_score.isChecked()) {
        // score
    }


    //TODO get the player and receiver info. - add the throw event to the database.
}

}
