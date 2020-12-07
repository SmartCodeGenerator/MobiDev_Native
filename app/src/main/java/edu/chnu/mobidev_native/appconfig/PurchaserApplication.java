package edu.chnu.mobidev_native.appconfig;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.DelegatingWorkerFactory;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

import edu.chnu.mobidev_native.work.RefreshDataWorker;
import edu.chnu.mobidev_native.work.factory.PurchaserWorkerFactory;
import timber.log.Timber;

public class PurchaserApplication extends Application implements Configuration.Provider {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        Configuration myConfig = new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();

        WorkManager.initialize(this, myConfig);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest refreshDataWorkRequest = new PeriodicWorkRequest
                .Builder(RefreshDataWorker.class, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, refreshDataWorkRequest);
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        DelegatingWorkerFactory workerFactory = new DelegatingWorkerFactory();
        workerFactory.addFactory(new PurchaserWorkerFactory(this));

        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.INFO).setWorkerFactory(workerFactory).build();
    }
}
