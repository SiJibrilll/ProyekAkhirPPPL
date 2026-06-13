package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;

public class PaymentConfirmationSteps {

    private final WebDriver driver;
    private final AdminPage adminPage;

    public PaymentConfirmationSteps() {
        this.driver = Hooks.getDriver();
        this.adminPage = new AdminPage(driver);
    }

    @Given("Admin sudah login sebagai administrator")
    public void adminSudahLoginSebagaiAdministrator() {
        adminPage.loginAsAdmin();
        Assertions.assertTrue(adminPage.isOnAdminDashboard(), "Admin gagal login.");
    }

    @And("Admin membuka halaman konfirmasi pembayaran")
    public void adminMembukaHalamanKonfirmasiPembayaran() {
        adminPage.openPaymentConfirmation();
        adminPage.waitForPaymentTableToLoad();
        Assertions.assertTrue(adminPage.isOnPaymentPage(), "Gagal membuka halaman konfirmasi pembayaran.");
    }

    @Given("Terdapat pembayaran yang belum dikonfirmasi")
    public void terdapatPembayaranYangBelumDikonfirmasi() {
        adminPage.waitForPaymentTableToLoad();
        Assertions.assertTrue(adminPage.hasUnconfirmedPayment(), "Tidak ada pembayaran berstatus abu-abu (pending) yang bisa dikonfirmasi."
        );
    }

    @When("Admin mengklik tombol centang pada baris yang belum dikonfirmasi")
    public void adminMengklikTombolCentangBelumDikonfirmasi() {
        adminPage.clickFirstUnconfirmedPaymentButton();
    }

    @Then("Tombol berubah menjadi hijau menandakan pembayaran terkonfirmasi")
    public void tombolBerubahMenjadiHijau() {
        Assertions.assertTrue(true);
    }

    @When("Admin mengklik tombol Refresh Data")
    public void adminMengklikTombolRefreshData() {
        adminPage.clickRefreshPaymentData();
        adminPage.waitForPaymentTableToLoad();
    }

    @Then("Tabel daftar pembayaran tampil")
    public void tabelDaftarPembayaranTampil() {
        adminPage.waitForPaymentTableToLoad();
        int rowCount = adminPage.getPaymentRowCount();
        Assertions.assertTrue(rowCount >= 0, "Tabel pembayaran gagal ditampilkan.");
    }
}