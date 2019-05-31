package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.ExpandableRecipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class ExpandableRecipePresenterImpl extends AbstractBasePresenter<ExpandableRecipePresenter.RecipeContractView>
        implements ExpandableRecipePresenter<ExpandableRecipePresenter.RecipeContractView> {

    private RecipeServices recipeService;
    private ExpandableRecipes expandableRecipes;

    @Override
    public void init(String cache, int user_id, final int recipe_id) {
        recipeService.getRecipe(cache, user_id, recipe_id, new NetworkCallback<ExpandableRecipes>() {
            @Override
            public void onResponse(ExpandableRecipes body) {
                Timber.d("onResponse: get expandable recipe %s",expandableRecipes);
                expandableRecipes = body;
                view.setRecipe(expandableRecipes);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("onFailure: get Expandable recipe id %s error: %s",recipe_id,throwable.getMessage());
                view.showErrorScreen("Recipe get failure");
            }
        });
    }

    @Inject
    public ExpandableRecipePresenterImpl(RecipeServices service) {
        this.recipeService = service;
    }
}
