package com.NBWallet.layers.api.request.stratgy;

import com.NBWallet.layers.api.request.LoginClient;

public class CustomerAuthStrategy implements AuthStrategy{
    @Override
    public String getToken() {
        return LoginClient.getToken("customerUser", "customerPassword");
    }
}
