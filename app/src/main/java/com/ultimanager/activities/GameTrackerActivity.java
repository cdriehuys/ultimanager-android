package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ultimanager.R;
import com.ultimanager.models.GamePosition;
import com.ultimanager.models.Player;
import com.ultimanager.models.Point;
import com.ultimanager.models.Possession;
import com.ultimanager.tasks.CompletePointTask;
import com.ultimanager.tasks.CreatePointTask;
import com.ultimanager.tasks.CreatePossessionTask;
import com.ultimanager.ui.DefenseFragment;
import com.ultimanager.viewmodels.GameViewModel;
import com.ultimanager.ui.LineSelectFragment;


public class GameTrackerActivity extends AppCompatActivity implements
        CompletePointTask.EventListener,
        CreatePointTask.EventListener,
        CreatePossessionTask.EventListener,
        DefenseFragment.DefenseListener,
        LineSelectFragment.OnLineSelectedListener {
    public final static String EXTRA_GAME_ID = "com.ultimanager.extras.GAME_ID";
    public final static String EXTRA_START_POSITION = "com.ultimanager.extras.START_POSITION";

    private final static String STATE_CURRENT_POSITION = "CURRENT_POSITION";
    private final static String STATE_GAME_ID = "GAME_ID";
    private final static String TAG = GameTrackerActivity.class.getSimpleName();

    private GamePosition currentPosition;
    private GameViewModel gameViewModel;
    private long gameId;
    private Point currentPoint;
    private Possession currentPossession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tracker);

        if (savedInstanceState == null) {
            Log.v(TAG, "Pulling game tracker state from intent.");

            Intent intent = getIntent();
            currentPosition = GamePosition.valueOf(intent.getStringExtra(EXTRA_START_POSITION));
            gameId = intent.getLongExtra(EXTRA_GAME_ID, -1);
        } else {
            Log.v(TAG, "Restoring previous game tracker state.");

            currentPosition = GamePosition.valueOf(
                    savedInstanceState.getString(STATE_CURRENT_POSITION));
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
        new CompletePointTask(this, currentPoint, Point.Result.OPPONENT_SCORED, this)
                .execute();
    }

    @Override
    public void onOpponentTurnover(Possession.Reason reason, @Nullable Player defensivePlayer) {
        Long defensivePlayerId = defensivePlayer == null ? null : defensivePlayer.id;

        Possession possession = new Possession(currentPoint.getId(), reason, defensivePlayerId);

        new CreatePossessionTask(this, possession, this).execute();
    }

    @Override
    public void onPointCreated(Point point) {
        currentPoint = point;

        Bundle args = new Bundle();
        args.putLong(DefenseFragment.ARG_POINT_ID, point.getId());

        DefenseFragment fragment = new DefenseFragment();
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.game_tracker_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onPointCompleted(Point point) {
        if (point.getResult() == Point.Result.HOME_SCORED) {
            currentPosition = GamePosition.DEFENSE;
        } else {
            currentPosition = GamePosition.OFFENSE;
        }

        launchLineSelection();
    }

    @Override
    public void onPossessionCreated(Possession possession) {
        currentPossession = possession;

        Log.v(TAG, "Would switch to offense fragment.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_CURRENT_POSITION, currentPosition.name());
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
