package com.example.myapplication.framework.retrofit.services;

/**
 * Using for callback into method Retrofit.Call.enqueue
 * @param <RESPONSE> - successful response body
 */
public interface NetworkCallback<RESPONSE> {

    void onResponse(RESPONSE body);

    void onFailure(Throwable throwable);

}
