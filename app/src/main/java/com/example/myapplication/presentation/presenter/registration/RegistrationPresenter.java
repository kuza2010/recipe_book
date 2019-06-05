package com.example.myapplication.presentation.presenter.registration;

import com.example.myapplication.presentation.presenter.BasePresenter;

public interface RegistrationPresenter<T> extends BasePresenter<T> {

    void registration(String name, String surname, String password,String repeatPass, String login);

    public interface RegistrationContractView {
        void showMessage(String message);
        void registration(String login,String password);
    }
}
