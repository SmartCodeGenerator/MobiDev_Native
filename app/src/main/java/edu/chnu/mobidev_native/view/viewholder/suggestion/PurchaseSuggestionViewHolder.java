package edu.chnu.mobidev_native.view.viewholder.suggestion;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.chnu.mobidev_native.databinding.PurchaseSuggestionBinding;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;

public class PurchaseSuggestionViewHolder extends RecyclerView.ViewHolder {

    private final PurchaseSuggestionBinding binding;

    private PurchaseSuggestionViewHolder(@NonNull PurchaseSuggestionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull PurchaseSuggestion suggestion) {
        binding.setSuggestion(suggestion);
        binding.executePendingBindings();
    }

    public static PurchaseSuggestionViewHolder from (@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        @NonNull PurchaseSuggestionBinding binding = PurchaseSuggestionBinding
                .inflate(inflater, parent, false);
        return new PurchaseSuggestionViewHolder(binding);
    }
}
