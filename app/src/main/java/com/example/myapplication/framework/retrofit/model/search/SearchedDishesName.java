package com.example.myapplication.framework.retrofit.model.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedDishesName {

    @SerializedName("dishes")
    private List<String> dishes = null;

    public List<String> getDishes() {
        return dishes;
    }

    public void setDishes(List<String> dishes) {
        this.dishes = dishes;
    }
}
