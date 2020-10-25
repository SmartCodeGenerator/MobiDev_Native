package edu.chnu.mobidev_native;

import android.app.Application;

import timber.log.Timber;

public class PurchaserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
