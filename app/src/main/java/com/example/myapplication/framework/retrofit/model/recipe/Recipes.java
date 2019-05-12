package com.example.myapplication.framework.retrofit.model.recipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipes {

    @SerializedName("recipes")
    private List<Recipe> recipes = null;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
