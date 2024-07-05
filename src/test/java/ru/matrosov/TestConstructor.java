package ru.matrosov;

import io.qameta.allure.junit4.DisplayName;
import ru.matrosov.browser.BrowserRule;
import ru.matrosov.browser.ChromeRule;
import ru.matrosov.browser.YandexRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.matrosov.pages.HomePage;
import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;

@RunWith(Parameterized.class)
public class TestConstructor
    {

    @Rule
    public BrowserRule rule;

    public TestConstructor(BrowserRule rule) {
        this.rule = rule;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                { new YandexRule() },
                { new ChromeRule() }
        };
    }

    @Before
    public void setUp(){
        HomePage homePage = new HomePage(rule.getWebDriver());
        homePage
                .openHomePage();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Соусы")
    public void goToSaucesSection(){
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnSaucesButton()
                .previousAndNextButtonsAreNotSelected();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Начинки")
    public void goToFillingsSection(){
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnFillingsButton()
                .previousTwoButtonsAreNotSelected();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Булки")
    public void goToBunsSection(){
        HomePage homePage = new HomePage(rule.getWebDriver());

        homePage
                .clickOnFillingsButton()
                .clickOnBunsButton()
                .nextTwoButtonsAreNotSelected();
    }

    @After
    public void tearDown(){
        clearBrowserCookies();
    }
}
