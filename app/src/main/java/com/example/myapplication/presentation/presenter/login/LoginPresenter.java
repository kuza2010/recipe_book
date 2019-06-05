package com.example.myapplication.presentation.presenter.login;

import android.support.annotation.Nullable;

import com.example.myapplication.presentation.presenter.BasePresenter;

public interface LoginPresenter<T> extends BasePresenter<T> {
    void tryToLogin(String login, String password,boolean rememberMe);
    void skipLogin();
    void checkRememberMeCredentials();

    /**
     * Using for callback in activity.
     * Any activity was used LoginPresenter
     * has implemented this interface.
     */
    interface LoginContractView {
        /**
         * This method will be called to enter the main activity of the application.
         *
         * @param isRegister - user registration indicator
         *                   if value equals true - user is register
         *                   otherwise - not registered
         */
        void login(boolean isRegister,int userId);

        /**
         * Initializes invoking activity with required values
         *
         * @param rmbme - remember me indicator
         * @param isLogOut - logout indicator
         * @param isRegister - user registration indicator
         * @param login - user login, null if user not register
         * @param password - user password, null if user not register
         */
        void initBundle(boolean rmbme, boolean isLogOut, boolean isRegister, @Nullable String login,@Nullable String password);

        /**
         * Usied to show popup message
         */
        void showMessage(String message);

        /**
         * Used to show signInLayout
         */
        void showSignInLayout();
    }
}
