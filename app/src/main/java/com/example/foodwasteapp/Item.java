package com.example.foodwasteapp;

import android.util.Log;

public class Item {

    private String Id;
    private String name;
    private Long Quantity;
    private String Storage;

    public Item() {

    }

    public Item(String itemId, String itemName, Long itemQuantity, String itemStorage) {
        this.Id = itemId;
        this.name = itemName;
        this.Quantity = itemQuantity;
        this.Storage = itemStorage;

    }
    public String getId() {
        return Id;
    }

    public void setId(String itemId) {
        this.Id = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long itemQuantity) {
        this.Quantity = itemQuantity;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String itemStorage) {
        this.Storage = itemStorage;
    }
}
