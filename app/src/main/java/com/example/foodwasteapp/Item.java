package com.example.foodwasteapp;

public class Item {

    String itemId;
    String itemQuantity;
    String itemStorage;

    public Item(){

    }

    public Item(String itemId, String itemQuantity, String itemStorage) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemStorage= itemStorage;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemStorage() {
        return itemStorage;
    }
}
