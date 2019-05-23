package com.example.myapplication.framework.retrofit.model.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedIngredientName {
    @SerializedName("ingredients")
    private List<String> ingredients = null;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
