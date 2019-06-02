package com.example.myapplication.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.presentation.presenter.login.LoginPresenter;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LogInActivity extends BaseToolbarActivity implements LoginPresenter.LoginContractView {

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getPreferences(MODE_PRIVATE);
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