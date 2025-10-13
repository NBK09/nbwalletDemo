package com.NBWallet.layers.api.request;

import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;


@Getter
@Slf4j
public class ApiRequest {
    protected String URL;
    public Response response;
    protected RequestSpecification requestSpecification;

    public ApiRequest(String URL, AuthStrategy token) {
        this.URL = URL;
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(this.URL)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .build();
        requestSpecification.log();
    }

    public static final String SLASH = "/";

    public static String getEndpoint(String ... args) {
        StringBuilder endpoint = new StringBuilder();
        for (String arg : args) {
            endpoint.append(arg)
                    .append(SLASH);
        }
        return endpoint.substring(0, endpoint.length() -1);
    }

    public Response get(String endpoint) {
        log.info("Performed GET {}", endpoint);
        this.response = given()
                .spec(requestSpecification)
                .get(endpoint);
        logResponse();
        return this.response;
    }

    public Response post(String endpoint, String body) {
        log.info("Performed POST {}", endpoint);
        log.info("Body is {}", body);
        this.response = given()
                .spec(requestSpecification)
                .body(body)
                .post(endpoint);
        logResponse();
        return this.response;
    }

    public Response delete(String endpoint, String body){
        log.info("Performed DELETE {}", endpoint);
        log.info("Body is {}", body);
        this.response = given()
                .spec(requestSpecification)
                .body(body)
                .delete(endpoint);
        logResponse();
        return this.response;
    }

    public Response put(String endpoint, String body){
        log.info("Performed PUT {}", endpoint);
        log.info("Body is {}", body);
        this.response = given()
                .spec(requestSpecification)
                .body(body)
                .put(endpoint);
        logResponse();
        return this.response;
    }


    private void logResponse() {
        log.warn("Response is \n{}", getResponse().getBody().asPrettyString());
    }
}
