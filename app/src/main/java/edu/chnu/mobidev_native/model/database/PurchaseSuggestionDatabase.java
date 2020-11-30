package edu.chnu.mobidev_native.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.chnu.mobidev_native.model.dao.ext.suggestion.PurchaseSuggestionDao;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestionData;

@Database(entities = {PurchaseSuggestionData.class}, version = 1)
public abstract class PurchaseSuggestionDatabase extends RoomDatabase {

    public abstract PurchaseSuggestionDao purchaseSuggestionDao();

    private static volatile PurchaseSuggestionDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors
            .newFixedThreadPool(NUMBER_OF_THREADS);

    public static PurchaseSuggestionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PurchaseSuggestionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PurchaseSuggestionDatabase.class, "purchase_suggestion_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
