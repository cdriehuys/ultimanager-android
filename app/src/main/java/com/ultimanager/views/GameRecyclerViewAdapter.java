package com.ultimanager.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.Game;

import java.text.SimpleDateFormat;
import java.util.List;


public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.RecyclerViewHolder> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/YYYY");

    private List<Game> games;

    public GameRecyclerViewAdapter(List<Game> games) {
        this.games = games;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Game game = games.get(position);
        holder.dateTextView.setText(DATE_FORMAT.format(game.startTime));
        holder.opposingTeamTextView.setText(game.opposingTeam);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_recycler_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView opposingTeamTextView;

        RecyclerViewHolder(View view) {
            super(view);

            dateTextView = view.findViewById(R.id.tv_date);
            opposingTeamTextView = view.findViewById(R.id.tv_opposing_team);
        }
    }
}
