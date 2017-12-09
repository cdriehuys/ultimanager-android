package com.ultimanager.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ultimanager.R;
import com.ultimanager.models.Player;
import com.ultimanager.models.Possession;
import com.ultimanager.viewmodels.PlayerListViewModel;

import java.util.List;


public class DefenseFragment extends Fragment implements View.OnClickListener {
    public final static String ARG_POINT_ID = "POINT_ID";

    private final static String TAG = DefenseFragment.class.getSimpleName();

    private DefenseListener eventListener;
    private long pointId;
    private ViewGroup playerViewGroup;

    public interface DefenseListener {
        void onOpponentScored();

        /**
         * Triggered when the opposing team turns over the disc.
         *
         * @param reason The reason the disc was turned over.
         * @param defensivePlayer If the turnover was the result of a D, this will be the player who
         *                        got the D. Otherwise this parameter is null.
         */
        void onOpponentTurnover(Possession.Reason reason, @Nullable Player defensivePlayer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            eventListener = (DefenseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement 'DefenseListener'.");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_opponent_scored:
                eventListener.onOpponentScored();
                break;
            case R.id.btn_throwaway:
                eventListener.onOpponentTurnover(Possession.Reason.TURN, null);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            pointId = args.getLong(ARG_POINT_ID, -1);
        } else {
            Log.e(TAG, "Didn't receive a point ID from the parent activity.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(R.layout.fragment_defense, container, false);

        Button opponentScoredButton = fragmentRoot.findViewById(R.id.btn_opponent_scored);
        opponentScoredButton.setOnClickListener(this);

        Button throwawayButton = fragmentRoot.findViewById(R.id.btn_throwaway);
        throwawayButton.setOnClickListener(this);

        playerViewGroup = fragmentRoot.findViewById(R.id.defense_player_buttons);

        PlayerListViewModel playerList = ViewModelProviders.of(this).get(PlayerListViewModel.class);
        playerList.loadPlayersForPoint(pointId);
        playerList.getPlayerList().observe(this, this::displayPlayerButtons);

        return fragmentRoot;
    }

    private void displayPlayerButtons(List<Player> players) {
        playerViewGroup.removeAllViews();

        for (Player player : players) {
            Button btn = new Button(getContext());
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setOnClickListener(view -> eventListener.onOpponentTurnover(Possession.Reason.D, player));
            btn.setText(getString(R.string.player_with_number, player.name, player.number));

            playerViewGroup.addView(btn);
        }
    }
}
