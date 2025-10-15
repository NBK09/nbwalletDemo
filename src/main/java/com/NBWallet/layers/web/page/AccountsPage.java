package com.NBWallet.layers.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$x;

public class AccountsPage extends BasePage<AccountsPage>{

    public SelenideElement btnCreate = $x("//button[text()='Create']");

    @Step("Click button create")
    public AccountsCreatePage clickBtnCreate(){
        elementManager.click(btnCreate);
        return Selenide.page(AccountsCreatePage.class);
    }


    @Override
    public AccountsPage waitForPageLoaded() {
        return null;
    }
}
