package com.ultimanager.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.Player;

import java.util.List;


public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Player> players;

    public PlayerRecyclerViewAdapter(List<Player> players) {
        this.players = players;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.nameTextView.setText(player.name);
        holder.itemView.setTag(player);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_recycler_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void addItems(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        RecyclerViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.tv_player_name);
        }
    }
}
