package com.example.myapplication.framework.retrofit.services.search;

import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

@Singleton
public interface SearchService {
    @GET("Prod/api/search/searchrecipes")
    Call<SearchedDishesName> getDishesName(@Header("Cache-Control") String header,
                                           @Query("recipe_name") String dishesName,
                                           @Query("count_recipes") int limit);
}
