package com.NBWallet.layers.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class AccountsCreatePage extends BasePage<AccountsCreatePage>{

    public SelenideElement accountPlans = $x("(//select[@id='source-account'])[1]");
    public SelenideElement accountCurrency = $x("(//select[@id='source-account'])[2]");
    public SelenideElement submitBtn = $x("//button[text()='Submit']");


    @Step("Select Account Plans {0}")
    public AccountsCreatePage selectAccountPlan(String selectText){
        elementManager.jsClick(accountPlans);
        return this;
    }
    @Step("Select Account Currency {0}")
    public AccountsCreatePage selectAccountCurrency(String selectText){
        elementManager.jsClick(accountCurrency);
        return this;
    }

    @Step("Click button submit")
    public AccountsPage clickBtnSubmit(){
        elementManager.click(submitBtn);
        return Selenide.page(AccountsPage.class);
    }

    @Override
    public AccountsCreatePage waitForPageLoaded() {
        return null;
    }
}
