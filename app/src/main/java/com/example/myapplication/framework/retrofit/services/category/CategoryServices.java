package com.example.myapplication.framework.retrofit.services.category;

import com.example.myapplication.framework.retrofit.model.Categories;
import com.example.myapplication.framework.retrofit.services.AWSException;
import com.example.myapplication.framework.retrofit.services.AbstractServices;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Header;

@Singleton
public class CategoryServices extends AbstractServices {

    private CategoryService service;

    @Inject
    public CategoryServices(@NotNull Retrofit retrofit) {
        service = retrofit.create(CategoryService.class);
    }

    public Categories getCategories() throws AWSException {
        //Call<Categories> call = service.getCategories();
        //return execute(call);
        return null;
    }

    public void getCategories(@Header("Cache-Control") String cache, NetworkCallback<Categories> networkCallback) {
        Call<Categories> call = service.getCategories(cache);
        enqueue(call,networkCallback);
    }
}
