package e2eTest;


import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.accountDTO.AccountResponse;
import com.NBWallet.layers.api.DTO.transactionDTO.TransactionResponse;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.controllers.ManagerController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import com.NBWallet.layers.db.utils.DbConnection;
import com.NBWallet.layers.web.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uiTest.BaseWebTest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferTest extends BaseWebTest {

    @Tag("E2E")
    @Test
    void transferTest() throws SQLException, InterruptedException {

        // 1️⃣ Токен менеджера
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        ManagerController managerController = new ManagerController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);


        // 2️⃣ Создание клиентов
        Customer firstCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(firstCustomer);

        AuthStrategy firstCustomerToken = AuthStrategyFactory.getStrategy(
                "customer",
                firstCustomer.getEmail(),
                firstCustomer.getPassword()
        );

        Customer secondCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(secondCustomer);

        AuthStrategy secondCustomerToken = AuthStrategyFactory.getStrategy(
                "customer",
                secondCustomer.getEmail(),
                secondCustomer.getPassword()
        );

        // Controllers
        CustomerController firstCustomerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(),
                firstCustomerToken);

        CustomerController secondCustomerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(),
                secondCustomerToken);

        // 3️⃣ Создание счёта
        AccountResponse firstAccount = firstCustomerController.createNewAccount(
                AccountResponse.builder()
                        .accountPlanId(3)
                        .currency(1)
                        .build()
        );

        AccountResponse secondAccount = firstCustomerController.createNewAccount(
                AccountResponse.builder()
                        .accountPlanId(3)
                        .currency(1)
                        .build()
        );
        // 4 Пополняем счет 1 клиента
        DbConnection.openConnection("NBWallet");
        String addFundsQuery = " UPDATE public.\"Account\" "
                + "SET \"Balance\" = ? "
                + "WHERE \"Number\" = ?;";

        DbConnection.executeUpdate(addFundsQuery, 1000, firstAccount.getNumber());


        // 7 Перевод денег

        open("login", LoginPage.class)
                .waitForPageLoaded()
                .fillEmail(firstCustomer.getEmail())
                .fillPassword(firstCustomer.getPassword())
                .clickBtnLogin()
                .clickDropdownMenu()
                .clickTransactionBtn()
                .selectNumberCard(firstAccount.getNumber())
                .fillDestination(secondAccount.getNumber())
                .fillAmount("1000")
                .fillNotes("Долг")
                .clickBtnSubmit()
                .clickConfirmBtn();

        // 8 Подтверждение перевода
        TransactionResponse allTransactions = managerController.getAllTransaction();
        Long transferTransactionId = allTransactions.getItems().stream()
                .filter(t -> t.getSourceAccount().getNumber().equals(firstAccount.getNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Транзакция перевода не найдена"))
                .getId();

        managerController.updateTransactionStatus(transferTransactionId, 1);

        // 9 Проверка
        String checkBalanceQuery = "SELECT \"Balance\" FROM public.\"Account\" "
                + "WHERE \"Number\" = ?;";

        ResultSet firstCustomerRs = DbConnection.query(checkBalanceQuery, firstAccount.getNumber());
        ResultSet secondCustomerRs = DbConnection.query(checkBalanceQuery, secondAccount.getNumber());

        double firstCustomerBalance = 0.0;
        double secondCustomerBalance = 0.0;

        if (firstCustomerRs.next()) {
            firstCustomerBalance = firstCustomerRs.getDouble("Balance");
        }
        if (secondCustomerRs.next()) {
            secondCustomerBalance = secondCustomerRs.getDouble("Balance");
        }

        DbConnection.closeConnection();

        Assertions.assertEquals(0.0, firstCustomerBalance, "Баланс не совпадает");
        Assertions.assertEquals(1000.0, secondCustomerBalance, "Баланс не совпадает");
    }
}
