package edu.chnu.mobidev_native.util.suggestion;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;

public class PurchaseSuggestionDiffCallback extends DiffUtil.ItemCallback<PurchaseSuggestion> {
    @Override
    public boolean areItemsTheSame(@NonNull PurchaseSuggestion oldItem,
                                   @NonNull PurchaseSuggestion newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull PurchaseSuggestion oldItem,
                                      @NonNull PurchaseSuggestion newItem) {
        return oldItem.getName().equals(newItem.getName()) &&
                oldItem.getPrice() == newItem.getPrice() &&
                oldItem.getImageBytes().equals(newItem.getImageBytes());
    }
}
