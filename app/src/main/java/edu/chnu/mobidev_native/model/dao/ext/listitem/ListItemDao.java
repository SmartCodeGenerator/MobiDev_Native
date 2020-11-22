package edu.chnu.mobidev_native.model.dao.ext.listitem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.chnu.mobidev_native.model.dao.BaseDao;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.relation.ListWithItems;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

@Dao
public interface ListItemDao extends BaseDao<ListItem> {

    @Transaction
    @Query("select * from shopping_list where id = :ownerId")
    LiveData<ListWithItems> getAll(int ownerId);
}
