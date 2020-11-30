package edu.chnu.mobidev_native.model.entity.suggestion;

import com.squareup.moshi.Json;

public class PurchaseSuggestionDTO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    private String name;
    private float price;
    @Json(name="image")
    private String imageBytes;

    public PurchaseSuggestionDTO(int id, String name, float price, String imageBytes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
    }
}
