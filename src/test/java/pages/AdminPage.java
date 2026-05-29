package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * AdminPage merepresentasikan halaman-halaman pada panel admin Resto Kita.
 * Mencakup login, dashboard, generate QR table, dan manajemen menu.
 */
public class AdminPage extends BasePage {

    public static final String BASE_URL        = "https://resto-kita.vercel.app";
    public static final String ADMIN_URL        = BASE_URL + "/admin";
    public static final String ADMIN_LOGIN_URL  = BASE_URL + "/login";
    public static final String ADMIN_MENU_URL   = BASE_URL + "/admin/menu";
    public static final String ADMIN_TABLES_URL = BASE_URL + "/admin/tables";

    // Kredensial admin
    public static final String ADMIN_EMAIL    = "test@example.com";
    public static final String ADMIN_PASSWORD = "password";

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    // ─── Navigation ────────────────────────────────────────────────────

    public void openLogin() {
        driver.get(ADMIN_LOGIN_URL);
    }

    public void openDashboard() {
        driver.get(ADMIN_URL);
    }

    public void openMenuManagement() {
        driver.get(ADMIN_MENU_URL);
    }

    public void openTablesPage() {
        driver.get(ADMIN_TABLES_URL);
    }

    // ─── Login ─────────────────────────────────────────────────────────

    public void loginAsAdmin() {
        openLogin();
        inputText(Locators.ADMIN_EMAIL_INPUT, ADMIN_EMAIL);
        inputText(Locators.ADMIN_PASSWORD_INPUT, ADMIN_PASSWORD);
        click(Locators.ADMIN_SUBMIT_BTN);
        waitForUrlContains("/admin");
    }

    public boolean isOnAdminDashboard() {
        return getCurrentUrl().contains("/admin");
    }

    public boolean isAdminSidebarDisplayed() {
        return isDisplayed(Locators.ADMIN_SIDEBAR_MENU);
    }

    public String getAdminPageTitle() {
        return getText(Locators.ADMIN_DASHBOARD_TITLE);
    }

    // ─── QR Table Generation ───────────────────────────────────────────

    /**
     * Memilih nomor meja dari dropdown di halaman Generate QR Table.
     * Dropdown menggunakan <select> dengan option berupa "Meja X".
     */
    public void selectTableNumber(String tableNumber) {
        WebElement select = waitForElement(Locators.QR_TABLE_SELECT);
        new Select(select).selectByVisibleText("Meja " + tableNumber);
    }

    /** Mengisi nama pelanggan pada form generate QR */
    public void enterCustomerName(String name) {
        inputText(Locators.QR_CUSTOMER_NAME_INPUT, name);
    }

    /** Klik tombol TAMBAH MEJA untuk generate QR */
    public void clickTambahMeja() {
        click(Locators.QR_TAMBAH_MEJA_BTN);
    }

    /** Mengecek apakah QR card tampil setelah generate */
    public boolean isQRCardDisplayed() {
        return isDisplayed(Locators.QR_CARD);
    }

    /**
     * Mengambil URL QR yang di-generate untuk meja tertentu.
     * URL ini berisi token sesi dan digunakan customer untuk akses menu.
     */
    public String getGeneratedQRUrl() {
        WebElement link = waitForElement(Locators.QR_URL_LINK);
        return link.getAttribute("href");
    }

    /**
     * Melakukan full flow generate QR:
     * pilih meja → isi nama pelanggan → klik TAMBAH MEJA → tunggu QR tampil.
     * Mengembalikan URL QR yang siap diakses customer.
     */
    public String generateQRAndGetUrl(String tableNumber, String customerName) {
        openTablesPage();
        selectTableNumber(tableNumber);
        enterCustomerName(customerName);
        clickTambahMeja();
        // Tunggu QR card muncul (render async)
        waitForElement(Locators.QR_URL_LINK);
        return getGeneratedQRUrl();
    }

    // ─── Menu Management ───────────────────────────────────────────────

    public void clickAddMenuButton() {
        click(Locators.ADMIN_ADD_MENU_BTN);
    }

    public void enterMenuName(String name) {
        inputText(Locators.ADMIN_MENU_NAME_INPUT, name);
    }

    public void enterMenuPrice(String price) {
        inputText(Locators.ADMIN_MENU_PRICE_INPUT, price);
    }

    public void clickSaveMenu() {
        click(Locators.ADMIN_MENU_SAVE_BTN);
    }

    public boolean isMenuTableDisplayed() {
        return isDisplayed(Locators.ADMIN_MENU_TABLE);
    }
}
