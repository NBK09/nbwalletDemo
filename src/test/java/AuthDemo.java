import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.api.controllers.IdentityController;
import com.NBWallet.layers.api.models.Token;
import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import org.junit.jupiter.api.Test;


public class AuthDemo {
    @Test
    void demoTest() {

        AuthStrategy managerToken = AuthStrategyFactory.getStrategy("manager");
        Token token = new Token();
        IdentityController identityController = new IdentityController(ConfigurationManager.getAppConfig().baseUrl(), managerToken);
        System.out.println("JWT: " + managerToken.getToken() + "\n" + "RefreshToken " + managerToken.);
        System.out.println("JWT: " + token.getJwtToken() + "\n" + "RefreshToken " + token.getRefreshToken());

    }
}
