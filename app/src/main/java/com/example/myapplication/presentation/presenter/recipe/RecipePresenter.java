package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.presentation.presenter.BasePresenter;

import java.util.List;

public interface RecipePresenter<T> extends BasePresenter<T> {

    void initByCategory(String recipeName);
    void init(final int userId, String type,String recipeName);
    void refreshRecipeList(final String currentCategory);

    interface RecipeContractView {
        void setRecipeList(List<Recipe> recipeList);
        void updateRecipes(List<Recipe> recipeList);
        void showHint();
    }
}
