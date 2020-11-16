package edu.chnu.mobidev_native.viewmodel.list;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;

import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.model.repository.ext.shoppinglist.ShoppingListRepository;
import timber.log.Timber;

public class ListViewModel extends AndroidViewModel {

    private final ShoppingListRepository repository;

    private final LiveData<List<ShoppingList>> shoppingLists;
    private final MutableLiveData<AbstractMap.SimpleEntry<String, Boolean>> listDeleted;
    public static final long[] BUZZ_PATTERN = {0, 500};

    public  ListViewModel(Application application) {
        super(application);
        repository = new ShoppingListRepository(application);
        shoppingLists = repository.getShoppingLists();

        listDeleted = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.i("ListViewModel destroyed!");
    }

    private String getDateTimeFormat(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(dateTime);
    }

    public LiveData<List<ShoppingList>> getShoppingLists() {
        return shoppingLists;
    }

    public LiveData<Integer> getNoListsMessageVisible() {
        return Transformations.map(shoppingLists, data -> {
            if (data.isEmpty()) {
                return View.VISIBLE;
            } else {
                return View.GONE;
            }
        });
    }

    public LiveData<AbstractMap.SimpleEntry<String, Boolean>> getListDeleted() {
        return listDeleted;
    }

    public void addList(String listName, boolean isCompleted, LocalDateTime timeCreated) {
        repository.insert(new ShoppingList(listName, isCompleted, getDateTimeFormat(timeCreated)));
    }

    public void deleteList(ShoppingList list) {
        String listName = list.getListName();

        repository.delete(list);

        listDeleted.setValue(new AbstractMap.SimpleEntry<>(listName, true));
    }

    public void onListDeleted() {
        AbstractMap.SimpleEntry<String, Boolean> entry =
                new AbstractMap.SimpleEntry<>(Objects
                        .requireNonNull(listDeleted.getValue()).getKey(), false);
        listDeleted.setValue(entry);
    }
}
