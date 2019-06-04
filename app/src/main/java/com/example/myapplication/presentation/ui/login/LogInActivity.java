package com.example.myapplication.presentation.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.presentation.presenter.login.LoginPresenter;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.SimpleAnimator;
import com.example.myapplication.presentation.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LogInActivity extends BaseToolbarActivity implements LoginPresenter.LoginContractView {
    public static final int DELAY = 3;

    public static final String IS_FROM_PREFERENCES_SCREEN = "is_preferences";

    @Inject
    LoginPresenter presenter;

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
    @BindView(R.id.signin_hint)
    AppCompatTextView signInHint;
    @BindView(R.id.without_registration)
    AppCompatTextView skipRegistration;

    private Bundle bundle;

    private boolean remember;
    private boolean isLogOut;

    public static Intent getInstance(Context packageContext,boolean isFromPreferences) {
        Intent intent = new Intent(packageContext, LogInActivity.class);
        intent.putExtra(IS_FROM_PREFERENCES_SCREEN, isFromPreferences);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        doInject();

        presenter.checkRememberMeCredentials();

        remember = bundle.getBoolean(RecipesPreferences.REMEMBER_ME, false);
        isLogOut = bundle.getBoolean(RecipesPreferences.IS_LOG_OUT, true);
        skipRegistration.setVisibility(fromPrefScreen() ? INVISIBLE : VISIBLE);
        rememberMe.setChecked(remember);
    }

    @Override
    public void initBundle(boolean rmbme, boolean isLogOut, boolean isRegiser, @Nullable String login,@Nullable String password) {
        //TODO: нормальная инифиализация бандлов
        bundle = new Bundle();

        bundle.putBoolean(RecipesPreferences.IS_REGISTRED_USER, isRegiser);
        bundle.putBoolean(RecipesPreferences.IS_LOG_OUT, isLogOut);
        bundle.putBoolean(RecipesPreferences.REMEMBER_ME, rmbme);
        bundle.putString(RecipesPreferences.LOGIN, login);
        bundle.putString(RecipesPreferences.PASS, password);
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
            SimpleAnimator.setDefaultAnimation(Animation.REVERSE,300,signInHint);
        } else {
            showtoolbar();
            mainLayout.setVisibility(VISIBLE);
            frameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void login(boolean isRegister) {
        startActivity(MainActivity.getInstance(this, isRegister).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
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

    @OnClick(R.id.without_registration)
    public void skipRegistrationClick() {
        presenter.skipLogin();
    }

    private boolean fromPrefScreen() {
        return getIntent().getBooleanExtra(IS_FROM_PREFERENCES_SCREEN, false);
    }
}