package com.example.myapplication.framework.retrofit.model.search;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("name")
    private String name;

    @SerializedName("id_ingredient")
    private Integer idIngredient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Integer idIngredient) {
        this.idIngredient = idIngredient;
    }
}
