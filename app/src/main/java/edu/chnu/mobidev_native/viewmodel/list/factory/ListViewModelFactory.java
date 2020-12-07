package edu.chnu.mobidev_native.viewmodel.list.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.chnu.mobidev_native.model.dao.ext.shoppinglist.ShoppingListDao;
import edu.chnu.mobidev_native.viewmodel.list.ListViewModel;

@SuppressWarnings("unchecked")
public class ListViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ListViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
