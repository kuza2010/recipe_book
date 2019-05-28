package com.example.myapplication.framework.retrofit.model.product;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id_ingredient")
    private int ingredientId;

    @SerializedName("ingredient_name")
    private String ingredientName;

    @SerializedName("ingredient_count")
    private int ingredientCount;

    @SerializedName("unit_measurement")
    private String units;

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(int ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientCount=" + ingredientCount +
                ", units='" + units + '\'' +
                '}';
    }
}
