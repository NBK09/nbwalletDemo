package e2eTest;


import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.accountDTO.AccountResponse;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.controllers.ManagerController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import com.NBWallet.layers.web.page.LoginPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uiTest.BaseWebTest;

public class TransferTest extends BaseWebTest {

    @Tag("E2E")
    @Test
    void transferTest() {

        // 1️⃣ Токен менеджера
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        ManagerController managerController = new ManagerController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);

        // 2️⃣ Создание первого клиента
        Customer firstCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(firstCustomer);

        AuthStrategy firstCustomerToken = AuthStrategyFactory.getStrategy(
                "customer",
                firstCustomer.getEmail(),
                firstCustomer.getPassword()
        );

        // 3️⃣ Создание счёта
            open("login", LoginPage.class)
                    .waitForPageLoaded()
                    .fillEmail(firstCustomer.getEmail())
                    .fillPassword(firstCustomer.getPassword())
                    .clickBtnLogin()
                    .clickBtnCreate()
                    .selectAccountPlan("Platinum")
                    .selectAccountCurrency("Usd")
                    .clickBtnSubmit();


        // 4 Создание второго клиента
        Customer secondCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(secondCustomer);

        AuthStrategy secondCustomerToken = AuthStrategyFactory.getStrategy(
                "customer",
                secondCustomer.getEmail(),
                secondCustomer.getPassword()
        );

        // 5 Создание счёта
        open("login", LoginPage.class)
                .waitForPageLoaded()
                .fillEmail(secondCustomer.getEmail())
                .fillPassword(secondCustomer.getPassword())
                .clickBtnLogin()
                .clickBtnCreate()
                .selectAccountPlan("Platinum")
                .selectAccountCurrency("Usd")
                .clickBtnSubmit();



    }
}
