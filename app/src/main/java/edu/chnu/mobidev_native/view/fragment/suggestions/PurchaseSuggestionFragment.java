package edu.chnu.mobidev_native.view.fragment.suggestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import edu.chnu.mobidev_native.R;
import edu.chnu.mobidev_native.util.suggestion.PurchaseSuggestionDiffCallback;
import edu.chnu.mobidev_native.view.adapter.suggestion.PurchaseSuggestionAdapter;
import edu.chnu.mobidev_native.viewmodel.suggestion.PurchaseSuggestionViewModel;

public class PurchaseSuggestionFragment extends Fragment {

    public PurchaseSuggestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_purchase_suggestion, container, false);

        PurchaseSuggestionViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(PurchaseSuggestionViewModel.class);

        PurchaseSuggestionAdapter adapter =
                new PurchaseSuggestionAdapter(new PurchaseSuggestionDiffCallback());

        RecyclerView recyclerView = view.findViewById(R.id.suggestions_list);
        recyclerView.setAdapter(adapter);

        viewModel.getPurchaseSuggestions().observe(getViewLifecycleOwner(), suggestions -> {
            if (suggestions != null) {
                adapter.submitList(suggestions);
            }
        });

        return view;
    }
}