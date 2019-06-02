package com.example.myapplication.framework.retrofit.model.login;

import com.google.gson.annotations.SerializedName;

public class SignIn {
    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("token")
    public String token;
}
