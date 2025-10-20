package apiTest;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.controllers.CustomerController;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.data.UserFactory;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import com.NBWallet.layers.db.repositories.AccountPlanRepository;
import com.NBWallet.layers.db.repositories.AccountRepository;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private final AccountPlanRepository accountPlanRepository = new AccountPlanRepository();

    private final AccountRepository accountRepository = new AccountRepository();

    private static final AuthStrategy customerToken = AuthStrategyFactory.getStrategy("customer", "aloha@gmail.com", "Parol123!");

    @Test
    void deleteAccount() {

        CustomerController customerController = new CustomerController(ConfigurationManager.getAppConfig().baseUrl(), customerToken);

        var identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), customerToken);

        var newCustomer = UserFactory.generateCustomer();

        var createCustomerResponse = identityController.signUpNewUser(newCustomer);


        customerController.deleteCustomer();
    }


    @Test
    void getDbAccountById() {
//        var accountToCreate = AccountPlan.builder()
//                .name("Gold5")
//                .annualServicePrice(4000.00)
//                .build();
//
//        accountPlanRepository.create(accountToCreate);

        var accountToUpdate = accountPlanRepository.getByName("Gold");

        accountToUpdate.setName("Gold10");
        accountToUpdate.setAnnualServicePrice(1500.23);

        accountPlanRepository.update(accountToUpdate);

        System.out.println(accountPlanRepository.getByName("Gold10"));
    }
}
