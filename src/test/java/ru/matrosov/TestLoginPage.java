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
import ru.matrosov.pages.RegistrationPage;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;
import static ru.matrosov.api.UserApiGenerator.randomUser;
import static ru.matrosov.constant.URL.STELLAR_BURGERS_HOME_PAGE_URL;

@RunWith(Parameterized.class)
public class TestLoginPage
    {

        @Rule
        public BrowserRule rule;
        UserApiMethod userApiMethod = new UserApiMethod ();
        private UserApi user = randomUser ();

        public TestLoginPage(BrowserRule rule) {
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
        }

        @Test
        @DisplayName("Вход в аккаунт по кнопке «Войти в аккаунт» на главной")
        public void inputByButtonToEnterAccountHp( ) {
            LoginPage loginPage = new LoginPage (rule.getWebDriver ());
            HomePage homePage = new HomePage (rule.getWebDriver ());

            homePage
                    .openHomePage ()
                    .clickOnButtonToEnterAccountHp ();
            loginPage
                    .waitingForLoading ()
                    .enterEmail (user.getEmail ())
                    .enterPassword (user.getPassword ())
                    .clickOnButtonLoginInFormAuth ()
                    .checkHomePageAfterAuth ();
        }

        @Test
        @DisplayName("Вход в аккаунт через кнопку «Личный кабинет» на главной")
        public void inputByPersonalAccountButtonHp( ) {
            LoginPage loginPage = new LoginPage (rule.getWebDriver ());
            HomePage homePage = new HomePage (rule.getWebDriver ());

            homePage
                    .openHomePage ()
                    .clickOnPersonalAccountButtonHp ();
            loginPage
                    .enterEmail (user.getEmail ())
                    .enterPassword (user.getPassword ())
                    .clickOnButtonLoginInFormAuth ()
                    .checkHomePageAfterAuth ();
        }

        @Test
        @DisplayName("Вход в аккаунт через кнопку в форме регистрации")
        public void inputByLoginButtonInFormRegistration( ) {
            RegistrationPage registrationPage = new RegistrationPage (rule.getWebDriver ());
            LoginPage loginPage = new LoginPage (rule.getWebDriver ());

            registrationPage
                    .openRegistrationPage ();
            loginPage
                    .clickOnLoginButtonInForms ()
                    .enterEmail (user.getEmail ())
                    .enterPassword (user.getPassword ())
                    .clickOnButtonLoginInFormAuth ()
                    .checkHomePageAfterAuth ();
        }

        @Test
        @DisplayName("Вход в аккаунт через кнопку в форме восстановления пароля")
        public void inputByLoginButtonInFormRestorePassword( ) {
            LoginPage loginPage = new LoginPage (rule.getWebDriver ());

            loginPage
                    .openPasswordRestorePage ()
                    .clickOnLoginButtonInForms ()
                    .enterEmail (user.getEmail ())
                    .enterPassword (user.getPassword ())
                    .clickOnButtonLoginInFormAuth ()
                    .checkHomePageAfterAuth ();
        }

        @After
        public void tearDown( ) {
            userApiMethod.delete (user);
            clearBrowserCookies ();
        }
    }
