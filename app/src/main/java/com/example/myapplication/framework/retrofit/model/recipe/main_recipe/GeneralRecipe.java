package com.example.myapplication.framework.retrofit.model.recipe.main_recipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralRecipe {
    @SerializedName("recipes")
    private ExpandableRecipes recipes;
    @SerializedName("is_favorite")
    private Boolean isFavorite;
    @SerializedName("autor_name")
    private String autorName;
    @SerializedName("ingredients")
    private List<String> ingredients = null;
    @SerializedName("tools")
    private List<String> tools = null;
    @SerializedName("all_steps")
    private List<AllStep> allSteps = null;
    @SerializedName("total_comments")
    private Integer totalComments;

    public ExpandableRecipes getRecipes() {
        return recipes;
    }

    public void setRecipes(ExpandableRecipes recipes) {
        this.recipes = recipes;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getAutorName() {
        return autorName;
    }

    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public List<AllStep> getAllSteps() {
        return allSteps;
    }

    public void setAllSteps(List<AllStep> allSteps) {
        this.allSteps = allSteps;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }
}
