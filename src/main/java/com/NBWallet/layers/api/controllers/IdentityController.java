package com.NBWallet.layers.api.controllers;

import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.NBWallet.layers.api.enums.Endpoints.*;

public class IdentityController extends ApiRequest {

    public IdentityController(String URL, AuthStrategy token) {
        super(URL, token);
        this.URL = URL;
        requestSpecification = new RequestSpecBuilder()
                .setPort(5050)
                .setContentType(ContentType.JSON)
                .setBaseUri(this.URL)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .build();
    }

    public Response signUpNewUser(Customer customer){
        this.response = post(getEndpoint(API.getPath(),V1.getPath(),AUTHENTICATION.getPath(),SIGN_UP.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(customer));
        return this.response;
    }

    public Response revokeUser(){
        this.response = delete(getEndpoint(API.getPath(), V1.getPath(),AUTHENTICATION.getPath(),REVOKE.getPath()));
        return this.response;
    }

    public Response refreshToken(){
        return this.response;
    }
}

