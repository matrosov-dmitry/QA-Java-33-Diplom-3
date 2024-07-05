package ru.matrosov;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
import ru.matrosov.pages.RegistrationPage;
import static ru.matrosov.constant.URL.STELLAR_BURGERS_HOME_PAGE_URL;
import static ru.matrosov.api.UserApiGenerator.randomUser;
import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;

@RunWith(Parameterized.class)
public class TestRegistrationPage
    {

    Faker faker = new Faker();
    UserApiMethod userApiMethod = new UserApiMethod();
    UserApi user = randomUser();

    @Rule
    public BrowserRule rule;

    public TestRegistrationPage(BrowserRule rule) {
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
    }

    @Test
    @DisplayName("Заполнение формы и регистрация с валидными данными")
    public void fillingOutTheRegistrationForm(){
        RegistrationPage registrationPage = new RegistrationPage (rule.getWebDriver());

        registrationPage
                .openRegistrationPage()
                .enterName(user.getName())
                .enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .tapOnButtonRegistration ()

                .checkRegistrationSuccess();
    }

    @Test
    @DisplayName("Заполнение формы регистрации с некорректным паролем: пароль 5 символов")
    public void fillingRegistrationFormWithIncorrectPassword(){
        RegistrationPage registrationPage = new RegistrationPage (rule.getWebDriver());

        registrationPage
                .openRegistrationPage()
                .enterName(user.getName())
                .enterEmail(user.getPassword())
                .enterPassword(faker.bothify("29???"))
                .tapOnButtonRegistration ()
                
                .checkIncorrectPassword();
    }

    @After
    public void tearDown(){

        Response response = userApiMethod.login(user);
        if(response.statusCode() == 200) {userApiMethod.delete(user);}

        clearBrowserCookies();
    }
}
