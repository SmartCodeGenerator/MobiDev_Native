package edu.chnu.mobidev_native.models;

public class ListItem {
    private int uid;
    private String description;
    private float price;
    private boolean isChecked;

    public ListItem(String description, float price, boolean isChecked) {
        this.description = description;
        this.price = price;
        this.setChecked(isChecked);
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
