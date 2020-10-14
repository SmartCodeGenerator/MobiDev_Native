package edu.chnu.mobidev_native.models;

public class ListItem {
    private int itemId;
    private String description;
    private float price;
    private boolean isChecked;

    public ListItem(int itemId, String description, float price, boolean isChecked) {
        this.itemId = itemId;
        this.description = description;
        this.price = price;
        this.setChecked(isChecked);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
