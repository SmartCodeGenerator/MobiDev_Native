package edu.chnu.mobidev_native.models;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class ShoppingList {
    private int uid;
    private String listName;
    private boolean isCompleted;
    private LocalDateTime timeCreated;

    private LinkedList<ListItem> listItems;

    public ShoppingList(String listName, boolean isCompleted, LocalDateTime timeCreated) {
        listItems = new LinkedList<>();
        this.listName = listName;
        this.isCompleted = isCompleted;
        this.timeCreated = timeCreated;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LinkedList<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(LinkedList<ListItem> listItems) {
        this.listItems = listItems;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
