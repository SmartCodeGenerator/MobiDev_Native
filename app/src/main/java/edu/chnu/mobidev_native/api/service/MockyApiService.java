package edu.chnu.mobidev_native.api.service;

import java.util.List;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MockyApiService {
    @GET("ce742463-b159-4914-9c64-0a72f5a04375")
    Call<List<PurchaseSuggestion>> getSuggestions();
}
