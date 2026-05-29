package pages;

import org.openqa.selenium.WebDriver;

/**
 * CheckoutPage merepresentasikan halaman checkout Resto Kita (/checkout).
 * Berisi form nama & WhatsApp, order summary, pilihan pembayaran, dan Confirm & Pay.
 */
public class CheckoutPage extends BasePage {

    public static final String CHECKOUT_URL = "https://resto-kita.vercel.app/checkout";

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(CHECKOUT_URL);
    }

    public boolean isOnCheckoutPage() {
        return getCurrentUrl().contains("/checkout");
    }

    public String getPageTitle() {
        return getText(Locators.CHECKOUT_TITLE);
    }

    public void enterName(String name) {
        inputText(Locators.CHECKOUT_NAME_INPUT, name);
    }

    public void enterPhone(String phone) {
        inputText(Locators.CHECKOUT_PHONE_INPUT, phone);
    }

    public void selectCashPayment() {
        click(Locators.CHECKOUT_CASH_BTN);
    }

    public void selectNonCashPayment() {
        click(Locators.CHECKOUT_NONCASH_BTN);
    }

    public boolean isTaxDisplayed() {
        return isDisplayed(Locators.CHECKOUT_TAX_ROW);
    }

    public String getTotalAmount() {
        return getText(Locators.CHECKOUT_TOTAL);
    }

    public void clickConfirmAndPay() {
        click(Locators.CHECKOUT_CONFIRM_BTN);
    }

    public boolean isOrderSummaryDisplayed() {
        return isDisplayed(Locators.CHECKOUT_ORDER_SUMMARY);
    }
}
