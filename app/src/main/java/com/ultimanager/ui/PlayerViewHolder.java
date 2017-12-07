package com.ultimanager.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.Player;


public class PlayerViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView numTextView;
    private TextView roleTextView;

    public PlayerViewHolder(View view) {
        super(view);

        nameTextView = view.findViewById(R.id.tv_player_name);
        numTextView = view.findViewById(R.id.tv_player_number);
        roleTextView = view.findViewById(R.id.tv_player_role);
    }

    public static void bind(PlayerViewHolder holder, Player player) {
        holder.nameTextView.setText(player.name);
        holder.numTextView.setText("#" + String.valueOf(player.number));
        holder.roleTextView.setText(player.role.humanName());
    }

    public void bind(Player player) {
        PlayerViewHolder.bind(this, player);
    }
}
