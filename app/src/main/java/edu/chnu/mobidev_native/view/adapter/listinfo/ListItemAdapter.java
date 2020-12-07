package edu.chnu.mobidev_native.view.adapter.listinfo;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.util.listitem.ListItemDiffCallback;
import edu.chnu.mobidev_native.util.listitem.ListItemViewHolderOnClickListenerUtils;
import edu.chnu.mobidev_native.view.viewholder.listinfo.ListItemViewHolder;

public class ListItemAdapter extends ListAdapter<ListItem, ListItemViewHolder> {

    private final ListItemViewHolderOnClickListenerUtils utils;

    public ListItemAdapter(@NonNull ListItemDiffCallback diffCallback,
                              ListItemViewHolderOnClickListenerUtils utils) {
        super(diffCallback);
        this.utils = utils;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListItemViewHolder.from(parent, utils);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItem item = getItem(position);
        holder.bind(item);
    }
}
