package edu.chnu.mobidev_native.model.repository.ext.suggestion;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import edu.chnu.mobidev_native.api.PurchaserNetwork;
import edu.chnu.mobidev_native.model.dao.ext.suggestion.PurchaseSuggestionDao;
import edu.chnu.mobidev_native.model.database.PurchaseSuggestionDatabase;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestionDTO;
import edu.chnu.mobidev_native.util.suggestion.PurchaseSuggestionTypeConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PurchaseSuggestionRepository {
    private final PurchaseSuggestionDao purchaseSuggestionDao;

    public PurchaseSuggestionRepository(Application application) {
        PurchaseSuggestionDatabase database = PurchaseSuggestionDatabase.getDatabase(application);
        purchaseSuggestionDao = database.purchaseSuggestionDao();
    }

    public LiveData<List<PurchaseSuggestion>> getPurchaseSuggestions() {
        return Transformations.map(purchaseSuggestionDao.getPurchaseSuggestionData(),
                PurchaseSuggestionTypeConverter::asDomainModelList);
    }

    public void refreshSuggestions() {
        PurchaserNetwork
                .getInstance().retrofitService().getSuggestions()
                .enqueue(new Callback<List<PurchaseSuggestionDTO>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<PurchaseSuggestionDTO>> call,
                                           @NotNull Response<List<PurchaseSuggestionDTO>>
                                                   response) {
                        List<PurchaseSuggestionDTO> dtoList = response.body();
                        PurchaseSuggestionDatabase.databaseWriteExecutor.execute(() ->
                                purchaseSuggestionDao
                                .insertAll(PurchaseSuggestionTypeConverter
                                        .asDataArray(Objects.requireNonNull(dtoList))));
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<PurchaseSuggestionDTO>> call,
                                          @NotNull Throwable t) {
                        Timber.i("Failure: %s", t.getMessage());
                    }
                });
    }
}
