package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Point;

import java.lang.ref.WeakReference;


public class UpdatePointTask extends AsyncTask<Void, Void, Point> {
    public interface EventListener {
        void onPointUpdated(Point point);
    }

    private Point point;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public UpdatePointTask(Context context, Point point, EventListener listener) {
        this.point = point;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Point doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context.getApplicationContext());

            db.points().updatePoint(point);

            return point;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Point point) {
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onPointUpdated(point);
        }
    }
}
