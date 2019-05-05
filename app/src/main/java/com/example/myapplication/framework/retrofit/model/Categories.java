package com.example.myapplication.framework.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("name")
    private String name;
    @SerializedName("data")
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

//    private List<Category> categoryList;
//
//    public List<Category> getCategoryList() {
//        return categoryList;
//    }
//
//    public void setCategoryList(List<Category> categoryList) {
//        this.categoryList = categoryList;
//    }
}
