package com.example.myapplication.framework.retrofit.model.search.recipe_search;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id_image")
    private Integer imageId;

    @SerializedName("name")
    private String name;


    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
