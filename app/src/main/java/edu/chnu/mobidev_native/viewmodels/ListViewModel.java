package edu.chnu.mobidev_native.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.AbstractMap;
import java.util.LinkedList;

import edu.chnu.mobidev_native.models.ShoppingList;
import timber.log.Timber;

public class ListViewModel extends ViewModel {

    private MutableLiveData<LinkedList<ShoppingList>> shoppingLists;
    private MutableLiveData<AbstractMap.SimpleEntry<String, Boolean>> listDeleted;
    public static final long[] BUZZ_PATTERN = {0, 500};

    public  ListViewModel() {
        shoppingLists = new MutableLiveData<>();
        shoppingLists.setValue(new LinkedList<ShoppingList>());

        listDeleted = new MutableLiveData<>();

        // TODO replace with db Get query
        addList("list 1", false, LocalDateTime.now(ZoneId.of("Europe/Kiev")));
        addList("list 2", false, LocalDateTime.now(ZoneId.of("Europe/Kiev")));
        addList("list 3", false, LocalDateTime.now(ZoneId.of("Europe/Kiev")));
        Timber.i(String.format("shoppingLists: %d items", shoppingLists.getValue().size()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.i("ListViewModel destroyed!");
    }

    public LiveData<LinkedList<ShoppingList>> getShoppingLists() {
        return shoppingLists;
    }

    public LiveData<AbstractMap.SimpleEntry<String, Boolean>> getListDeleted() {
        return listDeleted;
    }

    public void addList(String listName, boolean isCompleted, LocalDateTime timeCreated) {
        LinkedList<ShoppingList> newLists = new LinkedList<>();

        for (ShoppingList list : shoppingLists.getValue()) {
            newLists.add(list);
        }

        newLists.add(new ShoppingList(listName, isCompleted, timeCreated));

        shoppingLists.setValue(newLists);
    }

    public void removeList(String listName) {
        LinkedList<ShoppingList> newLists = new LinkedList<>();

        for (ShoppingList list : shoppingLists.getValue()) {
            if (!list.getListName().equals(listName)) {
                newLists.add(list);
            }
        }

        shoppingLists.setValue(newLists);
        listDeleted.setValue(new AbstractMap.SimpleEntry<>(listName, true));
    }

    public void onListDeleted() {
        AbstractMap.SimpleEntry<String, Boolean> entry =
                new AbstractMap.SimpleEntry<>(listDeleted.getValue().getKey(), false);
        listDeleted.setValue(entry);
    }
}
