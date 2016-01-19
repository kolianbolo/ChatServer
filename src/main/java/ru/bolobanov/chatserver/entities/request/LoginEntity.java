package ru.bolobanov.chatserver.entities.request;

/**
 * Created by Bolobanov Nikolay on 15.01.16.
 */
public class LoginEntity {

    private String login;
    private String password;

    public void setLogin(String pLogin) {
        login = pLogin;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
