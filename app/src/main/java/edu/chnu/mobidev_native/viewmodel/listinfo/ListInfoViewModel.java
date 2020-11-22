package edu.chnu.mobidev_native.viewmodel.listinfo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Objects;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;
import edu.chnu.mobidev_native.model.entity.relation.ListWithItems;
import edu.chnu.mobidev_native.model.entity.shoppinglist.ShoppingList;
import edu.chnu.mobidev_native.model.repository.ext.listitem.ListItemRepository;
import edu.chnu.mobidev_native.model.repository.ext.shoppinglist.ShoppingListRepository;

public class ListInfoViewModel extends AndroidViewModel {

    private final ListItemRepository repository;
    private final ShoppingListRepository shoppingListRepository;

    private LiveData<ListWithItems> listWithItems;

    public ListInfoViewModel(Application application) {
        super(application);
        repository = new ListItemRepository(application);
        shoppingListRepository = new ShoppingListRepository(application);
    }

    public void fetchData(int ownerId) {
        listWithItems = repository.getListItems(ownerId);
    }

    public void collectInfo(StringBuilder info) {
        info.append(Objects.requireNonNull(listWithItems.getValue())
                .shoppingList.getListName()).append(":\n\n");

        for (ListItem listItem : Objects.requireNonNull(listWithItems.getValue().listItems)) {
            info.append(String.format("%10s: %10.3f " +
                            (listItem.isChecked() ? "\u2713" : "") + "\n",
                    listItem.getDescription(),
                    listItem.getPrice()));
        }
    }

    public String getCalculatedTotalPrice() {
        float sum = 0;
        if (listWithItems.getValue() != null && listWithItems.getValue().listItems != null) {
            for (ListItem item : listWithItems.getValue().listItems) {
                sum += item.getPrice();
            }
        }
        return String.valueOf(sum);
    }

    public LiveData<ListWithItems> getListWithItems() {
        return listWithItems;
    }

    public void insertItem(ListItem item) {
        repository.insert(item);
    }

    public void updateItem(ListItem item) {
        repository.update(item);

        if (listWithItems.getValue() != null && listWithItems.getValue().listItems != null) {
            int itemsSize = listWithItems.getValue().listItems.size();
            int completedCounter = 0;

            for (ListItem listItem : listWithItems.getValue().listItems) {
                if (listItem.isChecked()) {
                    completedCounter++;
                }
            }

            ShoppingList owner = Objects.requireNonNull(listWithItems.getValue()).shoppingList;
            owner.setCompleted(completedCounter == itemsSize);
            shoppingListRepository.update(owner);
        }
    }
}
