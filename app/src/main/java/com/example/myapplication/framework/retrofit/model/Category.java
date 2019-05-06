package com.example.myapplication.framework.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("name")
    private String name;
    @SerializedName("data")
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}