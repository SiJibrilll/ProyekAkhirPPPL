package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.HomePage;

public class MenuSteps {

    private final WebDriver driver;
    private final HomePage homePage;

    public MenuSteps() {
        this.driver = Hooks.getDriver();
        this.homePage = new HomePage(driver);
    }

    // ─── Given (dipakai di Background) ─────────────────────────────────

    @Given("Customer membuka URL QR yang di-generate admin")
    public void customer_membuka_url_qr_dari_background() {
        String qrUrl = QRTableSteps.lastGeneratedQRUrl;
        Assertions.assertNotNull(qrUrl, "URL QR harus tersedia dari step generate QR sebelumnya");
        homePage.openFromQRUrl(qrUrl);
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Given("Customer membuka halaman menu utama")
    public void customer_membuka_halaman_menu_utama() {
        homePage.open();
    }

    // ─── Then (verifikasi halaman) ──────────────────────────────────────

    @Then("Customer melihat hero section dengan judul restoran")
    public void customer_melihat_hero_section() {
        String title = homePage.getHeroTitle();
        Assertions.assertFalse(
            title.isEmpty(),
            "Judul hero section seharusnya tidak kosong setelah akses via QR"
        );
    }

    @Then("Customer melihat daftar menu tersedia")
    public void customer_melihat_daftar_menu_tersedia() {
        // Scroll ke catalog section dulu
        homePage.scrollDown();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}

        int count = homePage.getMenuCardCount();
        Assertions.assertTrue(
            count > 0,
            "Seharusnya ada setidaknya 1 menu card yang tampil di katalog"
        );
    }

    // ─── When (aksi pencarian) ──────────────────────────────────────────

    @When("Customer mengetik {string} pada search bar")
    public void customer_mengetik_pada_search_bar(String keyword) {
        homePage.searchMenu(keyword);
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }

    @Then("Customer melihat hasil pencarian yang relevan")
    public void customer_melihat_hasil_pencarian_relevan() {
        int count = homePage.getMenuCardCount();
        Assertions.assertTrue(
            count > 0,
            "Seharusnya ada hasil pencarian yang relevan dengan kata kunci yang dimasukkan"
        );
    }

    @Then("Customer melihat pesan menu tidak ditemukan")
    public void customer_melihat_pesan_menu_tidak_ditemukan() {
        boolean emptyState = homePage.isMenuEmptyStateDisplayed();
        int count = homePage.getMenuCardCount();
        Assertions.assertTrue(
            emptyState || count == 0,
            "Seharusnya tidak ada menu atau tampil pesan tidak ditemukan untuk kata kunci tidak valid"
        );
    }

    // ─── When (aksi cart) ──────────────────────────────────────────────

    @When("Customer mengklik tombol tambah pada menu pertama")
    public void customer_mengklik_tambah_menu_pertama() {
        homePage.scrollDown();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        homePage.addFirstMenuToCart();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    @Then("Customer melihat floating cart button muncul")
    public void customer_melihat_floating_cart_button() {
        Assertions.assertTrue(
            homePage.isCartFloatingBtnDisplayed(),
            "Floating cart button seharusnya muncul setelah item ditambahkan ke cart"
        );
    }

    @And("Customer mengklik floating cart button")
    public void customer_mengklik_floating_cart_button() {
        homePage.openCartModal();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    @Then("Cart modal terbuka dengan item di dalamnya")
    public void cart_modal_terbuka_dengan_item() {
        Assertions.assertTrue(
            homePage.isCartModalOpen(),
            "Cart modal seharusnya terbuka"
        );
        Assertions.assertFalse(
            homePage.isCartEmpty(),
            "Cart seharusnya tidak kosong setelah item ditambahkan"
        );
    }

    @And("Customer menutup cart modal")
    public void customer_menutup_cart_modal() {
        homePage.closeCartModal();
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    @Then("Cart modal tertutup dan customer tetap di halaman menu")
    public void cart_modal_tertutup() {
        Assertions.assertFalse(
            homePage.isCartModalOpen(),
            "Cart modal seharusnya sudah tertutup"
        );
        Assertions.assertTrue(
            driver.getCurrentUrl().contains("resto-kita.vercel.app"),
            "Customer seharusnya masih di domain Resto Kita"
        );
    }

    @Then("Sistem memproses pencarian tanpa error")
    public void sistem_memproses_pencarian_tanpa_error() {
        Assertions.assertFalse(
            driver.getCurrentUrl().contains("error"),
            "Halaman seharusnya tidak menampilkan error saat pencarian"
        );
    }

    // ─── Given (orders page) ───────────────────────────────────────────

    @Given("Customer membuka halaman orders")
    public void customer_membuka_halaman_orders_dari_menu() {
        homePage.clickOrdersIcon();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
}
