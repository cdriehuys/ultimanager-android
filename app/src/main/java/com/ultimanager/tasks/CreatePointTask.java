package com.ultimanager.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ultimanager.models.AppDatabase;
import com.ultimanager.models.Game;
import com.ultimanager.models.Point;
import com.ultimanager.models.PointPlayer;

import java.lang.ref.WeakReference;


/**
 * Task to create a new point in a game.
 *
 * The task creates a new point marked as "In Progress", and sets the players for the point.
 */
public class CreatePointTask extends AsyncTask<Long, Void, Point> {
    /**
     * Interface for listening to events during the point creation process.
     */
    public interface EventListener {
        /**
         * Triggered after the new point has been saved in the database.
         *
         * @param point The point that was created.
         */
        void onPointCreated(Point point);
    }

    private static final String TAG = CreatePointTask.class.getSimpleName();

    private Game game;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<EventListener> eventListenerWeakReference;

    /**
     * Create a new task for creating a point.
     *
     * @param context The context to use when getting a reference to the application's database.
     * @param game The game to create the point in.
     * @param eventListener A listener to monitor the events during the point's creation.
     */
    public CreatePointTask(Context context, Game game, EventListener eventListener) {
        this.game = game;

        this.contextWeakReference = new WeakReference<>(context);
        this.eventListenerWeakReference = new WeakReference<>(eventListener);
    }

    /**
     * Create a new point with a set of players and save it to the database.
     *
     * @param playerIds The IDs of the players who are playing in the point being created.
     *
     * @return The newly created point.
     */
    @Override
    protected Point doInBackground(Long... playerIds) {
        Context context = contextWeakReference.get();
        if (context != null) {
            AppDatabase db = AppDatabase.getAppDatabase(context.getApplicationContext());

            Point point = new Point(game.id, Point.Result.IN_PROGRESS);
            point.setId(db.points().addPoint(point));

            Log.v(TAG, "Created point with ID: " + point.getId());

            for (Long id : playerIds) {
                PointPlayer pointPlayer = new PointPlayer();
                pointPlayer.playerId = id;
                pointPlayer.pointId = point.getId();

                db.pointPlayers().addPointPlayer(pointPlayer);

                Log.v(TAG, "Added point player:\n\tPlayer ID: " + id + "\n\tPoint ID: " + point.getId());
            }

            return point;
        }

        Log.w(TAG, "Did not create point because context was null.");

        return null;
    }

    /**
     * Notify the event listener once the point has been created.
     *
     * @param point The point that was just created.
     */
    @Override
    protected void onPostExecute(Point point) {
        // If we didn't create a point, no-op.
        if (point == null) {
            return;
        }

        // Only notify the listener if it's still around.
        EventListener listener = eventListenerWeakReference.get();
        if (listener != null) {
            listener.onPointCreated(point);
        }
    }
}
