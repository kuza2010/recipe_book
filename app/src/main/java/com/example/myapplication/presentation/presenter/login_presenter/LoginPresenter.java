package com.example.myapplication.presentation.presenter.login_presenter;

import com.example.myapplication.presentation.presenter.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginPresenter.LoginContractView> {

    public interface LoginContractView{
        void login();
        void next();
    }
}
