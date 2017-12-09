package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Point;
import com.ultimanager.models.Possession;

import java.lang.ref.WeakReference;


public class CreatePossessionTask extends AsyncTask<Void, Void, Possession> {
    public interface EventListener {
        void onPossessionCreated(Possession possession);
    }

    private Possession possession;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public CreatePossessionTask(Context context, Possession possession, EventListener listener) {
        this.possession = possession;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Possession doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context.getApplicationContext());

            possession.setId(db.possessions().addPossession(possession));

            return possession;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Possession possession) {
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onPossessionCreated(possession);
        }
    }
}
