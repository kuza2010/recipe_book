package com.example.myapplication.framework.retrofit;

import com.example.myapplication.framework.retrofit.model.Categories;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("Prod/api/categories/getcategories")
    Call<Categories> getCategories();

    @GET("Prod/api/categories/getcategories")
    Call<ResponseBody> getCategory();
}
