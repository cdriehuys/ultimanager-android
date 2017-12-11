package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Throw;

import java.lang.ref.WeakReference;


public class CreateThrowTask extends AsyncTask<Void, Void, Throw> {
    public interface EventListener {
        void onThrowCreated(Throw throw_);
    }

    private Throw throw_;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public CreateThrowTask(Context context, Throw throw_, EventListener listener) {
        this.throw_ = throw_;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Throw doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context);

            throw_.setId(db.throws_().addThrow(throw_));

            return throw_;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Throw throw_) {
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onThrowCreated(throw_);
        }
    }
}
