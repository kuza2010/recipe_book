package com.example.myapplication.framework.retrofit.model.search.recipe_search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerchedRecipes {

    @SerializedName("recipes")
    private List<Recipe> recipes = null;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
