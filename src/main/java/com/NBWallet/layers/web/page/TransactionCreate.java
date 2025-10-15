package com.NBWallet.layers.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransactionCreate extends BasePage<TransactionCreate>{
    public SelenideElement selectAccountNumber = $(By.id("source-account"));
    public SelenideElement destinationInput = $(By.id("destination"));
    public SelenideElement amount = $x("//input[@step='any']");
    public SelenideElement notesInput = $x("//input[@placeholder='Notes']");
    public SelenideElement submitBtn = $x("(//button[text()='Submit'])[1]");
    public SelenideElement confirmBtn = $x("(//button[text()='Submit'])[2]");

    @Override
    public TransactionCreate waitForPageLoaded() {
        return null;
    }

    public TransactionCreate selectNumberCard(String numberCard){
        elementManager.selectByValue(selectAccountNumber, numberCard);
        return this;
    }

    public TransactionCreate fillDestination(String numberCard){
        elementManager.input(destinationInput, numberCard);
        return this;
    }

    public TransactionCreate fillAmount(String transactionAmount){
        elementManager.input(amount, transactionAmount);
        return this;
    }

    public TransactionCreate fillNotes(String notes){
        elementManager.input(notesInput, notes);
        return this;
    }

    @Step("Click submit button")
    public TransactionCreate clickBtnSubmit(){
        elementManager.click(submitBtn);
        return this;
    }

    @Step("Click confirm button")
    public AccountsPage clickConfirmBtn(){
        elementManager.click(confirmBtn);
        return Selenide.page(AccountsPage.class);
    }
}
