package edu.chnu.mobidev_native.model.repository.ext.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.chnu.mobidev_native.model.dao.ext.shoppinglist.ShoppingListDao;
import edu.chnu.mobidev_native.model.database.PurchaserDatabase;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.model.repository.BaseRepository;

public class ShoppingListRepository implements BaseRepository<ShoppingList> {

    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> shoppingLists;

    public ShoppingListRepository(Application application) {
        PurchaserDatabase database = PurchaserDatabase.getDatabase(application);
        shoppingListDao = database.shoppingListDao();
        shoppingLists = shoppingListDao.getAll();
    }

    public LiveData<List<ShoppingList>> getShoppingLists() {
        return shoppingLists;
    }

    @Override
    public void insert(ShoppingList shoppingList) {
        PurchaserDatabase.databaseWriteExecutor.execute(() -> shoppingListDao.insert(shoppingList));
    }

    @Override
    public void update(ShoppingList shoppingList) {
        PurchaserDatabase.databaseWriteExecutor.execute(() -> shoppingListDao.update(shoppingList));
    }

    public void delete(ShoppingList list) {
        PurchaserDatabase.databaseWriteExecutor.execute(() -> shoppingListDao.delete(list));
    }
}
