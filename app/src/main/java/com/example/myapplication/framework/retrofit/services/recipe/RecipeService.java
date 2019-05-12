package com.example.myapplication.framework.retrofit.services.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.Recipes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecipeService {
    @GET("Prod/api/recipes/getrecipebycategory")
    Call<Recipes> getRecipeByCategoryName(@Header("Cache-Control")String header, @Query("category_name")String categoryName);
}
