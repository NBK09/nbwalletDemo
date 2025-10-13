package apiTest;


import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.AccountResponse;
import com.NBWallet.layers.api.DTO.TransactionRequest;
import com.NBWallet.layers.api.DTO.TransactionResponse;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.controllers.ManagerController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TransferTest {

    @Tag("E2E")
    @Test
    void transferTest() {

        // 1️⃣ Токен менеджера
        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
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

        CustomerController firstCustomerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(),
                firstCustomerToken);

        // 3️⃣ Создание счёта
        AccountResponse firstAccount = firstCustomerController.createNewAccount(
                AccountResponse.builder()
                        .accountPlanId(2)
                        .currency(1)
                        .build()
        );

        // 4️⃣ Пополнение счёта клиента
        System.out.println(firstAccount.getNumber());
        firstCustomerController.addFunds(firstAccount.getNumber(), 9999);

        // 5️⃣ Менеджер подтверждает пополнение
        TransactionResponse transactions = managerController.getAllTransaction();
        Long depositTransactionId = transactions.getItems().stream()
                .filter(t -> t.getDestinationAccount().getNumber().equals(firstAccount.getNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Пополнение не найдено"))
                .getId();

        managerController.updateTransactionStatus(depositTransactionId, 1);

        // 6️⃣ Создание второго клиента
        Customer secondCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(secondCustomer);

        AuthStrategy secondCustomerToken = AuthStrategyFactory.getStrategy(
                "customer",
                secondCustomer.getEmail(),
                secondCustomer.getPassword()
        );

        CustomerController secondCustomerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), secondCustomerToken);

        AccountResponse secondAccount = secondCustomerController.createNewAccount(
                AccountResponse.builder()
                        .accountPlanId(2)
                        .currency(1)
                        .build()
        );

        // 7️⃣ Перевод денег второму клиенту
        TransactionRequest transferRequest = TransactionRequest.builder()
                .amount(1500.0)
                .sourceAccountNumber(firstAccount.getNumber())
                .destinationAccountNumber(secondAccount.getNumber())
                .notes("Transfer to second customer")
                .build();

        firstCustomerController.createTransaction(transferRequest);

        // 8️⃣ Менеджер подтверждает перевод
        TransactionResponse allTransactions = managerController.getAllTransaction();
        Long transferTransactionId = allTransactions.getItems().stream()
                .filter(t -> t.getSourceAccount().getNumber().equals(firstAccount.getNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Транзакция перевода не найдена"))
                .getId();

        managerController.updateTransactionStatus(transferTransactionId, 1);

        // 9️⃣ Проверяем балансы
        Response acc1 = firstCustomerController.getAllAccount();
        Response acc2 = secondCustomerController.getAllAccount();

        System.out.println("✅ Баланс клиента 1: " + acc1.getBody().asString());
        System.out.println("✅ Баланс клиента 2: " + acc2.getBody().asString());
    }
}
