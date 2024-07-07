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
import ru.matrosov.pages.AccountPage;
import ru.matrosov.pages.HomePage;
import ru.matrosov.pages.LoginPage;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;
import static ru.matrosov.api.UserApiGenerator.randomUser;
import static ru.matrosov.constant.URL.STELLAR_BURGERS_HOME_PAGE_URL;

@RunWith(Parameterized.class)
public class TestGoToAccount
    {

        @Rule
        public BrowserRule rule;
        UserApi user = randomUser ();
        UserApiMethod userApiMethod = new UserApiMethod ();

        public TestGoToAccount(BrowserRule rule) {
            this.rule = rule;
        }

        @Parameterized.Parameters
        public static Object[][] getData( ) {
            return new Object[][]{
                    {new YandexRule ()},
                    {new ChromeRule ()}
            };
        }

        @Before
        public void setUp( ) {
            RestAssured.baseURI = STELLAR_BURGERS_HOME_PAGE_URL;
            userApiMethod.create (user);

            LoginPage loginPage = new LoginPage (rule.getWebDriver ());
            loginPage
                    .openLoginPage ()
                    .enterEmail (user.getEmail ())
                    .enterPassword (user.getPassword ())
                    .clickOnButtonLoginInFormAuth ()
                    .checkHomePageAfterAuth ();
        }

        @Test
        @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
        public void checkGoToAccountByPersonalAccountButton( ) {
            AccountPage accountPage = new AccountPage (rule.getWebDriver ());
            HomePage homePage = new HomePage (rule.getWebDriver ());

            homePage
                    .clickOnPersonalAccountButtonHp ();
            accountPage
                    .isDisplayedProfileText ();
        }

        @After
        public void tearDown( ) {
            userApiMethod.delete (user);
            clearBrowserCookies ();
        }
    }
