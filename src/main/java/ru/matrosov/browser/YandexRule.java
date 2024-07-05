package ru.matrosov.browser;

public class YandexRule extends BrowserRule {

    @Override
    protected String getDriverPath() {
        return "src/test/resources/yandexdriver";
    }
}
