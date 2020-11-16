package edu.chnu.mobidev_native.viewmodel.listinfo.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.chnu.mobidev_native.viewmodel.listinfo.ListInfoViewModel;

public class ListInfoViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ListInfoViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListInfoViewModel.class)) {
            return (T) new ListInfoViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
