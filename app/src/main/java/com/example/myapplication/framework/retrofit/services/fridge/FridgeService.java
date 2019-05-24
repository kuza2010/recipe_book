package com.example.myapplication.framework.retrofit.services.fridge;

import com.example.myapplication.framework.retrofit.model.product.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FridgeService {
    @GET("Prod/api/user/getingredients")
    Call<Products> getMyProducts(@Header("Cache-Control") String header,
                                 @Query("user_id")int userId);
}
