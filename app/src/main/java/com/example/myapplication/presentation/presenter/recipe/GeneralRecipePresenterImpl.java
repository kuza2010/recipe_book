package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.GeneralRecipe;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class GeneralRecipePresenterImpl extends AbstractBasePresenter<GeneralRecipePresenter.RecipeContractView>
        implements GeneralRecipePresenter<GeneralRecipePresenter.RecipeContractView> {

    private RecipeServices recipeService;
    private GeneralRecipe generalRecipe;

    @Override
    public void init(String cache, int user_id, final int recipe_id) {
        recipeService.getRecipe(cache, user_id, recipe_id, new NetworkCallback<GeneralRecipe>() {
            @Override
            public void onResponse(GeneralRecipe body) {
                Timber.d("onResponse: get expandable recipe %s", generalRecipe);
                generalRecipe = body;
                view.setContent(generalRecipe);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("onFailure: get Expandable recipe id %s error: %s",recipe_id,throwable.getMessage());
                view.showErrorScreen("Recipe get failure");
            }
        });
    }

    @Override
    public void bind(RecipeContractView recipeContractView) {
        super.bind(recipeContractView);
    }

    @Inject
    public GeneralRecipePresenterImpl(RecipeServices service) {
        this.recipeService = service;
    }
}
