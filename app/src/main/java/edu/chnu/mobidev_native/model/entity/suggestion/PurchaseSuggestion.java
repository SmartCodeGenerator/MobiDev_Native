package edu.chnu.mobidev_native.model.entity.suggestion;

import com.squareup.moshi.Json;

public class PurchaseSuggestion {

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

    private int id;
    private String name;
    private float price;

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Json(name="image")
    private String imageBytes;

    public PurchaseSuggestion(String name, float price) {
        this.name = name;
        this.price = price;
    }

}
