package com.ultimanager.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.ultimanager.R;
import com.ultimanager.models.Player;
import com.ultimanager.models.Throw;
import com.ultimanager.viewmodels.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;


public class OffenseFragment extends Fragment {
    public final static String ARG_POINT_ID = "POINT_ID";

    public interface OnThrowListener {
        void onThrowRecorded(Player thrower, Player receiver, Throw.Type type, Throw.Result result);
    }

    private final static String TAG = OffenseFragment.class.getSimpleName();

    private Button recordThrowButton;
    private List<Player> players;
    private long pointId;
    private OnThrowListener onThrowListener;
    private Player receiver;
    private Player thrower;
    private PlayerViewModel playerViewModel;
    private RadioButton backhandRadio;
    private RadioButton completionRadio;
    private RadioButton flickRadio;
    private RadioButton goalRadio;
    private RadioButton otherRadio;
    private RadioButton turnoverRadio;
    private ViewGroup receiverRG;
    private ViewGroup throwerRG;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onThrowListener = (OnThrowListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement 'OnThrowListener'");
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

        players = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(R.layout.fragment_offense, container, false);

        recordThrowButton = fragmentRoot.findViewById(R.id.btn_record_throw);
        recordThrowButton.setOnClickListener(view -> handleRecordThrow());

        receiverRG = fragmentRoot.findViewById(R.id.rg_receiver);
        throwerRG = fragmentRoot.findViewById(R.id.rg_thrower);

        backhandRadio = fragmentRoot.findViewById(R.id.radio_backhand);
        completionRadio = fragmentRoot.findViewById(R.id.radio_completion);
        flickRadio = fragmentRoot.findViewById(R.id.radio_flick);
        goalRadio = fragmentRoot.findViewById(R.id.radio_goal);
        otherRadio = fragmentRoot.findViewById(R.id.radio_other);
        turnoverRadio = fragmentRoot.findViewById(R.id.radio_turnover);

        // We only have to set click handlers for the results since there is no default option
        completionRadio.setOnClickListener(view -> enableRecordThrowButtonIfApplicable());
        goalRadio.setOnClickListener(view -> enableRecordThrowButtonIfApplicable());
        turnoverRadio.setOnClickListener(view -> enableRecordThrowButtonIfApplicable());

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.getPlayersForPoint(pointId).observe(this, players -> {
            this.players=players;
            resetUI(players);
        });

        return fragmentRoot;
    }

    private void handleRecordThrow() {
        Throw.Type throwType;
        if (backhandRadio.isChecked()) {
            throwType = Throw.Type.BACKHAND;
        } else if (flickRadio.isChecked()) {
            throwType = Throw.Type.FOREHAND;
        } else {
            throwType = Throw.Type.OTHER;
        }

        Throw.Result throwResult;
        if (completionRadio.isChecked()) {
            throwResult = Throw.Result.COMPLETE;
        } else if (goalRadio.isChecked()) {
            throwResult = Throw.Result.SCORE;
        } else {
            throwResult = Throw.Result.TURN;
        }

        onThrowListener.onThrowRecorded(thrower, receiver, throwType, throwResult);

        resetUI(players);
    }

    private void resetUI(List<Player> players) {
        otherRadio.setChecked(true);

        completionRadio.setChecked(true);
        goalRadio.setChecked(false);
        turnoverRadio.setChecked(false);

        recordThrowButton.setEnabled(false);

        throwerRG.removeAllViews();
        for (Player player : players) {
            RadioButton btn = new RadioButton(getContext());
            btn.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText(getString(R.string.player_with_number, player.name, player.number));

            btn.setOnClickListener(view -> setThrower(player));

            throwerRG.addView(btn);
        }

        receiverRG.removeAllViews();
        for (Player player : players) {
            RadioButton btn = new RadioButton(getContext());
            btn.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText(getString(R.string.player_with_number, player.name, player.number));

            btn.setOnClickListener(view -> setReceiver(player));

            receiverRG.addView(btn);
        }
    }

    private void setThrower(Player p){
        Log.v("TAG","Thrower set as " + p.name);
        thrower = p;
        enableRecordThrowButtonIfApplicable();
    }
    private void setReceiver(Player p) {
        Log.v("TAG","receiver set as " + p.name);
        receiver = p;
        enableRecordThrowButtonIfApplicable();
    }

    private void enableRecordThrowButtonIfApplicable(){
        if(receiver != null && thrower != null &&
                (completionRadio.isChecked() ||
                turnoverRadio.isChecked() ||
                goalRadio.isChecked())){
            recordThrowButton.setEnabled(true);
        }
    }

}
