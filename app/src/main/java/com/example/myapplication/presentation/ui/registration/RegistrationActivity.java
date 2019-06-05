package com.example.myapplication.presentation.ui.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.widget.EditText;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.presentation.presenter.registration.RegistrationPresenter;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.login.LogInActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class RegistrationActivity extends BaseToolbarActivity
        implements RegistrationPresenter.RegistrationContractView {

    @Inject
    RegistrationPresenter presenter;

    @BindView(R.id.register_btn)
    AppCompatButton register;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_repeat)
    EditText passwordRepeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.registration);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);

        presenter.bind(this);
    }

    @Override
    public void showMessage(String message) {
        popupToast(message, STANDART_DELAY);
    }

    @Override
    public void registration(String login, String password) {
        popupToast("User registered", STANDART_DELAY);
        startActivity(LogInActivity.getInstance(this,false));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }

    @OnClick(R.id.register_btn)
    public void onRegisterClick() {
        String name = this.name.getText().toString();
        String surname = this.surname.getText().toString();
        String password = this.password.getText().toString();
        String password2 = this.passwordRepeat.getText().toString();
        String login = this.login.getText().toString();

        presenter.registration(name, surname, password, password2, login);
    }
}
