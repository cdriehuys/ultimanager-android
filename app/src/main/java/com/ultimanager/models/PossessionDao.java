package com.ultimanager.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;


@Dao
public interface PossessionDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    long addPossession(Possession possession);
}
