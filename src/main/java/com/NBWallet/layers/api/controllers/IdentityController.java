package com.NBWallet.layers.api.controllers;

import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.models.Token;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.NBWallet.layers.api.enums.Endpoints.*;

public class IdentityController extends ApiRequest {

    public IdentityController(String URL, AuthStrategy token) {
        super(URL, token);
        this.URL = URL;
        requestSpecification = new RequestSpecBuilder()
                .setPort(5050)
                .setContentType(ContentType.MULTIPART)
                .setBaseUri(this.URL)
                .setAccept(ContentType.JSON)
                .build();
    }

    public Response signUpNewUser(Customer customer){
        Map<String, Object> formData = new HashMap<>();
        formData.put("FirstName", customer.getFirstName());
        formData.put("LastName", customer.getLastName());
        formData.put("Email", customer.getEmail());
        formData.put("Password", customer.getPassword());
        formData.put("PhoneNumber", customer.getPhoneNumber());
        this.response = post(getEndpoint(API.getPath(),V1.getPath(),AUTHENTICATION.getPath(),SIGN_UP.getPath()), formData);
        return this.response;
    }

    public Response revokeUser(){
        this.response = delete(getEndpoint(API.getPath(), V1.getPath(),AUTHENTICATION.getPath(),REVOKE.getPath()));
        return this.response;
    }

    public Token refreshToken(AuthStrategy authStrategy){
        this.response = post(getEndpoint(API.getPath(), V1.getPath(), AUTHENTICATION.getPath(),REFRESH.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(authStrategy));
        return response.as(Token.class);
    }
}

