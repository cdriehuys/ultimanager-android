package com.ultimanager.ui;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.ultimanager.R;
import com.ultimanager.models.Player;


public class ViewHolder extends SwappingHolder implements View.OnClickListener {
    private static final String TAG = PlayerViewHolder.class.getSimpleName();

    private MultiSelector multiSelector;

    private TextView nameTextView;
    private TextView numTextView;
    private TextView roleTextView;

    public ViewHolder(View view, MultiSelector multiSelector) {
        super(view, multiSelector);

        this.multiSelector = multiSelector;

        nameTextView = view.findViewById(R.id.tv_player_name);
        numTextView = view.findViewById(R.id.tv_player_number);
        roleTextView = view.findViewById(R.id.tv_player_role);

        view.setOnClickListener(this);
    }

    public static void bind(ViewHolder holder, Player player) {
        holder.nameTextView.setText(player.name);
        holder.numTextView.setText("#" + String.valueOf(player.number));
        holder.roleTextView.setText(player.role.humanName());
    }

    public void bind(Player player) {
        ViewHolder.bind(this, player);
    }

    @Override
    public void onClick(View view) {
        if (multiSelector.tapSelection(this)) {
            Log.v(TAG, "Selected player: " + nameTextView.getText().toString());
        }
    }
}
