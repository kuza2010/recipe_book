package com.example.myapplication.presentation.presenter.login;

import com.example.myapplication.BaseApp;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.framework.retrofit.model.login.SignIn;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.login.LoginServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginPresenterImpl extends AbstractBasePresenter<LoginPresenter.LoginContractView>
        implements LoginPresenter<LoginPresenter.LoginContractView> {
    @Inject
    RecipesPreferences preferences;
    @Inject
    LoginServices loginServices;

    public LoginPresenterImpl() {
        BaseApp.getComponent().inject(this);
    }

    public void checkRememberMeCredentials() {
        boolean rememberMe = preferences.getValue(RecipesPreferences.REMEMBER_ME, false);
        boolean isLogOut = preferences.getValue(RecipesPreferences.IS_LOG_OUT, true);
        boolean isRegistered = preferences.getValue(RecipesPreferences.IS_REGISTRED_USER,false);
        String login = preferences.getValue(RecipesPreferences.LOGIN, null);
        String pass = preferences.getValue(RecipesPreferences.PASS, null);
        Timber.d("checkRememberCredentials: remember = %s, logout = %s", rememberMe, isLogOut);

        view.initBundle(rememberMe, isLogOut,isRegistered, login, pass);
    }

    @Override
    public void tryToLogin(String login, String password, boolean rememberMe) {
        Timber.d("loginClick: loginClick - %s, pass - %s", login, password);

        if (login == null || login.isEmpty()
                || password == null || password.isEmpty()) {
            view.showMessage("Password or login empty!");
            return;
        }
        login(login, password, rememberMe);
    }

    @Override
    public void skipLogin() {
        Timber.d("skipLogin: login skipped");

        preferences.clearAll();
        preferences.saveSingle(RecipesPreferences.IS_LOG_OUT, true);
        preferences.saveSingle(RecipesPreferences.IS_REGISTRED_USER, false);

        view.login(false);
    }

    private void login(final String login, final String password, final boolean rememberMe) {
        view.showSignInLayout();
        loginServices.signIn(login, password, new NetworkCallback<SignIn>() {
            @Override
            public void onResponse(final SignIn body) {
                Timber.d("onResponse: body - %s", body);
                preferences.saveCredentials(body, login, password,rememberMe);
                view.login(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.d("onFailure: sign in failure %s", throwable.getMessage());
                view.showMessage("Sign in failure.  Try later.");
            }
        });
    }

}
