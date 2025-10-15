package com.NBWallet.layers.api.controllers;

import com.NBWallet.layers.api.DTO.accountDTO.AccountPlanResponse;
import com.NBWallet.layers.api.DTO.customerDTO.CustomersResponse;
import com.NBWallet.layers.api.DTO.transactionDTO.TransactionResponse;
import com.NBWallet.layers.api.models.AccountPlan;
import com.NBWallet.layers.api.models.BlacklistRequest;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

// 1

import java.util.Map;

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

    public void deleteAccountPlan(Long id) {
        String endpoint = getEndpoint(
                MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath(), String.valueOf(id));
        this.response = delete(endpoint);

    }

    public void putAccountPlan(AccountPlan accountPlan) {
        String endpoint = getEndpoint(MANAGER_API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath(),
                String.valueOf(accountPlan.getId()));
        this.response = put(endpoint, ObjectConverter.convertJavaObjectToJsonObject(accountPlan));
    }

    // Blacklist
    public BlacklistRequest addToBlacklist(BlacklistRequest request){
        this.response = post(getEndpoint(MANAGER_API.getPath(), V1.getPath(), BLACKLISTS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(request));
        return this.response.as(BlacklistRequest.class);
    }

    public Response getAllBlacklist(){
        this.response = get(getEndpoint(MANAGER_API.getPath(),V1.getPath(),BLACKLISTS.getPath()));
        return this.response;
    }


// Customer
    public CustomersResponse getAllCustomers() {
        this.response = get(getEndpoint(MANAGER_API.toString(), V1.toString(), CUSTOMERS.toString()));
        return this.response.as(CustomersResponse.class);
    }


    //Transaction
    public TransactionResponse getAllTransaction(){
        this.response = get(getEndpoint(MANAGER_API.getPath(), V1.getPath(), TRANSACTIONS.getPath()));
        return this.response.as(TransactionResponse.class);
    }

    public void updateTransactionStatus(Long id, int status) {
        // Формируем эндпоинт с подстановкой ID
        String endpoint = getEndpoint(
                MANAGER_API.getPath(),
                V1.getPath(),
                TRANSACTION_STATUS_BY_ID.getPath()
        ).replace("{id}", String.valueOf(id));

        // Формируем тело запроса, соответствующее Swagger-схеме
        Map<String, Object> body = Map.of(
                "id", id,
                "status", status
        );

        // Выполняем PUT-запрос
        this.response = put(endpoint, ObjectConverter.convertJavaObjectToJsonObject(body));

        log.info("✅ Updated transaction {} → status '{}'; response code: {}",
                id, status, response.getStatusCode());
    }

    public Response confirmTransaction(Long transactionId) {
        Map<String, Object> body = Map.of(
                "command", Map.of("status", "Approved") // именно так ожидает API
        );

        this.response = put(
                getEndpoint(MANAGER_API.getPath(), V1.getPath(), TRANSACTIONS.getPath(), transactionId.toString(), "status"),
                ObjectConverter.convertJavaObjectToJsonObject(body)
        );

        log.info("Updated transaction {} status to 'Approved', response code: {}", transactionId, response.statusCode());
        return this.response;
    }
}
