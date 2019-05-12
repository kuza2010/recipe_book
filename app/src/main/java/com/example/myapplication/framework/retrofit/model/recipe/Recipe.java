package com.example.myapplication.framework.retrofit.model.recipe;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("id_recipe")
    private Integer idRecipe;

    @SerializedName("image_id")
    private Integer imageId;

    @SerializedName("name")
    private String name;

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
