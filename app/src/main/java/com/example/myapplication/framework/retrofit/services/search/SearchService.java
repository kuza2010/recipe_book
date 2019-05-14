package com.example.myapplication.framework.retrofit.services.search;

import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SearchService {
    @GET("Prod/api/search/searchdishes")
    Call<SearchedDishesName> getDishesName(@Header("Cache-Control") String header,
                                           @Query("dish_name") String dishesName,
                                           @Query("count_dishes") int limit);
}
