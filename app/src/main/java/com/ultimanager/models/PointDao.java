package com.ultimanager.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


@Dao
public interface PointDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long addPoint(Point point);

    @Query("SELECT * FROM Point WHERE id = :id")
    Point getById(long id);

    @Update
    void updatePoint(Point point);
}
