package com.example.myapplication.framework.retrofit.model.recipe.main_recipe;

import com.google.gson.annotations.SerializedName;

public class AllStep {
    @SerializedName("description")
    private String description;

    @SerializedName("act")
    private Integer act;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }
}
