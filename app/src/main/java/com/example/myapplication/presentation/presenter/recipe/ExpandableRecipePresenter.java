package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.ExpandableRecipes;
import com.example.myapplication.presentation.presenter.BasePresenter;

public interface ExpandableRecipePresenter<T> extends BasePresenter<T> {

    void init(String cache,int user_id, int recipe_id);

    interface RecipeContractView {
        void setRecipe(ExpandableRecipes recipeList);
        void showErrorScreen(String message);
    }
}
