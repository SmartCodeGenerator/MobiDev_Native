package edu.chnu.mobidev_native.work.factory;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;

import edu.chnu.mobidev_native.work.RefreshDataWorker;

public class PurchaserWorkerFactory extends WorkerFactory {
    private final Application application;

    public PurchaserWorkerFactory(Application application) {
        this.application = application;
    }

    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext,
                                         @NonNull String workerClassName,
                                         @NonNull WorkerParameters workerParameters) {
        if (RefreshDataWorker.class.getName().equals(workerClassName)) {
            return new RefreshDataWorker(appContext, workerParameters, application);
        }
        return null;
    }
}
