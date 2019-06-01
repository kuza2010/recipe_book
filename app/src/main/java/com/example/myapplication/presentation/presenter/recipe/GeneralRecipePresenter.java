package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.GeneralRecipe;
import com.example.myapplication.presentation.presenter.BasePresenter;

public interface GeneralRecipePresenter<T> extends BasePresenter<T> {

    void init(String cache,int user_id, int recipe_id);

    interface RecipeContractView {
        void setContent(GeneralRecipe recipeList);
        void showErrorScreen(String message);
    }
}
