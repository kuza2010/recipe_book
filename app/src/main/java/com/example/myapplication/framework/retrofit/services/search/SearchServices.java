package com.example.myapplication.framework.retrofit.services.search;

import android.support.annotation.NonNull;

import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.model.search.SearchedIngredientName;
import com.example.myapplication.framework.retrofit.services.AWSException;
import com.example.myapplication.framework.retrofit.services.AbstractServices;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Header;
import timber.log.Timber;

@Singleton
public class SearchServices extends AbstractServices {

    private SearchService service;
    private Call call;

    @Inject
    public SearchServices(@NonNull Retrofit retrofit) {
        service = retrofit.create(SearchService.class);
    }

    public void cancelOrSkip(){
        if (call!=null && call.isExecuted()) {
            Timber.d("call is executed, canceled now!");
            call.cancel();
        }
    }

    public SearchedDishesName getRecipesNameByPart(@Header("Cache-Control") String cache, String partOfName, int limit) throws AWSException {
        call = service.getDishesNameByPart(cache, partOfName, limit);
        return (SearchedDishesName) execute(call);
    }

    public void getRecipesNameByPart(@Header("Cache-Control") String cache, String partOfName, int limit, NetworkCallback<SearchedDishesName> callback) {
        call = service.getDishesNameByPart(cache, partOfName, limit);
        enqueue(call,callback);
    }

    public void getIngredientNameByPart(@Header("Cache-Control") String cache, String partOfName, int limit, NetworkCallback<SearchedIngredientName> callback) {
        call = service.getIngredientNameByPart(cache, partOfName, limit);
        enqueue(call,callback);
    }

    public void getRecipesByFullName(@Header("Cache-Control") String cache, String name, NetworkCallback<Recipes> callback) {
        Call<Recipes> call = service.getRecipeByName(cache, name);
        enqueue(call, callback);
    }

    public void getRecipeByIngredients(@Header("Cache-Control") String cache, List<String> ingredients, NetworkCallback<Recipes> callback){
        Call<Recipes> call = service.getRecipeByIngredient(cache,ingredients);
        enqueue(call,callback);
    }
}
