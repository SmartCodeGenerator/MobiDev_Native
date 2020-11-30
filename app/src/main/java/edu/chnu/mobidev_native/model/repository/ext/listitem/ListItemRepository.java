package edu.chnu.mobidev_native.model.repository.ext.listitem;

import android.app.Application;

import androidx.lifecycle.LiveData;

import edu.chnu.mobidev_native.model.dao.ext.listitem.ListItemDao;
import edu.chnu.mobidev_native.model.database.PurchaserDatabase;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.relation.ListWithItems;
import edu.chnu.mobidev_native.model.repository.BaseRepository;

public class ListItemRepository implements BaseRepository<ListItem> {

    private final ListItemDao listItemDao;

    public ListItemRepository(Application application) {
        PurchaserDatabase database = PurchaserDatabase.getDatabase(application);
        listItemDao = database.listItemDao();
    }

    public LiveData<ListWithItems> getListItems(int ownerId) {
        return listItemDao.getAll(ownerId);
    }

    @Override
    public void insert(ListItem listItem) {
        PurchaserDatabase.databaseWriteExecutor.execute(() -> listItemDao.insert(listItem));
    }

    @Override
    public void update(ListItem listItem) {
        PurchaserDatabase.databaseWriteExecutor.execute(() -> listItemDao.update(listItem));
    }
}
