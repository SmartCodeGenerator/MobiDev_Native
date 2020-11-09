package edu.chnu.mobidev_native;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.chnu.mobidev_native.viewmodels.StartViewModel;

public class StartViewModelFactory implements ViewModelProvider.Factory {

    private Lifecycle lifecycle;

    public StartViewModelFactory(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StartViewModel.class)) {
            return (T) new StartViewModel(lifecycle);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
