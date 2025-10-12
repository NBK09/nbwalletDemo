import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.DTO.AccountPlanResponse;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.controllers.ManagerController;
import com.NBWallet.layers.api.models.AccountPlan;
import com.NBWallet.layers.api.models.Token;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;


public class AuthDemo {
    @Test
    void demoTest() {

        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        ManagerController managerController = new ManagerController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);



    }
}
