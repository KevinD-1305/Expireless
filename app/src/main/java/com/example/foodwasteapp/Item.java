package com.example.foodwasteapp;

import android.util.Log;

public class Item {

    private String Id;
    private String Quantity;
    private String Storage;

    public Item() {

    }

    public Item(String itemId, String itemQuantity, String itemStorage) {
        this.Id = itemId;
        this.Quantity = itemQuantity;
        this.Storage = itemStorage;
        Log.d("Set config", "no adapter");

    }

    public String getId() {
        return Id;
    }

    public void setId(String itemId) {
        this.Id = itemId;
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
}
