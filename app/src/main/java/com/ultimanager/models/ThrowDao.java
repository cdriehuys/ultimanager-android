package com.ultimanager.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;


@Dao
public interface ThrowDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    long addThrow(Throw throw_);
}
