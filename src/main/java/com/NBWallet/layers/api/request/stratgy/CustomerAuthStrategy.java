package com.NBWallet.layers.api.request.stratgy;

import com.NBWallet.layers.api.request.LoginClient;

public class CustomerAuthStrategy implements AuthStrategy{
   private String login;
   private String password;

    public CustomerAuthStrategy(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getToken() {
        return LoginClient.getToken(login, password);
    }
}
