package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Point;

import java.lang.ref.WeakReference;


public class LoadPointTask extends AsyncTask<Void, Void, Point> {
    public interface EventListener {
        void onPointLoaded(Point point);
    }

    private long pointId;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public LoadPointTask(Context context, long pointId, EventListener listener) {
        this.pointId = pointId;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Point doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context);

            return db.points().getById(pointId);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Point point) {
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onPointLoaded(point);
        }
    }
}
