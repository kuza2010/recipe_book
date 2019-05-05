package com.example.myapplication.presentation.presenter;

import timber.log.Timber;

public class LoginPresenter extends BasePresenter<LoginPresenter.LoginContractView>{

    public void login(){
        Timber.d("login now!");
        Timber.d("some network activity...");

        view.login();
    }

    public interface LoginContractView{
        void login();
    }
}
