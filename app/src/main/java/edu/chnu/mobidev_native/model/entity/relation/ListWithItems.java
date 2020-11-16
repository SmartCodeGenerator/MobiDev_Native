package edu.chnu.mobidev_native.model.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

public class ListWithItems {
    @Embedded
    public ShoppingList shoppingList;

    @Relation(
            parentColumn = "id",
            entityColumn = "owner_id"
    )
    public List<ListItem> listItems;
}
