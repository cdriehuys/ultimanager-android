package com.ultimanager.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.ultimanager.models.Player;
import com.ultimanager.viewmodels.PlayerListViewModel;

import java.util.ArrayList;
import java.util.List;


public class LineSelectFragment extends Fragment implements View.OnClickListener {
    private Adapter recyclerViewAdapter;
    private Button useLineBtn;
    private MultiSelector multiSelector;
    private OnLineSelectedListener lineSelectedListener;
    private RecyclerView recyclerView;

    public interface OnLineSelectedListener {
        void lineSelected(Long[] selectedPlayerIds);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            lineSelectedListener = (OnLineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement 'OnLineSelectedListener'.");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_line:
                handleLineSelected();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        multiSelector = new MultiSelector();
        multiSelector.setSelectable(true);

        recyclerViewAdapter = new Adapter(new ArrayList<>());

        PlayerListViewModel viewModel = ViewModelProviders.of(this).get(PlayerListViewModel.class);
        viewModel.loadAllPlayers();
        viewModel.getPlayerList().observe(this, recyclerViewAdapter::setPlayers);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(R.layout.fragment_line_select, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView = fragmentRoot.findViewById(R.id.recycler_line_select);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        useLineBtn = fragmentRoot.findViewById(R.id.btn_select_line);
        useLineBtn.setOnClickListener(this);

        Button selectLineBtn = fragmentRoot.findViewById(R.id.btn_select_7);
        selectLineBtn.setOnClickListener(this::selectFirst7);

        return fragmentRoot;
    }

    private void handleLineSelected() {
        List<Integer> positions = multiSelector.getSelectedPositions();
        Long[] playerIds = new Long[positions.size()];

        for (int i = 0; i < positions.size(); i++) {
            Player player = (Player) recyclerView.getChildAt(positions.get(i)).getTag();

            playerIds[i] = player.id;
        }

        lineSelectedListener.lineSelected(playerIds);
    }

    private void handlePlayerSelection() {
        if (multiSelector.getSelectedPositions().size() == 7) {
            useLineBtn.setEnabled(true);
        } else {
            useLineBtn.setEnabled(false);
        }
    }

    private void selectFirst7(View view){
        for(int i = 0; i < 7; i++){
            multiSelector.setSelected(i, 0, true);
        }

        handlePlayerSelection();
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
}
