package edu.chnu.mobidev_native.api;

import com.squareup.moshi.Moshi;

import edu.chnu.mobidev_native.api.service.MockyApiService;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class PurchaserNetwork {

    private static PurchaserNetwork instance;

    private final Retrofit retrofit;

    private PurchaserNetwork() {
        Moshi moshi = new Moshi.Builder().build();

        String BASE_URL = "https://run.mocky.io/v3/";
        retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build();
    }

    public static PurchaserNetwork getInstance() {
        if (instance == null) {
            instance = new PurchaserNetwork();
        }
        return instance;
    }

    public MockyApiService retrofitService() {
        return retrofit.create(MockyApiService.class);
    }
}
