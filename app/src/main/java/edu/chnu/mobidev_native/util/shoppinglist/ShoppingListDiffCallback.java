package edu.chnu.mobidev_native.util.shoppinglist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;

public class ShoppingListDiffCallback extends DiffUtil.ItemCallback<ShoppingList> {
    @Override
    public boolean areItemsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ShoppingList oldItem,
                                      @NonNull ShoppingList newItem) {
        return oldItem.getListName().equals(newItem.getListName()) &&
                oldItem.isCompleted() == newItem.isCompleted() &&
                oldItem.getTimeCreated().equals(newItem.getTimeCreated());
    }
}
