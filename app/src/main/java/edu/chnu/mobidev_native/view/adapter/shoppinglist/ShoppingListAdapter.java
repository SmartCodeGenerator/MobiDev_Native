package edu.chnu.mobidev_native.view.adapter.shoppinglist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.util.shoppinglist.ShoppingListDiffCallback;
import edu.chnu.mobidev_native.util.shoppinglist.ShoppingListViewHolderOnClickListenerUtils;
import edu.chnu.mobidev_native.view.viewholder.shoppinglist.ShoppingListViewHolder;

public class ShoppingListAdapter extends ListAdapter<ShoppingList, ShoppingListViewHolder> {

    private final ShoppingListViewHolderOnClickListenerUtils utils;

    public ShoppingListAdapter(@NonNull ShoppingListDiffCallback diffCallback,
                               ShoppingListViewHolderOnClickListenerUtils utils) {
        super(diffCallback);
        this.utils = utils;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingList item = getItem(position);
        holder.bind(item);
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShoppingListViewHolder.from(parent, utils);
    }
}
