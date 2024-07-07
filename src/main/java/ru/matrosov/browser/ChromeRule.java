package ru.matrosov.browser;

public class ChromeRule extends BrowserRule
    {

        @Override
        protected String getDriverPath( ) {
            return "src/test/resources/chromedriver";
        }
    }
