package com.example.myapplication.framework.retrofit.services.fridge;

import com.example.myapplication.framework.retrofit.model.product.Products;
import com.example.myapplication.framework.retrofit.model.product.RemoveIngredient;
import com.example.myapplication.framework.retrofit.model.product.UpdateIngredient;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface FridgeService {
    @GET("Prod/api/users/getingredients")
    Call<Products> getMyProducts(@Header("Cache-Control") String header,
                                 @Query("id_user")int userId);

    @POST("Prod/api/users/addingredient")
    Call<Void> addProduct(@Header("Cache-Control") String header,
                              @Query("id_user")int userId,
                              @Query("id_ingredient")int ingredientId,
                              @Query("count_ingredient")int ingredientCount);

    @PUT("Prod/api/users/updateingredient")
    Call<UpdateIngredient> updateIngredient(@Header("Cache-Control") String header,
                                            @Query("id_user")int userId,
                                            @Query("id_ingredient")int ingredientId,
                                            @Query("count_ingredient")int ingredientCount);

    @DELETE("Prod/api/users/removeingredient")
    Call<RemoveIngredient> removeIngredient(@Header("Cache-Control") String header,
                                            @Query("id_user")int userId,
                                            @Query("id_ingredient")int ingredientId);


}
