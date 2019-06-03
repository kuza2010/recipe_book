package com.example.myapplication.presentation.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.presentation.presenter.login.LoginPresenter;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LogInActivity extends BaseToolbarActivity implements LoginPresenter.LoginContractView {
    public static final int DELAY = 3;

    @Inject
    LoginPresenter presenter;
    @Inject
    RecipesPreferences preferences;

    @BindView(R.id.input_email_edit_text)
    EditText login;
    @BindView(R.id.input_password_edit_text)
    EditText pass;
    @BindView(R.id.remember_me_switch)
    SwitchCompat rememberMe;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;

    private Bundle bundle;
    private boolean remember;
    private boolean isLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        doInject();

        presenter.checkRememberMeCredentials();
        remember = bundle.getBoolean(RecipesPreferences.REMEMBER_ME, false);
        isLogOut = bundle.getBoolean(RecipesPreferences.IS_LOG_OUT, true);

        rememberMe.setChecked(remember);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (remember && !isLogOut)
            tryLogin();
    }

    private void doInject() {
        BaseApp.getComponent().inject(this);
        ButterKnife.bind(this);
        presenter.bind(this);
    }

    private void tryLogin() {
        String login = bundle.getString(RecipesPreferences.LOGIN);
        String pass = bundle.getString(RecipesPreferences.PASS);
        this.login.setText(login);
        this.pass.setText(pass);

        presenter.tryToLogin(login, pass,rememberMe.isChecked());
    }

    private void setVisibleSignInLayout(int visible) {
        if (visible == VISIBLE) {
            hideToolbar();
            mainLayout.setVisibility(View.INVISIBLE);
            frameLayout.setVisibility(VISIBLE);
        } else {
            showtoolbar();
            mainLayout.setVisibility(VISIBLE);
            frameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void login() {
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void initBundle(boolean rmbme, boolean isLogOut,String login, String password) {
        bundle = new Bundle();
        bundle.putBoolean(RecipesPreferences.IS_LOG_OUT, isLogOut);
        bundle.putBoolean(RecipesPreferences.REMEMBER_ME, rmbme);
        bundle.putString(RecipesPreferences.LOGIN, login);
        bundle.putString(RecipesPreferences.PASS, password);
    }

    @Override
    public void showMessage(String message) {
        popupToast(message, DELAY);
        setVisibleSignInLayout(INVISIBLE);
    }

    @Override
    public void showSignInLayout() {
        setVisibleSignInLayout(VISIBLE);
    }

    @OnClick(R.id.login_btn)
    public void loginClick() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        presenter.tryToLogin(login.getText().toString(), pass.getText().toString(), rememberMe.isChecked());
    }
}