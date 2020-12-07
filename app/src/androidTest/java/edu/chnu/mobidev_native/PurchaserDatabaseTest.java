package edu.chnu.mobidev_native;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import edu.chnu.mobidev_native.model.dao.ext.listitem.ListItemDao;
import edu.chnu.mobidev_native.model.dao.ext.shoppinglist.ShoppingListDao;
import edu.chnu.mobidev_native.model.database.PurchaserDatabase;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.relation.ListWithItems;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.util.LiveDataSync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PurchaserDatabaseTest {

    private PurchaserDatabase database;

    private ShoppingListDao shoppingListDao;
    private ListItemDao listItemDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        database = Room.inMemoryDatabaseBuilder(context, PurchaserDatabase.class)
                .allowMainThreadQueries()
                .build();

        shoppingListDao = database.shoppingListDao();
        listItemDao = database.listItemDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void shoppingListDaoListItemDaoInsertGetAll() throws Exception  {
        ShoppingList list1 = new ShoppingList("list 1",
                false,"");
        shoppingListDao.insert(list1);

        List<ShoppingList> query1 = LiveDataSync.getOrAwaitValue(shoppingListDao.getAll());

        assertEquals(1, query1.size());

        ShoppingList query1List = query1.get(0);

        assertEquals(query1List.getListName(), list1.getListName());

        ListItem item1 = new ListItem("i1",1,false, query1List.getId());
        listItemDao.insert(item1);

        ListWithItems query2 = LiveDataSync
                .getOrAwaitValue(listItemDao.getAll(query1List.getId()));

        assertEquals(1, query2.listItems.size());

        ListItem query2Item = query2.listItems.get(0);

        assertEquals(query1List.getId(), query2Item.getOwnerId());
        assertEquals(item1.getDescription(), query2Item.getDescription());
    }

    @Test
    public void shoppingListDaoUpdateDelete() throws Exception {
        ShoppingList list1 = new ShoppingList("11.11", false, "");
        shoppingListDao.insert(list1);

        List<ShoppingList> query1 = LiveDataSync.getOrAwaitValue(shoppingListDao.getAll());

        assertEquals(list1.getListName(), query1.get(0).getListName());

        query1.get(0).setCompleted(true);
        shoppingListDao.update(query1.get(0));

        List<ShoppingList> query2 = LiveDataSync.getOrAwaitValue(shoppingListDao.getAll());

        assertTrue(query2.get(0).isCompleted());

        shoppingListDao.delete(query2.get(0));

        List<ShoppingList> query3 = LiveDataSync.getOrAwaitValue(shoppingListDao.getAll());

        assertTrue(query3.isEmpty());
    }

    @Test
    public void listItemDaoUpdate() throws Exception {
        ShoppingList mainList = new ShoppingList("l1", false, "");
        shoppingListDao.insert(mainList);

        List<ShoppingList> query1 = LiveDataSync.getOrAwaitValue(shoppingListDao.getAll());
        int ownerId = query1.get(0).getId();

        ListItem item1 = new ListItem("item1", 1, true, ownerId);
        listItemDao.insert(item1);

        ListWithItems query2 = LiveDataSync.getOrAwaitValue(listItemDao.getAll(ownerId));
        item1 = query2.listItems.get(0);

        assertEquals("item1", item1.getDescription());

        item1.setChecked(false);
        listItemDao.update(item1);

        ListWithItems query3 = LiveDataSync.getOrAwaitValue(listItemDao.getAll(item1.getOwnerId()));

        assertFalse(query3.listItems.get(0).isChecked());
    }
}
