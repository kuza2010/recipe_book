package com.example.myapplication.framework.retrofit.model.product;

import com.google.gson.annotations.SerializedName;

public class UpdateIngredient {
    @SerializedName("count_ingredient")
    private Integer countIngredient;

    public Integer getCountIngredient() {
        return countIngredient;
    }

    public void setCountIngredient(Integer countIngredient) {
        this.countIngredient = countIngredient;
    }
}
