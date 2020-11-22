package edu.chnu.mobidev_native.util.listitem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;

public class ListItemDiffCallback extends DiffUtil.ItemCallback<ListItem> {
    @Override
    public boolean areItemsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
        return oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getPrice() == newItem.getPrice() &&
                oldItem.isChecked() == newItem.isChecked();
    }
}
