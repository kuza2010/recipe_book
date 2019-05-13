package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.presentation.presenter.BasePresenter;

import java.util.List;

public interface RecipePresenter<T> extends BasePresenter<T> {

    void init(String recipeName);
    void refreshRecipeList(final String currentCategory);

    public interface RecipeContractView {
        void updateRecipes(List<Recipe> recipeList);
    }
}
