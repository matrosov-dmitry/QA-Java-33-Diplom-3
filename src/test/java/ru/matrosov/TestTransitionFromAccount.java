package ru.matrosov;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.matrosov.api.UserApi;
import ru.matrosov.api.UserApiMethod;
import ru.matrosov.browser.BrowserRule;
import ru.matrosov.browser.ChromeRule;
import ru.matrosov.browser.YandexRule;
import ru.matrosov.pages.HomePage;
import ru.matrosov.pages.LoginPage;
import ru.matrosov.pages.AccountPage;
import static ru.matrosov.constant.URL.STELLAR_BURGERS_HOME_PAGE_URL;
import static ru.matrosov.api.UserApiGenerator.randomUser;
import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;

@RunWith(Parameterized.class)
public class TestTransitionFromAccount
    {

    UserApi user = randomUser();
    UserApiMethod userApiMethod = new UserApiMethod();

    @Rule
    public BrowserRule rule;

    public TestTransitionFromAccount(BrowserRule rule) {
        this.rule = rule;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                { new YandexRule () },
                { new ChromeRule () }
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = STELLAR_BURGERS_HOME_PAGE_URL;
        userApiMethod.create(user);

        LoginPage loginPage = new LoginPage (rule.getWebDriver());
        loginPage
                .openLoginPage()
                .enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .clickOnButtonLoginInFormAuth()
                .checkHomePageAfterAuth();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор - по клику на «Конструктор»")
    public void transitionFromAccountByConstructorButton(){
        AccountPage accountPage = new AccountPage (rule.getWebDriver());
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnPersonalAccountButtonHp();
        accountPage
                .clickOnConstructorButton();
        homePage
                .checkConstructorHeaderText();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор - по клику на логотип Stellar Burgers")
    public void transitionFromAccountByLogo(){
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnPersonalAccountButtonHp()
                .clickOnLogoStellarBurgers()
                .checkConstructorHeaderText();
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void transitionFromAccountByExitButton()  {
        AccountPage accountPage = new AccountPage (rule.getWebDriver());
        LoginPage loginPage = new LoginPage (rule.getWebDriver());
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnPersonalAccountButtonHp();
        accountPage
                .clickOnExitButton();
        loginPage
                .isDisplayedEnterText();
    }

    @After
    public void tearDown(){
        userApiMethod.delete(user);
        clearBrowserCookies();
    }
}
