package com.example.foodwasteapp;

import android.text.Editable;

public class Item {

    private String Name;
    private String Quantity;
    private String Storage;
    private String ExpiryDate;

    public Item() {

    }

    public Item(String itemName, String itemQuantity, String itemStorage, String itemExpiry) {
        this.Name = itemName;
        this.Quantity = itemQuantity;
        this.Storage = itemStorage;
        this.ExpiryDate = itemExpiry;

    }


    public String getName() {
        return Name;
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
