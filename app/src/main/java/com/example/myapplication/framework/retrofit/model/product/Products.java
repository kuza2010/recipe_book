package com.example.myapplication.framework.retrofit.model.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("ingredients")
    private List<Product> ingredients = null;

    public List<Product> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Product> ingredients) {
        this.ingredients = ingredients;
    }
}
