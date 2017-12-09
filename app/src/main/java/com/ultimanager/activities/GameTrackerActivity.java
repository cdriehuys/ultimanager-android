package com.ultimanager.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ultimanager.R;
import com.ultimanager.WelcomeActivity;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Point;
import com.ultimanager.models.PointPlayer;
import com.ultimanager.ui.GameViewModel;
import com.ultimanager.ui.LineSelectFragment;

import java.lang.ref.WeakReference;


public class GameTrackerActivity extends AppCompatActivity implements
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
        gameViewModel.getGame().observe(this, game -> {
            LineSelectFragment fragment = new LineSelectFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.game_tracker_fragment, fragment);
            transaction.commit();
        });
    }

    private void handleSelectLineComplete() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }

    @Override
    public void lineSelected(Long[] selectedPlayerIds) {
        new SaveLineTask(this, gameId).execute(selectedPlayerIds);
    }

    private static class SaveLineTask extends AsyncTask<Long, Void, Void> {
        private long gameId;
        private WeakReference<GameTrackerActivity> activityReference;

        SaveLineTask(GameTrackerActivity activity, long gameId) {
            activityReference = new WeakReference<>(activity);
            this.gameId = gameId;
        }

        @Override
        protected Void doInBackground(Long... playerIds) {
            GameTrackerActivity activity = activityReference.get();
            if (activity != null) {
                AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());

                Game game = db.games().getById(gameId);

                Point point = new Point(game.id, Point.Result.IN_PROGRESS);

                long pointId = db.points().addPoint(point);

                for (Long id : playerIds) {
                    PointPlayer pointPlayer = new PointPlayer();
                    pointPlayer.playerId = id;
                    pointPlayer.pointId = pointId;

                    db.pointPlayers().addPointPlayer(pointPlayer);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            GameTrackerActivity activity = activityReference.get();
            if (activity != null) {
                activity.handleSelectLineComplete();
            }
        }
    }
}
