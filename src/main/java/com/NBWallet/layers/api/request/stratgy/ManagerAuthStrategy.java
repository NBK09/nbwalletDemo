package com.NBWallet.layers.api.request.stratgy;

import com.NBWallet.layers.api.request.LoginClient;

public class ManagerAuthStrategy implements AuthStrategy{
    @Override
    public String getToken() {
        return LoginClient.getToken("manager@nbwallet.com", "password");
    }
}
