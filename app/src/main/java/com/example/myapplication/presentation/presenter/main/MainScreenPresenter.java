package com.example.myapplication.presentation.presenter.main;

import com.example.myapplication.framework.retrofit.model.Category;
import com.example.myapplication.presentation.presenter.BasePresenter;

import java.util.List;

public interface MainScreenPresenter<T> extends BasePresenter<T> {

    public void fetch();

    public interface MainContractView {
    }
}
