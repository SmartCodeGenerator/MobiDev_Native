package edu.chnu.mobidev_native.model.dao.ext.suggestion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestionData;

@Dao
public interface PurchaseSuggestionDao {
    @Query("select * from purchase_suggestion")
    LiveData<List<PurchaseSuggestionData>> getPurchaseSuggestionData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PurchaseSuggestionData... data);
}
