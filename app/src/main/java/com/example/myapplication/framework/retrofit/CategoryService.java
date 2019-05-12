package com.example.myapplication.framework.retrofit;

import com.example.myapplication.framework.retrofit.model.Categories;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CategoryService {
    @GET("Prod/api/categories/getcategories")
    Call<Categories> getCategories(@Header("Cache-Control")String header);
}
