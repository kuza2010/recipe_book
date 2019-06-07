package com.example.myapplication.presentation.presenter.recipe;

import android.support.annotation.Nullable;

import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;
import com.example.myapplication.presentation.ui.recipe.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class RecipePresenterImpl extends AbstractBasePresenter<RecipePresenter.RecipeContractView>
        implements RecipePresenter<RecipePresenter.RecipeContractView> {

    RecipeServices recipeServices;

    private List<Recipe> recipeList;

    @Inject
    public RecipePresenterImpl(RecipeServices recipeService) {
        this.recipeServices = recipeService;
        this.recipeList = new ArrayList<>();
    }

    @Override
    public void initByCategory(final String currentCategory) {
        Timber.d("recipe presenter start download recipes from category %s", currentCategory);

        recipeServices.getRecipeByCategoryName(currentCategory, CACHE, new NetworkCallback<Recipes>() {
            @Override
            public void onResponse(Recipes recipes) {
                Timber.d("download %s recipes", recipes.getRecipes().size());
                recipeList.addAll(recipes.getRecipes());
                //notify view
                view.setRecipeList(recipeList);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed to get list recipe for categories \"%s\"", currentCategory);
                Timber.e("Error message %s", throwable.getMessage());
            }
        });
    }

    @Override
    public void init(int userId, String typePage,@Nullable String category) {
        Timber.d("recipe presenter start download recipe to user with id %s", userId);

        if (typePage.equals(RecipeActivity.TypePage.BY_ME.title))
            recipeServices.getMyRecipe(CACHE, userId, callback);
        else if (typePage.equals(RecipeActivity.TypePage.FAVORITE.title))
            recipeServices.getFavoriteRecipe(CACHE, userId, callback);
        else if (category != null)
            recipeServices.getRecipeByCategoryName(category, CACHE, callback);
        else
            throw new RuntimeException("init presenter failed");
    }

    @Override
    public void refreshRecipeList(final String currentCategory) {
        Timber.d("recipe presenter start download recipes from category %s", currentCategory);

        throw new RuntimeException("not implemented");
//        recipeServices.getRecipeByCategoryName(currentCategory, NO_CACHE, new NetworkCallback<Recipes>() {
//            @Override
//            public void onResponse(Recipes recipes) {
//                Timber.d("download %s recipes", recipes.getRecipes().size());
//                List<Recipe> different = Utils.getNewRecipes(recipeList, recipes.getRecipes());
//                Timber.d("fetch %s new record", different.size());
//                recipeList.addAll(0,different);
//
//                //notify view
//                view.updateRecipes(different);
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Timber.e("Failed to refresh list recipe for categories \"%s\"", currentCategory);
//                Timber.e("Error message %s", throwable.getMessage());
//            }
//        });
    }

    @Override
    public void unbind() {
        if (recipeList != null)
            recipeList.clear();

        super.unbind();
    }

    private NetworkCallback<Recipes> callback = new NetworkCallback<Recipes>() {
        @Override
        public void onResponse(Recipes body) {
            Timber.d("download %s recipes", body.getRecipes().size());
            if (body.getRecipes().isEmpty()) {
                recipeList.clear();
                view.showHint();
            } else {
                recipeList.clear();
                recipeList.addAll(body.getRecipes());
                //notify view
                view.setRecipeList(recipeList);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            Timber.e("Failed to get list recipe ");
            Timber.e("Error message %s", throwable.getMessage());
        }
    };
}
