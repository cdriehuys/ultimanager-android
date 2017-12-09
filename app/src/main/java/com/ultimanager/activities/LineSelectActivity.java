package com.ultimanager.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.ultimanager.R;
import com.ultimanager.WelcomeActivity;
import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Player;
import com.ultimanager.models.Point;
import com.ultimanager.models.PointPlayer;
import com.ultimanager.viewmodels.PlayerListViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LineSelectActivity extends AppCompatActivity {
    public final static String EXTRA_GAME_ID = "com.ultimanager.extras.GAME_ID";

    private RecyclerView recyclerView;

    private Button useLineBtn;

    private long gameId;

    private MultiSelector multiSelector;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_line:
                handleSelectLine();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_select);

        multiSelector = new MultiSelector();
        multiSelector.setSelectable(true);

        recyclerView = findViewById(R.id.recycler_line_select);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setLayoutManager(layoutManager);

        final Adapter recyclerViewAdapter = new Adapter(new ArrayList<Player>());
        recyclerView.setAdapter(recyclerViewAdapter);

        PlayerListViewModel viewModel = ViewModelProviders.of(this).get(PlayerListViewModel.class);
        viewModel.getPlayerList().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                recyclerViewAdapter.setPlayers(players);
            }
        });

        useLineBtn = findViewById(R.id.btn_select_line);

        Intent intent = getIntent();
        gameId = intent.getLongExtra(EXTRA_GAME_ID, -1);
    }

    private void handlePlayerSelection() {
        if (multiSelector.getSelectedPositions().size() == 7) {
            useLineBtn.setEnabled(true);
        } else {
            useLineBtn.setEnabled(false);
        }
    }

    private void handleSelectLine() {
        List<Integer> positions = multiSelector.getSelectedPositions();
        Long[] playerIds = new Long[positions.size()];

        for (int i = 0; i < positions.size(); i++) {
            Player player = (Player) recyclerView.getChildAt(positions.get(i)).getTag();
            playerIds[i] = player.id;
        }

        new SaveLineTask(this, gameId).execute(playerIds);
    }

    private void handleSelectLineComplete() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }

    private class ViewHolder extends SwappingHolder implements View.OnClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private TextView nameTextView;
        private TextView numTextView;
        private TextView roleTextView;

        ViewHolder(View view) {
            super(view, multiSelector);

            nameTextView = view.findViewById(R.id.tv_player_name);
            numTextView = view.findViewById(R.id.tv_player_number);
            roleTextView = view.findViewById(R.id.tv_player_role);

            view.setOnClickListener(this);
        }

        void bind(Player player) {
            nameTextView.setText(player.name);
            numTextView.setText("#" + String.valueOf(player.number));
            roleTextView.setText(player.role.humanName());

            itemView.setTag(player);
        }

        @Override
        public void onClick(View view) {
            if (multiSelector.tapSelection(this)) {
                Log.v(TAG, "Selected player: " + nameTextView.getText().toString());

                handlePlayerSelection();
            }
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Player> players;

        Adapter(List<Player> players) {
            this.players = players;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Player player = players.get(position);

            holder.bind(player);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.player_recycler_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

        void setPlayers(List<Player> players) {
            this.players = players;
            notifyDataSetChanged();
        }
    }

    private static class SaveLineTask extends AsyncTask<Long, Void, Void> {
        private long gameId;
        private WeakReference<LineSelectActivity> activityReference;

        SaveLineTask(LineSelectActivity activity, long gameId) {
            activityReference = new WeakReference<>(activity);
            this.gameId = gameId;
        }

        @Override
        protected Void doInBackground(Long... playerIds) {
            LineSelectActivity activity = activityReference.get();
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
            LineSelectActivity activity = activityReference.get();
            if (activity != null) {
                activity.handleSelectLineComplete();
            }
        }
    }
}
