package edu.chnu.mobidev_native.view.viewholder.shoppinglist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.chnu.mobidev_native.databinding.ShoppingListBinding;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.util.shoppinglist.ShoppingListViewHolderOnClickListenerUtils;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    private final ShoppingListBinding binding;
    private final ShoppingListViewHolderOnClickListenerUtils utils;

    private ShoppingListViewHolder(@NonNull ShoppingListBinding binding,
                                   ShoppingListViewHolderOnClickListenerUtils utils) {
        super(binding.getRoot());
        this.binding = binding;
        this.utils = utils;
    }

    public void bind(@NonNull ShoppingList item) {
        binding.setShoppingList(item);
        binding.executePendingBindings();

        binding.deleteListButton.setOnClickListener(utils.getDeleteButtonOnClickListener(item));
        binding.infoListButton.setOnClickListener(utils.getInfoButtonOnClickListener(item));
    }

    public static ShoppingListViewHolder from(@NonNull ViewGroup parent,
                                              ShoppingListViewHolderOnClickListenerUtils utils) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        @NonNull ShoppingListBinding binding = ShoppingListBinding
                .inflate(inflater, parent, false);
        return new ShoppingListViewHolder(binding, utils);
    }
}
