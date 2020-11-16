package edu.chnu.mobidev_native.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface BaseDao<TEntity> {

    @Insert
    void insert(TEntity entity);

    @Update
    void update(TEntity entity);
}
