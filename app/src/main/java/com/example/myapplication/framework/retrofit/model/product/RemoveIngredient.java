package com.example.myapplication.framework.retrofit.model.product;

import com.google.gson.annotations.SerializedName;

public class RemoveIngredient {
    @SerializedName("id_ingredient")
    private Integer ingrdientId;

    @SerializedName("ingredient_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIngrdientId() {
        return ingrdientId;
    }

    public void setIngrdientId(Integer ingrdientId) {
        this.ingrdientId = ingrdientId;
    }
}
