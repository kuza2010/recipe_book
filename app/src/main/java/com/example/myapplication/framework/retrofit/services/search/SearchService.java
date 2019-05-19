package com.example.myapplication.framework.retrofit.services.search;

import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.model.search.recipe_search.SerchedRecipes;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

@Singleton
public interface SearchService {
    @GET("Prod/api/search/recipesinsuggestionlist")
    Call<SearchedDishesName> getDishesNameByPart(@Header("Cache-Control") String header,
                                                 @Query("recipe_name") String partOfName,
                                                 @Query("count_recipes") int limit);

    @GET("Prod/api/search/recipes")
    Call<SerchedRecipes> getRecipeByName(@Header("Cache-Control") String header,
                                         @Query("recipe_name") String recipeName);
}
