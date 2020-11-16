package edu.chnu.mobidev_native.viewmodel.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> listId;

    public SharedViewModel() {
        listId = new MutableLiveData<>();
        listId.setValue(0);
    }

    public void setListId(int id) {
        listId.setValue(id);
    }

    public Integer getListId() { return listId.getValue(); }
}
