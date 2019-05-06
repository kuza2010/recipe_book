package com.example.myapplication.framework.retrofit;

import com.example.myapplication.framework.retrofit.model.Categories;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import timber.log.Timber;

@Singleton
public class CategoryServiceImpl {

    private CategoryService service;

    @Inject
    public CategoryServiceImpl(@NotNull Retrofit retrofit) {
        service = retrofit.create(CategoryService.class);
    }

    public Categories getCategories() {
        try {
            return service.getCategories().execute().body();
        } catch (Exception e) {
            Timber.e("getCategories: error %s", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ResponseBody getCategory() {
        try {
            return service.getCategory().execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
