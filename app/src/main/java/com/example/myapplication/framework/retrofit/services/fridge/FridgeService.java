package com.example.myapplication.framework.retrofit.services.fridge;

import com.example.myapplication.framework.retrofit.model.product.Products;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FridgeService {
    @GET("Prod/api/users/getingredients")
    Call<Products> getMyProducts(@Header("Cache-Control") String header,
                                 @Query("id_user")int userId);

    @POST("Prod/api/user/addingredient")
    Call<Response> addProduct(@Header("Cache-Control") String header,
                              @Query("id_user")int userId,
                              @Query("od_ingredient")int ingredientId,
                              @Query("count_ingredient")int ingredientCount);

}
