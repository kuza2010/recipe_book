package com.example.myapplication.framework.retrofit.services;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public abstract class AbstractServices {

    protected <T> T execute(Call<T> call) throws AWSException {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Timber.e("response %s failed", call);
                if (null != response.errorBody())
                    Timber.e("errorBody: %s", response.errorBody());
                throw new AWSException("response failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AWSException(String.format("response failed: %s", e.getMessage()), e);
        }
    }


    protected <T> void enqueue(Call<T> call, final NetworkCallback<T> nCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful())
                    nCallback.onResponse(response.body());
                else {
                    if (null != response.errorBody())
                        Timber.e("errorBody: %s", response.errorBody());
                    nCallback.onFailure(new AWSException("response is not successful!"));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Timber.e("Response is not successful! Throwable: %s", t.getMessage());
                nCallback.onFailure(new AWSException(String.format("response failed: %s", t.getMessage()), t));
            }
        });
    }
}
