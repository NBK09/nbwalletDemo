package com.NBWallet.layers.api.controllers;

import com.NBWallet.layers.api.DTO.CustomersResponse;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

// 1
import static com.NBWallet.layers.api.enums.Endpoints.*;

public class ManagerController extends ApiRequest {

    public ManagerController(String URL, AuthStrategy token) {
        super(URL, token);
        this.URL = URL;
        requestSpecification = new RequestSpecBuilder()
                .setPort(7070)
                .setContentType(ContentType.JSON)
                .setBaseUri(this.URL)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .build();
    }

    public CustomersResponse getAllCustomers() {
        this.response = get(getEndpoint(MANAGER_API.toString(), V1.toString(), CUSTOMERS.toString()));
        return this.response.as(CustomersResponse.class);
    }
}
