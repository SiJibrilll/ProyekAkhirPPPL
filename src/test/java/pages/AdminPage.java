package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * AdminPage merepresentasikan halaman-halaman pada panel admin Resto Kita.
 * Mencakup login, dashboard, generate QR table, dan manajemen menu.
 */
public class AdminPage extends BasePage {

    public static final String BASE_URL = "https://resto-kita.vercel.app";
    public static final String ADMIN_URL = BASE_URL + "/admin";
    public static final String ADMIN_LOGIN_URL = BASE_URL + "/login";
    public static final String ADMIN_MENU_URL = BASE_URL + "/admin/menu";
    public static final String ADMIN_TABLES_URL = BASE_URL + "/admin/tables";
    public static final String ADMIN_EMPLOYEES_URL = BASE_URL + "/admin/employees";

    // Kredensial admin
    public static final String ADMIN_EMAIL = "test@example.com";
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
    /**
     * Memilih nomor meja dari dropdown di halaman Generate QR Table.
     * Menggunakan selectByValue karena teks di UI memiliki karakter newline (enter).
     */
    public void selectTableNumber(String tableNumber) {
        // 1. Cari elemen select menggunakan locator yang sudah diperbarui
        WebElement selectElement = waitForElement(Locators.QR_TABLE_SELECT);

        // 2. Wajib: Tunggu sampai opsi nomor meja (misal value="4") benar-benar muncul di HTML
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                selectElement,
                By.xpath(".//option[@value='" + tableNumber + "']")
        ));

        // 3. Eksekusi
        new Select(selectElement).selectByValue(tableNumber);
    }

    /**
     * Mengisi nama pelanggan pada form generate QR
     */
    public void enterCustomerName(String name) {
        inputText(Locators.QR_CUSTOMER_NAME_INPUT, name);
    }

    /**
     * Klik tombol TAMBAH MEJA untuk generate QR
     */
    public void clickTambahMeja() {
        click(Locators.QR_TAMBAH_MEJA_BTN);
    }

    /**
     * Mengecek apakah QR card tampil setelah generate
     */
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

    public void enterMenuDescription(String description) {
        inputText(Locators.ADMIN_DESKRIPSI_NAME_INPUT, description);
    }

    public void enterMenuKategori(String kategoriText) {
        // 1. Buat Explicit Wait khusus untuk menunggu elemen aktif (maksimal tunggu 10 detik)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Tunggu sampai dropdown kategori berstatus CLICKABLE (tidak disabled)
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(Locators.ADMIN_KATEGORI_SELECT));

        // 3. Setelah aktif, baru lakukan inisiasi Select dan pilih teksnya
        org.openqa.selenium.support.ui.Select dropdown = new org.openqa.selenium.support.ui.Select(element);
        dropdown.selectByVisibleText(kategoriText);
    }

    public void enterMenuPrice(String price) {
        inputText(Locators.ADMIN_MENU_PRICE_INPUT, price);
    }

    /**
     * Mengunggah gambar dengan mengirimkan path file ke input type="file"
     */
    public void uploadGambarMenu(String relativeFilePath) {
        // Mengubah path relatif (misal: src/test/resources/image.jpg) menjadi path absolut
        File file = new File(relativeFilePath);
        String absolutePath = file.getAbsolutePath();

        // Kirim path langsung ke elemen input, TANPA perlu diklik
        driver.findElement(Locators.ADMIN_MENU_IMAGE_INPUT).sendKeys(absolutePath);
    }

    public void clickSaveMenu() {
        click(Locators.ADMIN_MENU_SAVE_BTN);
    }

    public boolean isMenuTableDisplayed() {
        return isDisplayed(Locators.ADMIN_MENU_TABLE);
    }

    // ─── Employees Management ──────────────────────────────────────────

    public void openEmployeesManagement() {
        driver.get(ADMIN_EMPLOYEES_URL);
    }

    public void clickAddEmployeeButton() {
        click(Locators.ADMIN_ADD_EMPLOYEE_BTN);
    }

    public void enterEmployeeName(String name) {
        inputText(Locators.ADMIN_EMPLOYEE_NAME_INPUT, name);
    }

    public void enterEmployeeUsername(String username) {
        inputText(Locators.ADMIN_EMPLOYEE_USERNAME_INPUT, username);
    }

    public void enterEmployeeEmail(String email) {
        inputText(Locators.ADMIN_EMPLOYEE_EMAIL_INPUT, email);
    }

    public void enterEmployeePassword(String password) {
        inputText(Locators.ADMIN_EMPLOYEE_PASSWORD_INPUT, password);
    }

    public void enterEmployeePhone(String phone) {
        inputText(Locators.ADMIN_EMPLOYEE_PHONE_INPUT, phone);
    }

    public void clickSaveEmployee() {
        click(Locators.ADMIN_EMPLOYEE_SAVE_BTN);
    }

    public void clickCancelEmployee() {
        click(Locators.ADMIN_EMPLOYEE_CANCEL_BTN);
    }

    public void clickChangePasswordEmployee() {
        click(Locators.ADMIN_EMPLOYEE_CHANGE_PASSWORD_BTN);
    }

    public void clickEditEmployee() {
        click(Locators.ADMIN_EMPLOYEE_EDIT_BTN);
    }

    public void clickDeleteEmployeeFromTable() {
        click(Locators.ADMIN_EMPLOYEE_DELETE_FROM_TABLE_BTN);
    }

    public void confirmDeleteEmployee() {
        click(Locators.ADMIN_EMPLOYEE_CONFIRM_DELETE_BTN);
    }

    public void enterOldPassword(String password) {
        inputText(Locators.ADMIN_EMPLOYEE_OLD_PASSWORD_INPUT, password);
    }

    public void enterNewPassword(String password) {
        inputText(Locators.ADMIN_EMPLOYEE_NEW_PASSWORD_INPUT, password);
    }

    public void enterConfirmPassword(String password) {
        inputText(Locators.ADMIN_EMPLOYEE_CONFIRM_PASSWORD_INPUT, password);
    }

    public void clickSavePasswordChange() {
        click(Locators.ADMIN_EMPLOYEE_SAVE_PASSWORD_BTN);
    }

    public boolean isEmployeeTableDisplayed() {
        return isDisplayed(Locators.ADMIN_EMPLOYEES_TABLE);
    }

    public boolean isEmployeeFormDisplayed() {
        return isDisplayed(Locators.ADMIN_EMPLOYEE_NAME_INPUT);
    }

    public boolean isValidationErrorDisplayed() {
        return isDisplayed(Locators.EMPLOYEE_VALIDATION_ERROR);
    }

    public String getValidationErrorMessage() {
        WebElement errorElement = driver.findElement(Locators.EMPLOYEE_VALIDATION_ERROR);
        return errorElement.getText();
    }

    // ─── Payment Confirmation ──────────────────────────────────────────

    public static final String ADMIN_PAYMENT_URL = BASE_URL + "/admin/payment-confirmation";

    public void openPaymentConfirmation() {
        driver.get(ADMIN_PAYMENT_URL);
    }

    public boolean isOnPaymentPage() {
        return getCurrentUrl().contains("payment-confirmation");
    }

    public void waitForPaymentTableToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.PAYMENT_TABLE_ROWS));
    }

    public int getPaymentRowCount() {
        return driver.findElements(Locators.PAYMENT_TABLE_ROWS).size();
    }

    private By getExactUnconfirmedButtonLocator() {
        return By.xpath("//button[@title='Konfirmasi Pembayaran']");
    }

    public boolean hasUnconfirmedPayment() {
        try {
            List<WebElement> unconfirmed = driver.findElements(getExactUnconfirmedButtonLocator());
            return !unconfirmed.isEmpty() && unconfirmed.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstUnconfirmedPaymentButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btn;
        try {
            btn = wait.until(ExpectedConditions.presenceOfElementLocated(getExactUnconfirmedButtonLocator()));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        } catch (Exception e) {
            throw new RuntimeException("Selenium gagal mendeteksi tombol dengan title 'Konfirmasi Pembayaran': " + e.getMessage());
        }
        try {
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.moveToElement(btn).click().perform();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
        handleConfirmationModal();
    }

    private void handleConfirmationModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            By backupModalXpath = By.xpath(
                    "//button[contains(., 'Ya') or contains(., 'Konfirmasi') or contains(., 'Selesai')] | " +
                            "//div[@role='button' and (contains(., 'Ya') or contains(., 'Konfirmasi'))] | " +
                            "//*[contains(@class, 'modal')]//button"
            );
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(backupModalXpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", confirmBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
            try {
                wait.until(ExpectedConditions.invisibilityOf(confirmBtn));
            } catch (Exception ignored) {}
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyelesaikan konfirmasi pada modal: " + e.getMessage());
        }
    }

    public void clickRefreshPaymentData() {
        click(Locators.PAYMENT_REFRESH_BTN);
    }
}