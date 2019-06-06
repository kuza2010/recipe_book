package com.example.myapplication.framework.retrofit.services.recipe;

import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.GeneralRecipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecipeService {
    @GET("Prod/api/recipes/getrecipesbycategory")
    Call<Recipes> getRecipeByCategoryName(@Header("Cache-Control")String header,
                                          @Query("category_name")String categoryName);

    @GET("Prod/api/recipes/getrecipe")
    Call<GeneralRecipe> getRecipe(@Header("Cache-Control")String header,
                                  @Query("id_user")Integer userId,
                                  @Query("id_recipe")Integer recipeId);


    @GET("Prod/api/users/getfavoriterecipes")
    Call<Recipes> getFavoriteRecipe(@Header("Cache-Control")String header,
                                  @Query("id_user")Integer userId);

    @GET("Prod/api/users/getcreatedrecipes")
    Call<Recipes> getCreatedRecipe(@Header("Cache-Control")String header,
                                          @Query("id_user")Integer userId);
}
