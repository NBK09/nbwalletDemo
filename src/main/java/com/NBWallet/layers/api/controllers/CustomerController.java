package com.NBWallet.layers.api.controllers;


import com.NBWallet.layers.api.DTO.AccountResponse;
import com.NBWallet.layers.api.models.AccountPlan;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.NBWallet.layers.api.enums.Endpoints.*;

public class CustomerController extends ApiRequest {
    public CustomerController(String URL, AuthStrategy token) {
        super(URL, token);
        this.URL = URL;
        requestSpecification = new RequestSpecBuilder()
                .setPort(6060)
                .setContentType(ContentType.JSON)
                .setBaseUri(this.URL)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .build();
    }

    public Response getAllAccount(){
        this.response = get(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT.getPath()));
        return this.response;
    }

    public AccountResponse createNewAccount(AccountResponse accountResponse){
        this.response = post(getEndpoint(API.getPath(), V1.getPath(),ACCOUNT.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(accountResponse));
        return this.response.as(AccountResponse.class);
    }


}
