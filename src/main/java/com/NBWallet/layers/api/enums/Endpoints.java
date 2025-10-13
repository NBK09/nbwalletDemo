package com.NBWallet.layers.api.enums;


import lombok.Getter;

public enum Endpoints {
    // base
    API("api"),
    MANAGER_API("manager-api"),
    V1("v1"),
    ID("id"),

    // auth
    AUTHENTICATION("authentication"),
    LOGIN("login"),
    SIGN_UP("signup"),
    REVOKE("revoke"),
    REFRESH("refresh"),

    // api segments
    ACCOUNT("account"),
    ACCOUNT_PLANS("account-plans"),
    ACCOUNT_TRANSACTION_LIMITS("account-transaction-limits"),
    CUSTOMERS("customers"),
    TRANSACTIONS("transactions"),
    INFO("info"),
    ADD_FUNDS("add-funds"),

    // manager segments
    BLACKLISTS("blacklists"),
    TRANSACTION_STATUS_BY_ID("transactions/{id}/status");

    @Getter
    private final String path;

    Endpoints(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}