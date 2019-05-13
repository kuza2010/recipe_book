package com.example.myapplication.presentation.presenter.recipe;

import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeService;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class RecipePresenterImpl extends AbstractBasePresenter<RecipePresenter.RecipeContractView>
        implements RecipePresenter<RecipePresenter.RecipeContractView> {

    @Inject
    RecipeServices recipeServices;

    private List<Recipe> recipeList;

    @Inject
    public RecipePresenterImpl(RecipeServices recipeService) {
        this.recipeServices = recipeService;
        this.recipeList = new ArrayList<>();
    }

    @Override
    public void init(final String currentCategory) {
        Timber.d("recipe presenter start download recipes from category %s",currentCategory);

        recipeServices.getRecipeByCategoryName(currentCategory, CACHE, new NetworkCallback<Recipes>() {
            @Override
            public void onResponse(Recipes recipes) {
                Timber.d("download %s recipes", recipes.getRecipes().size());
                recipeList.addAll(recipes.getRecipes());
                //notify view
                view.updateRecipes(recipeList);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed to get list recipe for categories \"%s\"", currentCategory);
                Timber.e("Error message %s", throwable.getMessage());
            }
        });
    }

    @Override
    public void refreshRecipeList(final String currentCategory) {
        Timber.d("recipe presenter start download recipes from category %s", currentCategory);

        recipeServices.getRecipeByCategoryName(currentCategory, CACHE, new NetworkCallback<Recipes>() {
            @Override
            public void onResponse(Recipes recipes) {
                Timber.d("download %s recipes", recipes.getRecipes().size());
                List<Recipe> different = Utils.getNewRecipes(recipeList, recipes.getRecipes());
                Timber.d("added %s new record", different.size());
                recipeList.addAll(0,different);
                //notify view
                view.updateRecipes(different);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed to refresh list recipe for categories \"%s\"", currentCategory);
                Timber.e("Error message %s", throwable.getMessage());
            }
        });
    }

    @Override
    public void unbind() {
        if (recipeList != null)
            recipeList.clear();

        super.unbind();
    }
}
