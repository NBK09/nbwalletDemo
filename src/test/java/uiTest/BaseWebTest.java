package uiTest;

import com.NBWallet.layers.api.request.stratgy.AuthStrategy;
import com.NBWallet.layers.api.request.stratgy.AuthStrategyFactory;
import com.NBWallet.layers.db.utils.DbConnection;
import com.NBWallet.layers.web.manager.WebDriverManager;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.NBWallet.common.config.ConfigurationManager.getAppConfig;

public class BaseWebTest {
    private final String BASE_URL = getAppConfig().baseUrl();
    private final String PORT = ":8080";
    protected static AuthStrategy managerToken;
    protected static AuthStrategy customerToken;



    @Step("Open the {1}")
    public <T> T open(String endPoint, Class<T> clazz) {
        T page = Selenide.open(String.format("%s/%s", BASE_URL + PORT, endPoint), clazz);
        WebDriverManager.configureRemoteDriver();
        return page;
    }

    @BeforeAll
    public static void setUp(){
        managerToken = AuthStrategyFactory.getStrategy("manager");
        WebDriverManager.configureBasicWebDriver();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @AfterAll
    public static void tearDown(){
        SelenideLogger.removeListener("AllureSelenide");
    }
}
