package com.example.myapplication.presentation.presenter.main;

import com.example.myapplication.presentation.presenter.BasePresenter;

public interface MainScreenPresenter<T> extends BasePresenter<T> {

    public void fetch();

    public interface MainContractView {
    }
}
