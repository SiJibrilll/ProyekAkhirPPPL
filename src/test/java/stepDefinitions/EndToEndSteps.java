package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrdersPage;

/**
 * EndToEndSteps menggabungkan semua step dari admin QR generate
 * hingga customer checkout dalam satu flow.
 */
public class EndToEndSteps {

    private final WebDriver driver;
    private final AdminPage adminPage;
    private final HomePage homePage;
    private final OrdersPage ordersPage;
    private final CheckoutPage checkoutPage;

    public EndToEndSteps() {
        this.driver = Hooks.getDriver();
        this.adminPage = new AdminPage(driver);
        this.homePage = new HomePage(driver);
        this.ordersPage = new OrdersPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
    }

    // ─── Customer akses via URL QR ──────────────────────────────────────

    /**
     * Customer membuka URL QR yang sudah di-generate oleh admin.
     * URL diambil dari QRTableSteps.lastGeneratedQRUrl (shared state).
     */
//    @When("Customer membuka URL QR yang di-generate admin")
//    public void customer_membuka_url_qr() {
//        String qrUrl = QRTableSteps.lastGeneratedQRUrl;
//        Assertions.assertNotNull(qrUrl, "URL QR harus sudah di-generate oleh admin terlebih dahulu");
//
//        // Buka URL di tab yang sama (atau bisa di tab baru untuk simulasi device berbeda)
//        homePage.openFromQRUrl(qrUrl);
//
//        // Tunggu halaman menu load
//        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
//    }

    @Then("Customer melihat halaman menu dengan nomor meja yang sesuai")
    public void customer_melihat_halaman_menu_dengan_nomor_meja() {
        // Verifikasi URL sudah redirect ke "/" (token dibersihkan dari URL oleh Next.js)
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(
            currentUrl.contains("resto-kita.vercel.app"),
            "Customer seharusnya berada di domain Resto Kita"
        );

        // Verifikasi navbar menampilkan informasi meja
        Assertions.assertTrue(
            homePage.isDisplayed(pages.Locators.NAVBAR_TABLE_NUMBER),
            "Navbar seharusnya menampilkan nomor meja"
        );
    }

    @And("Navbar menampilkan nomor meja yang benar")
    public void navbar_menampilkan_nomor_meja() {
        String tableText = homePage.getTableNumberFromNavbar();
        Assertions.assertFalse(
            tableText.isEmpty(),
            "Nomor meja di navbar seharusnya tidak kosong setelah akses via QR URL"
        );
    }

    // ─── Orders → Checkout navigation ──────────────────────────────────
//
//    @Then("Customer diarahkan ke halaman Checkout")
//    public void customer_diarahkan_ke_checkout() {
//        checkoutPage.waitForUrlContains("/checkout");
//        Assertions.assertTrue(
//            checkoutPage.isOnCheckoutPage(),
//            "Seharusnya diarahkan ke halaman checkout"
//        );
//    }

    // ─── Tanpa sesi QR (error state) ───────────────────────────────────

    @Given("Customer membuka halaman menu tanpa token QR")
    public void customer_membuka_tanpa_token() {
        // Buka langsung tanpa parameter token
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("sessionStorage.clear();");
        homePage.open();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("Sistem menampilkan pesan sesi meja tidak valid")
    public void sistem_menampilkan_sesi_tidak_valid() {
        // Aplikasi menampilkan error state "Sesi Meja Tidak Valid"
        boolean hasSessionError = homePage.isDisplayed(
            org.openqa.selenium.By.xpath(
                "//*[contains(text(),'Sesi Meja Tidak Valid') or contains(text(),'sesi') or contains(text(),'TableSession')]"
            )
        );
        // Jika error tidak tampil, bisa juga redirect atau halaman kosong tanpa menu
        boolean noMenuItems = homePage.getMenuCardCount() == 0;

        Assertions.assertTrue(
            hasSessionError || noMenuItems,
            "Seharusnya tampil error sesi tidak valid atau tidak ada menu ketika tanpa token"
        );
    }
}
