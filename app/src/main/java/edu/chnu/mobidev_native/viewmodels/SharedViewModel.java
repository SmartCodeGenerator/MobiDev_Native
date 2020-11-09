package edu.chnu.mobidev_native.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> selectedList;

    public SharedViewModel() {
        selectedList = new MutableLiveData<>();
        selectedList.setValue("Not found");
    }

    public void setSelectedList(String listName) {
        selectedList.setValue(listName);
    }

    public LiveData<String> getSelectedList() {
        return selectedList;
    }
}
