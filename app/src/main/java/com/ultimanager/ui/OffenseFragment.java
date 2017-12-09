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

import java.util.List;


public class OffenseFragment extends Fragment implements RadioButton.OnClickListener {
    public final static String ARG_POINT_ID = "POINT_ID";

    public interface OnThrowListener {
        void onThrowRecorded(Player thrower, Player receiver, Throw.Type type, Throw.Result result);
    }

    private final static String TAG = OffenseFragment.class.getSimpleName();

    private Button recordThrowButton;
    private OnThrowListener onThrowListener;
    private long pointId;
    private Player receiver;
    private Player thrower;
    private PlayerViewModel playerViewModel;
    private RadioButton backhandRadio;
    private RadioButton completionRadio;
    private RadioButton flickRadio;
    private RadioButton goalRadio;
    private RadioButton otherRadio;
    private RadioButton turnoverRadio;
    private ViewGroup receiverButtons;
    private ViewGroup receiverInfo;
    private ViewGroup throwInfo;
    private ViewGroup throwerButtons;
    private ViewGroup throwerInfo;

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
    public void onClick(View view) {
        RadioButton radio = (RadioButton) view;

        switch (radio.getId()) {
            case R.id.radio_completion:
            case R.id.radio_goal:
            case R.id.radio_turnover:
                if (radio.isChecked()) {
                    recordThrowButton.setEnabled(true);
                }
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
        View fragmentRoot = inflater.inflate(R.layout.fragment_offense, container, false);

        recordThrowButton = fragmentRoot.findViewById(R.id.btn_record_throw);
        recordThrowButton.setOnClickListener(view -> handleRecordThrow());

        receiverButtons = fragmentRoot.findViewById(R.id.receiver_buttons);
        receiverInfo = fragmentRoot.findViewById(R.id.receiver_info);
        throwInfo = fragmentRoot.findViewById(R.id.throw_info);
        throwerButtons = fragmentRoot.findViewById(R.id.thrower_buttons);
        throwerInfo = fragmentRoot.findViewById(R.id.thrower_info);

        backhandRadio = fragmentRoot.findViewById(R.id.radio_backhand);
        completionRadio = fragmentRoot.findViewById(R.id.radio_completion);
        flickRadio = fragmentRoot.findViewById(R.id.radio_flick);
        goalRadio = fragmentRoot.findViewById(R.id.radio_goal);
        otherRadio = fragmentRoot.findViewById(R.id.radio_other);
        turnoverRadio = fragmentRoot.findViewById(R.id.radio_turnover);

        // We only have to set click handlers for the results since there is no default option
        completionRadio.setOnClickListener(this);
        goalRadio.setOnClickListener(this);
        turnoverRadio.setOnClickListener(this);

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.getPlayersForPoint(pointId).observe(this, this::resetUI);

        return fragmentRoot;
    }

    private void disableAllInView(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setEnabled(false);
        }
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

        resetUI(playerViewModel.getPlayerList().getValue());
    }

    private void resetUI(List<Player> players) {
        throwerInfo.setVisibility(View.VISIBLE);
        receiverInfo.setVisibility(View.INVISIBLE);
        throwInfo.setVisibility(View.INVISIBLE);
        recordThrowButton.setEnabled(false);
        recordThrowButton.setVisibility(View.VISIBLE);

        throwerButtons.removeAllViews();
        for (Player player : players) {
            Button btn = new Button(getContext());
            btn.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText(getString(R.string.player_with_number, player.name, player.number));

            btn.setOnClickListener(view -> {
                Button b = (Button) view;
                SpannableString spanString = new SpannableString(b.getText());
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                b.setText(spanString);

                disableAllInView(throwerButtons);
                receiverInfo.setVisibility(View.VISIBLE);

                thrower = player;
            });

            throwerButtons.addView(btn);
        }

        receiverButtons.removeAllViews();
        for (Player player : players) {
            Button btn = new Button(getContext());
            btn.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText(getString(R.string.player_with_number, player.name, player.number));

            btn.setOnClickListener(view -> {
                Button b = (Button) view;
                SpannableString spanString = new SpannableString(b.getText());
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                b.setText(spanString);

                disableAllInView(receiverButtons);
                throwInfo.setVisibility(View.VISIBLE);

                receiver = player;
            });

            receiverButtons.addView(btn);
        }
    }
}
