package edu.chnu.mobidev_native.model.dao.ext.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import edu.chnu.mobidev_native.model.dao.BaseDao;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

@Dao
public interface ShoppingListDao extends BaseDao<ShoppingList> {

    @Query("select * from shopping_list order by id desc")
    LiveData<List<ShoppingList>> getAll();

    @Delete
    void delete(ShoppingList list);
}
