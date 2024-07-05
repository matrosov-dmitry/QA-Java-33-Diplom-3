package ru.matrosov.pages;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage
    {

    WebDriver webDriver;

    public AccountPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    //Заголовок Профиль в личном кабинете
    private final By profileText = By.xpath(".//a[text() = 'Профиль']");
    //Кнопка Конструктор в личном кабинете
    private final By constructorButton = By.xpath(".//p[text() = 'Конструктор']");
    //Кнопка Выйти из личного кабинета
    private final By exitButton = By.xpath(".//button[text() = 'Выход']");

    @Step("Проверка наличия текста Профиль в личном кабинете")
    public Boolean isDisplayedProfileText(){
        Boolean displayed = webDriver.findElement(profileText).isDisplayed();
        return displayed;
    }

    @Step("Клик по кнопке Конструктор")
    public AccountPage clickOnConstructorButton(){
        webDriver.findElement(constructorButton).click();
        return this;
    }

    @Step("Клик по кнопке Выйти")
    public AccountPage clickOnExitButton(){
        webDriver.findElement(exitButton).click();
        return this;
    }
}
