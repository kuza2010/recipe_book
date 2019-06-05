package com.example.myapplication.framework.retrofit.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registration {

    @Expose
    @SerializedName("user_id")
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class RegistrationBody {

        public String name;

        public String surname;

        public String login;

        public String password;

        public RegistrationBody(String name, String surname, String login, String password) {
            this.name = name;
            this.surname = surname;
            this.login = login;
            this.password = password;
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

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "RegistrationBody{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public static class Builder{
            private String name;

            private String surname;

            private String login;

            private String password;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setSurname(String surname) {
                this.surname = surname;
                return this;
            }

            public Builder setLogin(String login) {
                this.login = login;
                return this;
            }

            public Builder setPassword(String password) {
                this.password = password;
                return this;
            }

            public RegistrationBody build(){
                return new RegistrationBody(name,surname,login,password);
            }
        }
    }
}
