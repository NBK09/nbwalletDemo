package com.NBWallet.layers.api.controllers;

import com.NBWallet.layers.api.DTO.AccountPlanResponse;
import com.NBWallet.layers.api.DTO.CustomersResponse;
import com.NBWallet.layers.api.models.AccountPlan;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.events.Event;

// 1
import static com.NBWallet.layers.api.enums.Endpoints.*;
@Slf4j
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

    //Account Plan
    public AccountPlan newAccountPlan(AccountPlan accountPlan){
        this.response = post(getEndpoint(MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(accountPlan));
        return this.response.as(AccountPlan.class);
    }

    public AccountPlanResponse getAllAccountPlan(){
        this.response = get(getEndpoint(MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath()));
        return this.response.as(AccountPlanResponse.class);
    }

    public void deleteAccountPlan(String id){
        this.response = delete(getEndpoint(MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath(),ID.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(id));
    }

    public void putAccountPlan(AccountPlan accountPlan) {
        // Выполняем запрос
        this.response = put(
                getEndpoint(MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath(), ID.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(accountPlan)
        );
    }



    public CustomersResponse getAllCustomers() {
        this.response = get(getEndpoint(MANAGER_API.toString(), V1.toString(), CUSTOMERS.toString()));
        return this.response.as(CustomersResponse.class);
    }
}
