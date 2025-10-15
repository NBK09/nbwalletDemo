package com.NBWallet.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage<LoginPage>{

    public SelenideElement inputEmail = $(By.id("email"));
    public SelenideElement inputPassword = $(By.id("password"));
    public SelenideElement btnLogin = $x("//button[normalize-space(text())='Login']");


    @Override
    public LoginPage waitForPageLoaded() {
        btnLogin.shouldBe(Condition.text("Login"));
        return this;
    }
    @Step("Input email {0}")
    public LoginPage fillEmail(String email){
        elementManager.input(inputEmail, email);
        return this;
    }

    @Step("Input password {0}")
    public LoginPage fillPassword(String password){
        elementManager.input(inputPassword, password);
        return this;
    }

    @Step("Click button login")
    public AccountsPage clickBtnLogin(){
        elementManager.click(btnLogin);
        return Selenide.page(AccountsPage.class);
    }

}
