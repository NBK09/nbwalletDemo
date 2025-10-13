package apiTest;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.models.Customer;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    void deleteAccount(){
        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        Customer customer = UserFactory.generateCustomer();
        identityController.signUpNewUser(customer);
        String login = customer.getEmail();
        String password = customer.getPassword();
        AuthStrategy customerToken = AuthStrategyFactory.getStrategy("customer", login, password);
        CustomerController customerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), customerToken);
        customerController.deleteCustomer();

    }
}
