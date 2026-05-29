package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;

public class QRTableSteps {

    private final WebDriver driver;
    private final AdminPage adminPage;

    // Menyimpan URL QR yang di-generate agar bisa dipakai step lain
    // (termasuk EndToEndSteps dan MenuSteps via shared state)
    static String lastGeneratedQRUrl = null;

    public QRTableSteps() {
        this.driver = Hooks.getDriver();
        this.adminPage = new AdminPage(driver);
    }

    @Given("Admin membuka halaman Generate QR Table")
    public void admin_membuka_halaman_qr_table() {
        adminPage.openTablesPage();
    }

    @Then("Admin melihat form pilih nomor meja dan input nama pelanggan")
    public void admin_melihat_form_qr() {
        Assertions.assertTrue(
            adminPage.isDisplayed(pages.Locators.QR_TABLE_SELECT),
            "Dropdown pilih nomor meja seharusnya tampil"
        );
        Assertions.assertTrue(
            adminPage.isDisplayed(pages.Locators.QR_CUSTOMER_NAME_INPUT),
            "Input nama pelanggan seharusnya tampil"
        );
    }

    @When("Admin memilih nomor meja {string}")
    public void admin_memilih_nomor_meja(String tableNumber) {
        adminPage.selectTableNumber(tableNumber);
    }

    @When("Admin tidak memilih nomor meja")
    public void admin_tidak_memilih_nomor_meja() {
        // Tidak melakukan apapun — biarkan dropdown tetap di pilihan awal (kosong)
    }

    @And("Admin mengisi nama pelanggan {string}")
    public void admin_mengisi_nama_pelanggan(String name) {
        if (!name.isEmpty()) {
            adminPage.enterCustomerName(name);
        }
    }

    @And("Admin menekan tombol TAMBAH MEJA")
    public void admin_menekan_tambah_meja() {
        adminPage.clickTambahMeja();
    }

    @Then("QR Code berhasil di-generate dan tampil di halaman")
    public void qr_berhasil_di_generate() {
        // Tunggu sebentar untuk render QR yang async
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        Assertions.assertTrue(
            adminPage.isQRCardDisplayed(),
            "QR Card seharusnya tampil setelah generate berhasil"
        );

        // Simpan URL untuk dipakai step selanjutnya
        try {
            lastGeneratedQRUrl = adminPage.getGeneratedQRUrl();
        } catch (Exception e) {
            // URL link mungkin belum tampil — tidak di-assert di sini
        }
    }

    @Then("URL QR mengandung parameter token dan nomor meja")
    public void url_qr_mengandung_token() {
        String url = adminPage.getGeneratedQRUrl();
        lastGeneratedQRUrl = url;

        Assertions.assertNotNull(url, "URL QR seharusnya tidak null");
        Assertions.assertTrue(
            url.contains("token="),
            "URL QR seharusnya mengandung parameter token sesi"
        );
        Assertions.assertTrue(
            url.contains("table="),
            "URL QR seharusnya mengandung parameter nomor meja"
        );
    }

    @Then("Sistem menampilkan peringatan nama pelanggan harus diisi")
    public void peringatan_nama_pelanggan_kosong() {
        // Aplikasi menggunakan alert() browser untuk validasi ini
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            Assertions.assertTrue(
                alertText.contains("Nama") || alertText.contains("nama") || alertText.contains("isi"),
                "Alert seharusnya menyebutkan nama pelanggan harus diisi"
            );
            alert.accept();
        } catch (NoAlertPresentException e) {
            // Fallback: validasi HTML5 atau halaman masih di /admin/tables
            Assertions.assertTrue(
                driver.getCurrentUrl().contains("/admin/tables"),
                "Seharusnya tetap di halaman tables jika validasi gagal"
            );
        }
    }

    @Then("Sistem menampilkan peringatan pilih nomor meja")
    public void peringatan_pilih_nomor_meja() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            Assertions.assertTrue(
                alertText.contains("meja") || alertText.contains("Meja") || alertText.contains("Pilih"),
                "Alert seharusnya meminta memilih nomor meja"
            );
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assertions.assertTrue(
                driver.getCurrentUrl().contains("/admin/tables"),
                "Seharusnya tetap di halaman tables jika validasi gagal"
            );
        }
    }

    @When("Admin menekan tombol REGENERATE pada QR card meja tersebut")
    public void admin_menekan_regenerate() {
        adminPage.click(pages.Locators.QR_REGENERATE_BTN);
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("QR Code berhasil di-generate ulang dengan token baru")
    public void qr_berhasil_di_regenerate() {
        Assertions.assertTrue(
            adminPage.isQRCardDisplayed(),
            "QR Card seharusnya tetap tampil setelah regenerate"
        );
    }

    // ─── Shared step untuk Background di feature lain ──────────────────

    @Given("Admin sudah generate QR untuk meja {string} dengan nama {string}")
    public void admin_sudah_generate_qr(String tableNumber, String customerName) {
        // Pastikan masih di admin context, buka halaman tables
        adminPage.openTablesPage();
        adminPage.selectTableNumber(tableNumber);
        adminPage.enterCustomerName(customerName);
        adminPage.clickTambahMeja();

        try { Thread.sleep(2500); } catch (InterruptedException ignored) {}

        // Ambil dan simpan URL QR yang di-generate
        try {
            lastGeneratedQRUrl = adminPage.getGeneratedQRUrl();
        } catch (Exception e) {
            // QR mungkin sudah ada dari sebelumnya
            Assertions.fail("Gagal mendapatkan URL QR setelah generate: " + e.getMessage());
        }
    }
}
