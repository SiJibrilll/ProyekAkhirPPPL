package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * OrdersPage merepresentasikan halaman "Your Orders" (/orders).
 * Menampilkan daftar item yang sudah di-order oleh customer.
 */
public class OrdersPage extends BasePage {

    public static final String ORDERS_URL = "https://resto-kita.vercel.app/orders";

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ORDERS_URL);
    }

    public String getPageTitle() {
        return getText(Locators.ORDERS_PAGE_TITLE);
    }

    public boolean isOnOrdersPage() {
        return getCurrentUrl().contains("/orders");
    }

    public int getOrderCardCount() {
        List<WebElement> cards = driver.findElements(Locators.ORDERS_CARD_LIST);
        return cards.size();
    }

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(Locators.ORDERS_EMPTY_MSG);
    }

    public void clickAddMoreItems() {
        click(Locators.ORDERS_ADD_MORE_BTN);
    }

    public void clickProceedToCheckout() {
        click(Locators.ORDERS_PROCEED_BTN);
    }

    public String getGrandTotal() {
        return getText(Locators.ORDERS_TOTAL_LABEL);
    }
}
