package com.example.myapplication.framework.retrofit.services.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.AbstractServices;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Header;

@Singleton
public class RecipeServices extends AbstractServices {

    private RecipeService recipeService;

    @Inject
    public RecipeServices(Retrofit retrofit) {
        recipeService = retrofit.create(RecipeService.class);
    }


    public void getRecipeByCategoryName(String categoryName, @Header("Cache-Control") String cacheControl, NetworkCallback<Recipes> networkCallback) {
        Call<Recipes> call = recipeService.getRecipeByCategoryName(cacheControl, categoryName);
        enqueue(call, networkCallback);
    }
}
