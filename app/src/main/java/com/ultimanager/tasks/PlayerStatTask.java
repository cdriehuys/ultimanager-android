package com.ultimanager.tasks;

/**
 * Created by elijahlong on 12/8/17.
 */

import android.os.AsyncTask;
import android.content.Context;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Player;

import java.lang.ref.WeakReference;


public class PlayerStatTask extends AsyncTask<Void, Void, int[]> {
    private static final int NUMBER_OF_STATS = 5;

    public interface EventListener {
        void onStatsLoaded(int[] arr);
    }

    private Player player;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public PlayerStatTask(Context context, Player player, EventListener listener) {
        this.player = player;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected int[] doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context.getApplicationContext());

            int[] statArr = new int[NUMBER_OF_STATS];

            statArr[0] = db.players().getPlayerThrowsStat(player.id); // # of throws
            statArr[1] = db.players().getPlayerTurnsStat(player.id); // # of turns
            statArr[2] = db.players().getPlayerPointsPlayedStat(player.id); // #points played
            statArr[3] = db.players().getTotalPointsPlayedStat();
            statArr[4] = db.players().getPlayerThrowsStat(player.id);

            return statArr;
        }

        return null;
    }

    @Override
    protected void onPostExecute(int[] arr) {
        PlayerStatTask.EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onStatsLoaded(arr);
        }
    }

}
