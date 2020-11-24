package edu.chnu.mobidev_native.view.adapter.suggestion;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import edu.chnu.mobidev_native.util.suggestion.PurchaseSuggestionDiffCallback;
import edu.chnu.mobidev_native.view.viewholder.suggestion.PurchaseSuggestionViewHolder;

public class PurchaseSuggestionAdapter extends ListAdapter<PurchaseSuggestion,
        PurchaseSuggestionViewHolder> {

    public PurchaseSuggestionAdapter(@NonNull PurchaseSuggestionDiffCallback diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PurchaseSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        return PurchaseSuggestionViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseSuggestionViewHolder holder, int position) {
        PurchaseSuggestion suggestion = getItem(position);
        holder.bind(suggestion);
    }
}
