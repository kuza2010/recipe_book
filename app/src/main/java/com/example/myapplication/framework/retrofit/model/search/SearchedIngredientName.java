package com.example.myapplication.framework.retrofit.model.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedIngredientName {
    @SerializedName("ingredients")
    private List<Ingredient> ingredients = null;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


}
