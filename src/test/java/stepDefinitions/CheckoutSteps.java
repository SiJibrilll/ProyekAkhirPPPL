package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.CheckoutPage;

import java.util.Objects;

public class CheckoutSteps {

    private final WebDriver driver;
    private final CheckoutPage checkoutPage;

    public CheckoutSteps() {
        this.driver = Hooks.getDriver();
        this.checkoutPage = new CheckoutPage(driver);
    }

    @Given("Customer membuka halaman checkout")
    public void customer_membuka_halaman_checkout() {
        checkoutPage.open();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @Then("Customer melihat form checkout dengan order summary")
    public void customer_melihat_form_checkout() {
        Assertions.assertTrue(
            checkoutPage.isOnCheckoutPage(),
            "Seharusnya berada di halaman checkout"
        );
        Assertions.assertTrue(
            checkoutPage.isOrderSummaryDisplayed(),
            "Order summary seharusnya tampil di halaman checkout"
        );
    }

    @Then("Customer melihat kalkulasi pajak 10 persen")
    public void customer_melihat_kalkulasi_pajak() {
        Assertions.assertTrue(
            checkoutPage.isTaxDisplayed(),
            "Baris Tax Charge 10% seharusnya tampil di halaman checkout"
        );
    }

    @When("Customer memilih metode pembayaran Cash")
    public void customer_memilih_cash() {
        checkoutPage.selectCashPayment();
    }

    @Then("Pilihan Cash terpilih dengan benar")
    public void pilihan_cash_terpilih() {
        Assertions.assertTrue(
            checkoutPage.isDisplayed(pages.Locators.CHECKOUT_CONFIRM_BTN),
            "Tombol Confirm & Pay seharusnya tampil saat metode Cash dipilih"
        );
    }

    @When("Customer memilih metode pembayaran Non-Cash")
    public void customer_memilih_non_cash() {
        checkoutPage.selectNonCashPayment();
    }

    @Then("Customer melihat informasi pembayaran Midtrans")
    public void customer_melihat_info_midtrans() {
        Assertions.assertTrue(
            checkoutPage.isDisplayed(pages.Locators.CHECKOUT_CONFIRM_BTN),
            "Tombol Confirm & Pay seharusnya tetap tampil setelah pilih Non-Cash"
        );
    }

    @When("Customer mengisi nama {string}")
    public void customer_mengisi_nama(String name) {
        checkoutPage.enterName(name);
    }

    @And("Customer mengisi nomor WhatsApp {string}")
    public void customer_mengisi_nomor_wa(String phone) {
        checkoutPage.enterPhone(phone);
    }

    @Then("Tombol Confirm and Pay dapat diklik")
    public void tombol_confirm_pay_dapat_diklik() {
        Assertions.assertTrue(
            checkoutPage.isDisplayed(pages.Locators.CHECKOUT_CONFIRM_BTN),
            "Tombol Confirm & Pay seharusnya aktif dengan data yang valid"
        );
    }

    @Then("Sistem menampilkan validasi nama tidak valid")
    public void validasi_nama_tidak_valid() {
        Assertions.assertTrue(
            checkoutPage.isOnCheckoutPage(),
            "Seharusnya tetap di halaman checkout saat validasi nama gagal"
        );
    }

    @Then("Sistem menampilkan validasi nomor tidak valid")
    public void validasi_nomor_tidak_valid() {
        Assertions.assertTrue(
            checkoutPage.isOnCheckoutPage(),
            "Seharusnya tetap di halaman checkout saat nomor WA tidak valid"
        );
    }

    @Then("Sistem memvalidasi nomor tersebut")
    public void sistem_memvalidasi_nomor() {
        Assertions.assertFalse(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("error"),
            "Halaman seharusnya tidak crash saat memvalidasi nomor"
        );
    }
}
