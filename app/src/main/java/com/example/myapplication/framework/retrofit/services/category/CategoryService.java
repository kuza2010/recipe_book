package com.example.myapplication.framework.retrofit.services.category;

import com.example.myapplication.framework.retrofit.model.category.Categories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoryService {
    @GET("Prod/api/categories/getcategories")
    Call<Categories> getCategories(@Header("Cache-Control")String header);
}
