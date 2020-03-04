package com.example.foodwasteapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.widget.ImageView;

public class Item {

    private String Name;
    private String Quantity;
    private String Storage;
    private String ExpiryDate;
    private ImageView image;

    public Item() {

    }

    public Item(String itemName, String itemQuantity, String itemStorage, String itemExpiry, ImageView itemImage) {
        this.Name = itemName;
        this.Quantity = itemQuantity;
        this.Storage = itemStorage;
        this.ExpiryDate = itemExpiry;
        this.image = itemImage;
    }

    public String getName() {
        return Name;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
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
