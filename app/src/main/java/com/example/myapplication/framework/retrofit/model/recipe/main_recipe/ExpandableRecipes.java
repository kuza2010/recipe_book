package com.example.myapplication.framework.retrofit.model.recipe.main_recipe;

import com.google.gson.annotations.SerializedName;

public class ExpandableRecipes{

    @SerializedName("id_recipe")
    private Integer idRecipe;
    @SerializedName("id_image")
    private Integer idImage;
    @SerializedName("name")
    private String name;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("total_time")
    private String totalTime;
    @SerializedName("description")
    private String description;

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
