package com.example.myapplication.framework.retrofit.services.search;

import android.support.annotation.NonNull;

import com.example.myapplication.framework.retrofit.model.search.SearchedDishesName;
import com.example.myapplication.framework.retrofit.services.AWSException;
import com.example.myapplication.framework.retrofit.services.AbstractServices;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Header;

@Singleton
public class SearchServices extends AbstractServices {

    private SearchService service;
    private Call<SearchedDishesName>call;

    @Inject
    public SearchServices(@NonNull Retrofit retrofit) {
        service = retrofit.create(SearchService.class);
    }

    public SearchedDishesName getDishesNameByPart(@Header("Cache-Control") String cache, String partOfName, int limit) throws AWSException {
        call = service.getDishesName(cache, partOfName, limit);
        return execute(call);
    }
}
