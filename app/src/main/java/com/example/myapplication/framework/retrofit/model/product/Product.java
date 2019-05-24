package com.example.myapplication.framework.retrofit.model.product;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("ingredient_id")
    private String ingredientId;

    @SerializedName("ingredient_name")
    private String ingredientName;

    @SerializedName("ingredient_count")
    private String ingredientCount;

    @SerializedName("units")
    private String units;

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(String ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
