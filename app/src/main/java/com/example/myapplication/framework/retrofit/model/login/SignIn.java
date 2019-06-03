package com.example.myapplication.framework.retrofit.model.login;

import com.google.gson.annotations.SerializedName;

public class SignIn {
    @SerializedName("name")
    public String name;

    public String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String pass;

    @SerializedName("surname")
    public String surname;

    @SerializedName("id_user")
    public int userId;

    @SerializedName("token")
    public String token;

    @Override
    public String toString() {
        return "SignIn{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
