package edu.chnu.mobidev_native.model.entity.shoppinglist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.LinkedList;

import edu.chnu.mobidev_native.model.entity.listitem.ListItem;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "list_name")
    private String listName;

    @ColumnInfo(name = "completed")
    private boolean isCompleted;

    @ColumnInfo(name = "time_created")
    private String timeCreated;

    public ShoppingList(String listName, boolean isCompleted, String timeCreated) {
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

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
