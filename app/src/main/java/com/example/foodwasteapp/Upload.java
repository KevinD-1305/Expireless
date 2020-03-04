package com.example.foodwasteapp;

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload(String s) {

    }

    public Upload(String name, String imageUrl) {
        name = (Scanner.productName);
        mName = name;
        mImageUrl = imageUrl;
    }
    public String getName() {
        return mName;
    }

    public void setmName(String name) {
        mName = name;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}

