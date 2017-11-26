package com.ultimanager.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultimanager.R;
import com.ultimanager.models.Player;

import java.util.List;


/**
 * Custom adapter for listing players in a recycler view.
 */
public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.RecyclerViewHolder> {
    private List<Player> players;

    /**
     * Construct a new adapter for a list of players.
     *
     * @param players The players to initially populate the adapter with.
     */
    public PlayerRecyclerViewAdapter(List<Player> players) {
        this.players = players;
    }

    /**
     * Bind a single element in the recycler view to a player from the player list.
     *
     * Populates the content of the current holder with the information given by the player at the
     * provided position.
     *
     * @param holder The holder to bind to the player at the given position.
     * @param position The position of the player in the player list that the holder is being bound
     *                 to.
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.nameTextView.setText(player.name);
        holder.roleTextView.setText(player.role.humanName());
    }

    /**
     * Customize the layout of each view holder in the recycler view.
     *
     * @param parent The view holder's parent.
     * @param viewType The type of view required for the item that will be bound to the holder being
     *                 created.
     *
     * @return The holder that will be used to display a single player's information.
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_recycler_item, parent, false));
    }

    /**
     * Get the number of items in the view.
     *
     * @return The size of the player list.
     */
    @Override
    public int getItemCount() {
        return players.size();
    }

    /**
     * Set the list of players being displayed.
     *
     * @param players A new list of players to display in the view.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    /**
     * A view containing the information for a single player.
     */
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView roleTextView;

        RecyclerViewHolder(View view) {
            super(view);

            nameTextView = view.findViewById(R.id.tv_player_name);
            roleTextView = view.findViewById(R.id.tv_player_role);
        }
    }
}
