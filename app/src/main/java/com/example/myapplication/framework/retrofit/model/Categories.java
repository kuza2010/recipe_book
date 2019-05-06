package com.example.myapplication.framework.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
