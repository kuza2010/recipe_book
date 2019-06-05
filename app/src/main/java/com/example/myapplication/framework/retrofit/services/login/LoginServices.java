package com.example.myapplication.framework.retrofit.services.login;

import com.example.myapplication.framework.retrofit.model.login.Registration;
import com.example.myapplication.framework.retrofit.model.login.SignIn;
import com.example.myapplication.framework.retrofit.services.AbstractServices;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;

@Singleton
public class LoginServices extends AbstractServices {

    private LoginService loginService;

    @Inject
    public LoginServices(Retrofit retrofit){
        this.loginService = retrofit.create(LoginService.class);
    }


    public void registration(Registration.RegistrationBody credentials, NetworkCallback<Void> callback){
        Call<Void> call = loginService.registration(credentials);
        enqueue(call,callback);
    }

    public void signIn(String login, String password,NetworkCallback<SignIn>callback){
        Call<SignIn> call = loginService.signIn(login,password);
        enqueue(call,callback);
    }
}
