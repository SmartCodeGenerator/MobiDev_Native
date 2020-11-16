package edu.chnu.mobidev_native.model.repository;

import androidx.lifecycle.LiveData;

public interface BaseRepository<TEntity> {

    void insert(TEntity entity);

    void update(TEntity entity);

}
