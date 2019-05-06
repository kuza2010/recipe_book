package com.example.myapplication.presentation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.BaseApp;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.CategoryServiceImpl;
import com.example.myapplication.framework.retrofit.model.Categories;
import com.example.myapplication.presentation.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LogInActivity extends Activity implements LoginPresenter.LoginContractView {
    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        doInject();
    }

    void doInject() {
        BaseApp.getComponent().inject(this);
        ButterKnife.bind(this);
        presenter.bind(this);
    }

    @Override
    public void login() {
        Timber.d("after network activity. Successful login...");
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
    }

    @OnClick(R.id.login_btn)
    public void login(Button loginButton) {
        //TODO: validate login, password
        presenter.login();
    }
}