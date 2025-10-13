package com.NBWallet.layers.api.controllers;


import com.NBWallet.layers.api.DTO.*;
import com.NBWallet.layers.api.request.ApiRequest;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.utils.ObjectConverter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.NBWallet.layers.api.enums.Endpoints.*;
@Slf4j
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

    public Response getAllAccountPlan(){
        this.response = get(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT_PLANS.getPath()));
        return this.response;
    }

    public AccountTransactionLimitDto createAccountLimit(AccountTransactionLimitDto accountTransactionLimitDto){
        this.response = post(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT_TRANSACTION_LIMITS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(accountTransactionLimitDto));
        return this.response.as(AccountTransactionLimitDto.class);
    }

    public Response getAllLimit(){
        this.response = get(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT_TRANSACTION_LIMITS.getPath()));
        return this.response;
    }

    public AccountTransactionLimitDto editAccountLimit(AccountTransactionLimitDto accountTransactionLimitDto){
        this.response = put(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT_TRANSACTION_LIMITS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(accountTransactionLimitDto));
        return this.response.as(AccountTransactionLimitDto.class);
    }

    public Response deleteAccountLimit(String id){
        this.response = delete(getEndpoint(API.getPath(), V1.getPath(), ACCOUNT_TRANSACTION_LIMITS.getPath(), id));
        return this.response;
    }

    public Response deleteCustomer() {
        String endpoint = getEndpoint(MANAGER_API.getPath(), V1.getPath(), CUSTOMERS.getPath());
        this.response = delete(endpoint);
        return this.response;
    }

    // ===================== 🏦 ТРАНЗАКЦИИ =====================

    /**
     * POST /api/v1/transactions
     * Создаёт новую транзакцию между аккаунтами
     */
    public List<TransactionItemDto> createTransaction(TransactionRequest request) {
        this.response = post(getEndpoint(API.getPath(), V1.getPath(), TRANSACTIONS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(request));

        if (this.response.statusCode() >= 400) {
            log.error("Transaction creation failed: {}", this.response.getBody().asString());
            throw new RuntimeException("Transaction creation failed: " + this.response.getBody().asString());
        }

        // Обрабатываем разные типы ответа (список или объект)
        String body = this.response.getBody().asString();
        if (body.trim().startsWith("[")) {
            return Arrays.asList((TransactionItemDto) ObjectConverter
                    .convertJsonArrayToListOfObj(body, TransactionItemDto[].class)).reversed();
        } else {
            return List.of(ObjectConverter.convertJsonObjectToJavaObject(body, TransactionItemDto.class));
        }
    }

    /**
     * GET /api/v1/transactions
     * Получает список всех транзакций пользователя
     */
    public TransactionResponse getAllTransactions() {
        this.response = get(
                getEndpoint(API.getPath(), V1.getPath(), TRANSACTIONS.getPath())
        );
        return this.response.as(TransactionResponse.class);
    }

    /**
     * GET /api/v1/transactions/info/{id}
     * Получает информацию о конкретной транзакции
     */
    public TransactionItemDto getTransactionInfo(String transactionId) {
        this.response = get(
                getEndpoint(API.getPath(), V1.getPath(), TRANSACTIONS.getPath(), INFO.getPath(), transactionId)
        );
        return this.response.as(TransactionItemDto.class);
    }

    /**
     * POST /api/v1/transactions/add-funds
     * Пополняет баланс аккаунта
     */
    public AddFundsRequest addFunds(String accountNumber, int amount) {
        var body = Map.of("accountNumber", accountNumber,
                                                                                            "amount", amount);
        this.response = post(
                getEndpoint(API.getPath(), V1.getPath(), TRANSACTIONS.getPath(), ADD_FUNDS.getPath()),
                ObjectConverter.convertJavaObjectToJsonObject(body)
        );
        return this.response.as(AddFundsRequest.class);
    }
}
