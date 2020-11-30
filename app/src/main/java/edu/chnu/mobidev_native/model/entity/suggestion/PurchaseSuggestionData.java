package edu.chnu.mobidev_native.model.entity.suggestion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "purchase_suggestion")
public class PurchaseSuggestionData {

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private float price;

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    @ColumnInfo(name = "image")
    private String imageBytes;

    public PurchaseSuggestionData(int id, String name, float price, String imageBytes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
    }
}
