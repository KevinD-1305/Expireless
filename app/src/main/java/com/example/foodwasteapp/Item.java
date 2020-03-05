package com.example.foodwasteapp;

import android.widget.ImageView;

public class Item {

    private String Name;
    private String Quantity;
    private String Storage;
    private String ExpiryDate;
    private String image;

    public Item() {

    }

    public Item(String itemName, String itemQuantity, String itemStorage, String itemExpiry, String itemImage) {
        this.Name = itemName;
        this.Quantity = itemQuantity;
        this.Storage = itemStorage;
        this.ExpiryDate = itemExpiry;
        this.image = itemImage;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String itemName) {
        this.Name = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String itemQuantity) {
        this.Quantity = itemQuantity;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String itemStorage) {
        this.Storage = itemStorage;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }
}
