package com.example.myapplication.framework.retrofit.model.recipe;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("id_recipe")
    private Integer idRecipe;

    @SerializedName("id_image")
    private Integer imageId;

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCoockingTime() {
        return coockingTime;
    }

    public void setCoockingTime(String coockingTime) {
        this.coockingTime = coockingTime;
    }

    @SerializedName("total_time")
    private String coockingTime;

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
    }

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
