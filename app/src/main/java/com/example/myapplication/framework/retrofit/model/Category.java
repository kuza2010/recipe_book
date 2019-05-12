package com.example.myapplication.framework.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("name")
    private String name;
    @SerializedName("image_id")
    private Integer imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}