package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage adalah class induk yang berisi interaksi umum
 * yang dapat digunakan oleh semua Page class.
 * Mengurangi code duplication antar page.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /** Menunggu elemen visible lalu mengembalikannya */
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Menunggu elemen clickable lalu melakukan klik */
    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Menghapus isi field lalu mengetik teks */
    public void inputText(By locator, String text) {
        WebElement el = waitForElement(locator);
        el.clear();
        el.sendKeys(text);
    }

    /** Mengambil teks dari elemen */
    public String getText(By locator) {
        return waitForElement(locator).getText();
    }

    /** Mengecek apakah elemen tampil di halaman */
    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Scroll ke elemen menggunakan JavaScript */
    public void scrollToElement(By locator) {
        WebElement el = waitForElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
    }

    /** Scroll ke bawah halaman */
    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
    }

    /** Mendapatkan URL halaman saat ini */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Menunggu URL mengandung teks tertentu */
    public void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }
}
