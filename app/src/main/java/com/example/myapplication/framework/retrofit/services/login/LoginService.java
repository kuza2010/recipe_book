package com.example.myapplication.framework.retrofit.services.login;

import com.example.myapplication.framework.retrofit.model.login.Registration;
import com.example.myapplication.framework.retrofit.model.login.SignIn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @GET("Prod/api/users/signin")
    Call<SignIn> signIn(@Query("login")String login,
                        @Query("password")String password);

    @POST("Prod/api/users/registration")
    Call<Registration> registration(@Body Registration.RegistrationBody credentials);
}
