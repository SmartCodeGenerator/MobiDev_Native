package edu.chnu.mobidev_native.view.viewholder.listinfo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.chnu.mobidev_native.databinding.ListItemBinding;
import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.util.listitem.ListItemViewHolderOnClickListenerUtils;

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    private final ListItemBinding binding;
    private final ListItemViewHolderOnClickListenerUtils utils;

    private ListItemViewHolder(@NonNull ListItemBinding binding,
                               ListItemViewHolderOnClickListenerUtils utils) {
        super(binding.getRoot());
        this.binding = binding;
        this.utils = utils;
    }

    public void bind(@NonNull ListItem item) {
        binding.setListItem(item);
        binding.executePendingBindings();

        binding.listItemDescription
                .setOnClickListener(utils.getListItemDescriptionOnClickListener(item));
    }

    public static ListItemViewHolder from(@NonNull ViewGroup parent,
                                          ListItemViewHolderOnClickListenerUtils utils) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        @NonNull ListItemBinding binding = ListItemBinding
                .inflate(inflater, parent, false);
        return new ListItemViewHolder(binding, utils);
    }
}
