package apiTest;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.AccountResponse;
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
    void transferTest(){
        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        ManagerController managerController = new ManagerController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);


        identityController.signUpNewUser(UserFactory.generateCustomer());
        String login = UserFactory.generateCustomer().getEmail();
        String password = UserFactory.generateCustomer().getPassword();
        AuthStrategy customerToken = AuthStrategyFactory.getStrategy("customer", login, password);
        CustomerController customerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), customerToken);

        customerController.createNewAccount(AccountResponse.builder()
                        .accountPlanId(2)
                        .currency(1)
                        .build());


    }
}
