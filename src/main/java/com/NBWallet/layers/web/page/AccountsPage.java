package com.NBWallet.layers.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$x;

public class AccountsPage extends BasePage<AccountsPage>{

    public SelenideElement createBtn = $x("//button[text()='Create']");
    public SelenideElement dropDownMenu = $x("//span[text()='Toggle Dropdown']/..");
    public SelenideElement transactionBtn = $x("//a[text()='Transaction']");

    @Step("Click button create")
    public AccountsCreatePage clickBtnCreate(){
        elementManager.click(createBtn);
        return Selenide.page(AccountsCreatePage.class);
    }

    @Step("Click dropdown menu")
    public AccountsPage clickDropdownMenu(){
        elementManager.click(dropDownMenu);
        return this;
    }

    @Step("Click button transaction")
    public TransactionCreate clickTransactionBtn(){
        elementManager.click(transactionBtn);
        return Selenide.page(TransactionCreate.class);
    }

    @Override
    public AccountsPage waitForPageLoaded() {
        return null;
    }
}
