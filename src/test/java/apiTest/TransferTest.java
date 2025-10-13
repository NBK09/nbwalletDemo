package apiTest;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.AccountResponse;
import com.NBWallet.layers.api.DTO.AddFundsRequest;
import com.NBWallet.layers.api.DTO.TransactionItemDto;
import com.NBWallet.layers.api.DTO.TransactionResponse;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.controllers.ManagerController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TransferTest {
    @Tag("E2E")
    @Test
    void transferTest() {
        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
        // Account 1
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        ManagerController managerController = new ManagerController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        Customer firstCustomer = UserFactory.generateCustomer();
        identityController.signUpNewUser(firstCustomer);
        String firstUserLogin = firstCustomer.getEmail();
        String firstUserPassword = firstCustomer.getPassword();

        AuthStrategy firstCustomerToken = AuthStrategyFactory.getStrategy("customer", firstUserLogin, firstUserPassword);
        CustomerController firstCustomerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), firstCustomerToken);
        AccountResponse accountResponse = firstCustomerController.createNewAccount(AccountResponse.builder()
                .accountPlanId(2)
                .currency(1)
                .build());

//          firstCustomerController.addFunds(accountResponse.getNumber(), 9999.0);
          TransactionResponse transactionItemDto = managerController.getAllTransaction();

//                managerController.updateTransactionStatus(transactionItemDto.getItems().stream().filter(s -> s.getSourceAccount() == ), "1");

        // Account 2
        Customer customer = UserFactory.generateCustomer();
        identityController.signUpNewUser(customer);
        String lastUserLogin = customer.getEmail();
        String lastUserPassword = customer.getPassword();

        AuthStrategy customerToken = AuthStrategyFactory.getStrategy("customer", lastUserLogin, lastUserPassword);
        CustomerController customerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), customerToken);
        customerController.createNewAccount(AccountResponse.builder()
                .accountPlanId(2)
                .currency(1)
                .build());
    }
}
