package edu.chnu.mobidev_native.viewmodel.suggestion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import edu.chnu.mobidev_native.api.PurchaserNetwork;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PurchaseSuggestionViewModel extends AndroidViewModel {

    private final MutableLiveData<List<PurchaseSuggestion>> purchaseSuggestions;

    public PurchaseSuggestionViewModel(@NonNull Application application) {
        super(application);
        purchaseSuggestions = new MutableLiveData<>();
    }

    public LiveData<List<PurchaseSuggestion>> getPurchaseSuggestions() {
        return purchaseSuggestions;
    }

    public void fetchSuggestions() {
        PurchaserNetwork
                .getInstance().retrofitService().getSuggestions()
                .enqueue(new Callback<List<PurchaseSuggestion>>() {
            @Override
            public void onResponse(@NotNull Call<List<PurchaseSuggestion>> call,
                                   @NotNull Response<List<PurchaseSuggestion>> response) {
                purchaseSuggestions.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<PurchaseSuggestion>> call,
                                  @NotNull Throwable t) {
                Timber.i("Failure: %s", t.getMessage());
            }
        });
    }
}
