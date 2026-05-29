package pages;

import org.openqa.selenium.WebDriver;

/**
 * LoginPage merepresentasikan halaman login admin Resto Kita (/login).
 * Locator mengacu ke Locators.java (Centralized Locators).
 */
public class LoginPage extends BasePage {

    public static final String LOGIN_URL = "https://resto-kita.vercel.app/login";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(LOGIN_URL);
    }

    public void enterEmail(String email) {
        inputText(Locators.ADMIN_EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        inputText(Locators.ADMIN_PASSWORD_INPUT, password);
    }

    public void clickSignIn() {
        click(Locators.ADMIN_SUBMIT_BTN);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(Locators.LOGIN_ERROR_MSG);
    }

    public String getErrorMessage() {
        return getText(Locators.LOGIN_ERROR_MSG);
    }
}
