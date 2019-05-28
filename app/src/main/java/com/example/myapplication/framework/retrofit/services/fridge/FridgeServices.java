package com.example.myapplication.framework.retrofit.services.fridge;

import com.example.myapplication.framework.retrofit.model.product.Products;
import com.example.myapplication.framework.retrofit.model.product.RemoveIngredient;
import com.example.myapplication.framework.retrofit.model.product.UpdateIngredient;
import com.example.myapplication.framework.retrofit.services.AbstractServices;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Header;

@Singleton
public class FridgeServices extends AbstractServices {

    private FridgeService service;

    @Inject
    public FridgeServices(Retrofit retrofit) {
        service = retrofit.create(FridgeService.class);
    }

    public void getMyProducts(@Header("Cache-Control") String cache, int id, NetworkCallback<Products> callback) {
        Call<Products> call = service.getMyProducts(cache, id);
        enqueue(call, callback);
    }

    public void addIngredient(@Header("Cache-Control") String cache, int userId, int ingredientId, int countIngredient, NetworkCallback<Void> callback) {
        Call<Void> call = service.addProduct(cache, userId, ingredientId, countIngredient);
        enqueue(call, callback);
    }

    public void updateIngredient(@Header("Cache-Control") String cache, int userId, int ingredientId, int countIngredient, NetworkCallback<UpdateIngredient> callback){
        Call<UpdateIngredient> call = service.updateIngredient(cache,userId,ingredientId,countIngredient);
        enqueue(call,callback);
    }

    public void removeIngredient(@Header("Cache-Control") String cache, int userId, int ingredientId, NetworkCallback<RemoveIngredient> callback){
        Call<RemoveIngredient> call = service.removeIngredient(cache,userId,ingredientId);
        enqueue(call,callback);
    }
}
