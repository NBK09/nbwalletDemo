package com.NBWallet.layers.api.request;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.models.Token;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginClient {
    private static final String BASE_URL = ConfigurationManager.getAppConfig().baseUrl();

    public static String getToken(String username, String password){
        Response response = given().baseUri(BASE_URL)
                .port(5050)
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "username", username,
                        "password", password
                )).when()
                .post("/api/v1/authentication/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Token tokenResponse = response.as(Token.class);
        return tokenResponse.getJwtToken();
    }
}
