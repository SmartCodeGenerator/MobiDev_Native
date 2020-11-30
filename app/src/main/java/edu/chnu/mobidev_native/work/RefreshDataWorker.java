package edu.chnu.mobidev_native.work;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import edu.chnu.mobidev_native.model.repository.ext.suggestion.PurchaseSuggestionRepository;
import retrofit2.HttpException;

public class RefreshDataWorker extends Worker {

    private final Application application;

    public static final String WORK_NAME = "RefreshDataWorker";

    public RefreshDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,
                             Application application) {
        super(context, workerParams);
        this.application = application;
    }

    @NonNull
    @Override
    public Result doWork() {
        PurchaseSuggestionRepository repository = new PurchaseSuggestionRepository(application);

        try {
            repository.refreshSuggestions();
            return Result.success();
        } catch (HttpException exception) {
            return Result.retry();
        }
    }
}
