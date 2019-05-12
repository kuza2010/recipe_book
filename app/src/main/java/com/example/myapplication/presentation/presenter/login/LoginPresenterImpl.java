package com.example.myapplication.presentation.presenter.login;

import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import timber.log.Timber;

public class LoginPresenterImpl extends AbstractBasePresenter<LoginPresenter.LoginContractView>
        implements LoginPresenter<LoginPresenter.LoginContractView> {
    @Override
    public void login() {
        Timber.d("login now!");
        Timber.d("some network activity...");

        view.login();
    }
}
