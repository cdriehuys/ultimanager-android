package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ultimanager.R;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Player;
import com.ultimanager.models.Point;
import com.ultimanager.models.PointPlayer;
import com.ultimanager.models.Possession;
import com.ultimanager.tasks.CreatePointTask;
import com.ultimanager.ui.DefenseFragment;
import com.ultimanager.viewmodels.GameViewModel;
import com.ultimanager.ui.LineSelectFragment;

import java.lang.ref.WeakReference;


public class GameTrackerActivity extends AppCompatActivity implements
        CreatePointTask.EventListener,
        DefenseFragment.DefenseListener,
        LineSelectFragment.OnLineSelectedListener {
    public final static String EXTRA_GAME_ID = "com.ultimanager.extras.GAME_ID";

    private final static String STATE_GAME_ID = "GAME_ID";
    private final static String TAG = GameTrackerActivity.class.getSimpleName();

    private GameViewModel gameViewModel;
    private long gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tracker);

        if (savedInstanceState == null) {
            Log.v(TAG, "Pulling game tracker state from intent.");

            Intent intent = getIntent();
            gameId = intent.getLongExtra(EXTRA_GAME_ID, -1);
        } else {
            Log.v(TAG, "Restoring previous game tracker state.");

            gameId = savedInstanceState.getLong(STATE_GAME_ID, -1);
        }

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.loadGame(gameId);
        gameViewModel.getGame().observe(this, game -> launchLineSelection());
    }

    @Override
    public void lineSelected(Long[] selectedPlayerIds) {
        new CreatePointTask(this, gameViewModel.getGame().getValue(), this)
                .execute(selectedPlayerIds);
    }

    @Override
    public void onOpponentScored() {
        // TODO: Save opponent point
        launchLineSelection();
    }

    @Override
    public void onOpponentTurnover(Possession.Reason reason, @Nullable Player defensivePlayer) {
        // TODO: Save turnover
        launchLineSelection();
    }

    @Override
    public void onPointCreated(Point point) {
        Bundle args = new Bundle();
        args.putLong(DefenseFragment.ARG_POINT_ID, point.getId());

        DefenseFragment fragment = new DefenseFragment();
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.game_tracker_fragment, fragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_GAME_ID, gameId);

        super.onSaveInstanceState(outState);
    }

    private void launchLineSelection() {
        LineSelectFragment fragment = new LineSelectFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.game_tracker_fragment, fragment);
        transaction.commit();
    }
}
