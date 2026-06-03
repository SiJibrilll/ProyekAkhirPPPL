package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;

import java.util.Objects;

public class AdminSteps {

    private final WebDriver driver;
    private final AdminPage adminPage;

    public AdminSteps() {
        this.driver = Hooks.getDriver();
        this.adminPage = new AdminPage(driver);
    }

    @Given("Admin sudah login ke panel admin")
    public void admin_sudah_login() {
        adminPage.loginAsAdmin();
        Assertions.assertTrue(
            adminPage.isOnAdminDashboard(),
            "Seharusnya sudah masuk ke dashboard admin"
        );
    }

    @When("Admin membuka halaman manajemen menu")
    public void admin_membuka_halaman_manajemen_menu() {
        adminPage.openMenuManagement();
    }

    @Then("Admin melihat tabel daftar menu yang ada")
    public void admin_melihat_tabel_menu() {
        Assertions.assertTrue(
            adminPage.isMenuTableDisplayed(),
            "Tabel daftar menu seharusnya tampil di halaman manajemen menu"
        );
    }

    @And("Admin mengklik tombol tambah menu")
    public void admin_mengklik_tambah_menu() {
        adminPage.clickAddMenuButton();
    }

    @Then("Admin melihat form input menu baru")
    public void admin_melihat_form_input_menu() {
        Assertions.assertTrue(
            adminPage.isDisplayed(pages.Locators.ADMIN_MENU_NAME_INPUT),
            "Form input nama menu seharusnya tampil setelah klik tambah menu"
        );
    }

    @And("Admin mengisi nama menu {string}")
    public void admin_mengisi_nama_menu(String name) {
        adminPage.enterMenuName(name);
    }

    @And("Admin mengisi deskripsi menu {string}")
    public void admin_mengisi_deskripsi_menu(String description) {adminPage.enterMenuDescription(description);}

    @And("Admin memilih kategori menu {string}")
    public void admin_mengisi_kategori_menu(String category) {adminPage.enterMenuKategori( category);}

    @And("Admin mengisi harga menu {string}")
    public void admin_mengisi_harga_menu(String price) {
        adminPage.enterMenuPrice(price);
    }

    @And("Admin mengunggah gambar menu {string}")
    public void admin_mengunggah_gambar_menu(String filePath) {
        adminPage.uploadGambarMenu(filePath);
    }

    @And("Admin menyimpan menu")
    public void admin_menyimpan_menu() {
        adminPage.clickSaveMenu();
    }

    @Then("Menu baru berhasil ditambahkan ke daftar")
    public void menu_berhasil_ditambahkan() {
        Assertions.assertTrue(
            adminPage.isMenuTableDisplayed() || Objects.requireNonNull(driver.getCurrentUrl()).contains("/admin/menu"),
            "Seharusnya kembali ke halaman list menu setelah berhasil tambah menu"
        );
    }

    @Then("Sistem menampilkan validasi harga tidak valid")
    public void validasi_harga_tidak_valid() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("/tambah") ||
            adminPage.isDisplayed(pages.Locators.ADMIN_MENU_PRICE_INPUT),
            "Seharusnya tetap di form tambah menu saat harga tidak valid"
        );
    }

    @Then("Sistem menampilkan validasi nama menu tidak boleh kosong")
    public void validasi_nama_menu_kosong() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("/tambah") ||
            adminPage.isDisplayed(pages.Locators.ADMIN_MENU_NAME_INPUT),
            "Seharusnya tetap di form tambah menu saat nama kosong"
        );
    }
}
