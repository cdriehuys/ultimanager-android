package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Point;

import java.lang.ref.WeakReference;


public class CompletePointTask extends AsyncTask<Void, Void, Point> {
    /**
     * Interface for listening to events while the point is completed.
     */
    public interface EventListener {
        /**
         * Triggered after the point is completed and saved in the database.
         *
         * @param point The point that was completed.
         */
        void onPointCompleted(Point point);
    }

    private static final String TAG = CompletePointTask.class.getSimpleName();

    private long pointId;
    private Point.Result result;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    public CompletePointTask(Context context, long pointId, Point.Result result, EventListener listener) {
        this.pointId = pointId;
        this.result = result;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Point doInBackground(Void... voids) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context.getApplicationContext());

            Point point = db.points().getById(pointId);
            point.setResult(result);

            db.points().updatePoint(point);

            Log.d(TAG, "Set result of point with ID " + pointId + " to " + result.name());

            return point;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Point point) {
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onPointCompleted(point);
        }
    }
}
