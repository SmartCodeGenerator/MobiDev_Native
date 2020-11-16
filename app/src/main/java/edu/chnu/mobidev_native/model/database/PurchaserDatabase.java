package edu.chnu.mobidev_native.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.chnu.mobidev_native.model.dao.ext.listitem.ListItemDao;
import edu.chnu.mobidev_native.model.dao.ext.shoppinglist.ShoppingListDao;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

@Database(entities = {ShoppingList.class, ListItem.class}, version = 1, exportSchema = false)
public abstract class PurchaserDatabase extends RoomDatabase {

    public abstract ShoppingListDao shoppingListDao();
    public abstract ListItemDao listItemDao();

    private static volatile PurchaserDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors
            .newFixedThreadPool(NUMBER_OF_THREADS);

    public static PurchaserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PurchaserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PurchaserDatabase.class, "purchaser_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
