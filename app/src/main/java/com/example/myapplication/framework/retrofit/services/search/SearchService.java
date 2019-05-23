package com.example.myapplication.framework.retrofit.services.search;

import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.model.search.SearchedIngredientName;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

@Singleton
public interface SearchService {
    @GET("Prod/api/search/recipesinsuggestionlist")
    Call<SearchedDishesName> getDishesNameByPart(@Header("Cache-Control") String header,
                                                 @Query("recipe_name") String partOfName,
                                                 @Query("count_recipes") int limit);

    @GET("Prod/api/search/ingredientsinsuggestionlist")
    Call<SearchedIngredientName> getIngredientNameByPart(@Header("Cache-Control") String header,
                                                         @Query("ingredient_name") String partOfName,
                                                         @Query("count_ingredients") int limit);

    @GET("Prod/api/search/recipes")
    Call<Recipes> getRecipeByName(@Header("Cache-Control") String header,
                                         @Query("recipe_name") String recipeName);

    @POST("Prod/api/search/recipesbyingredients")
    Call<Recipes> getRecipeByIngredient(@Header("Cache-Control") String header, @Body List<String> ingredients);


}
