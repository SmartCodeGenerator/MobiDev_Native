package edu.chnu.mobidev_native.viewmodel.suggestion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import edu.chnu.mobidev_native.model.repository.ext.suggestion.PurchaseSuggestionRepository;

public class PurchaseSuggestionViewModel extends AndroidViewModel {

    private final LiveData<List<PurchaseSuggestion>> purchaseSuggestions;

    public PurchaseSuggestionViewModel(@NonNull Application application) {
        super(application);
        PurchaseSuggestionRepository purchaseSuggestionRepository =
                new PurchaseSuggestionRepository(application);
        purchaseSuggestions = purchaseSuggestionRepository.getPurchaseSuggestions();
    }

    public LiveData<List<PurchaseSuggestion>> getPurchaseSuggestions() {
        return purchaseSuggestions;
    }
}
