package edu.chnu.mobidev_native.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.function.Predicate;

import edu.chnu.mobidev_native.models.ListItem;

public class ListInfoViewModel extends ViewModel {

    private MutableLiveData<LinkedList<ListItem>> listItems;

    public ListInfoViewModel() {
        listItems = new MutableLiveData<>();
        listItems.setValue(new LinkedList<ListItem>());

        // TODO remove code below when implement db

        ListItem testSample1 = new ListItem("item1", 40, false);
        testSample1.setUid(testSample1.hashCode());
        ListItem testSample2 = new ListItem("item2", 40, false);
        testSample2.setUid(testSample2.hashCode());
        ListItem testSample3 = new ListItem("item3", 40, false);
        testSample3.setUid(testSample3.hashCode());
        ListItem testSample4 = new ListItem("item4", 40, false);
        testSample4.setUid(testSample4.hashCode());

        listItems.getValue().add(testSample1);
        listItems.getValue().add(testSample2);
        listItems.getValue().add(testSample3);
        listItems.getValue().add(testSample4);
    }

    public void collectInfo(String listName, StringBuilder info) {
        info.append(listName).append(":\n\n");

        for(ListItem listItem : listItems.getValue()) {
            info.append(String.format("%10s: %10.3f " +
                            (listItem.isChecked() ? "\u2713" : "") + "\n",
                    listItem.getDescription(),
                    listItem.getPrice()));
        }
    }

    public String getCalculatedTotalPrice() {
        float sum = 0;
        for (ListItem item : listItems.getValue()) {
            sum += item.getPrice();
        }
        return String.valueOf(sum);
    }

    public ListItem getById(int id) {
        final int targetId = id;
        ListItem targetItem = listItems.getValue().stream().filter(new Predicate<ListItem>() {
            @Override
            public boolean test(ListItem i) {
                return i.getUid() == targetId;
            }
        }).findAny().orElse(null);

        return targetItem;
    }

    public LiveData<LinkedList<ListItem>> getListItems() {
        return listItems;
    }
}
