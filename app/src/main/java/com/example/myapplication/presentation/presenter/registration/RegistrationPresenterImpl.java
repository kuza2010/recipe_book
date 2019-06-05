package com.example.myapplication.presentation.presenter.registration;

import com.example.myapplication.framework.retrofit.model.login.Registration;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.login.LoginServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import java.util.function.Predicate;

import timber.log.Timber;

public class RegistrationPresenterImpl extends AbstractBasePresenter<RegistrationPresenter.RegistrationContractView>
        implements RegistrationPresenter<RegistrationPresenter.RegistrationContractView> {

    private LoginServices loginService;

    public RegistrationPresenterImpl(LoginServices loginServices) {
        this.loginService = loginServices;
    }

    @Override
    public void registration(String name, String surname, String password, String repeatPass, String login) {
        if (isAllValid(name, surname, password, repeatPass, login)) {
            Registration.RegistrationBody credentials = new Registration.RegistrationBody.Builder()
                    .setLogin(login)
                    .setName(name)
                    .setPassword(password)
                    .setSurname(surname)
                    .build();

            Timber.d("registration: %s", credentials);

            loginService.registration(credentials, new NetworkCallback<Void>() {
                @Override
                public void onResponse(Void registration) {
                    Timber.d("onResponse: user %s successful registered!", credentials.getName());
                    view.registration(password, login);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Timber.e("onFailure: registration failure, message %s", throwable.getMessage());
                    view.showMessage("Registration failure. Try later.");
                }
            });
        }
    }

    private boolean isAllValid(String name, String surname, String password, String repeatPass, String login) {
        Predicate<String> isValid = s -> s != null && !s.isEmpty();

        if (!isValid.test(name)) {
            view.showMessage("Name is empty!");
            return false;
        }

        if (!isValid.test(surname)) {
            view.showMessage("Surname is empty!");
            return false;
        }

        if (!isValid.test(password)) {
            view.showMessage("Password is empty!");
            return false;
        }

        if (!isValid.test(repeatPass)) {
            view.showMessage("Password is empty!");
            return false;
        }

        if (!isValid.test(login)) {
            view.showMessage("Login is empty!");
            return false;
        }

        if (!password.equals(repeatPass)) {
            view.showMessage("Entered passwords do not match ");
            return false;
        }

        Timber.d("All param for reg valid");
        return true;
    }
}
