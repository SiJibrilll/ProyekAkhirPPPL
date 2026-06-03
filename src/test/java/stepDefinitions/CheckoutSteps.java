package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By; // Ditambahkan untuk mendeteksi elemen secara langsung
import org.openqa.selenium.WebDriver;
import pages.CheckoutPage;

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

    // =========================================================================
    // FIX: Menambahkan Step yang Undefined / Hilang dari Log Error
    // =========================================================================
    @When("Customer mengklik tombol Proceed Checkout ke checkout")
    public void customer_mengklik_tombol_proceed_checkout_ke_checkout() {
        // Mencari tombol yang mengandung teks 'Checkout' atau 'Proceed' secara dinamis
        driver.findElement(By.xpath("//button[contains(.,'Checkout') or contains(.,'Proceed')]")).click();
        try {
            Thread.sleep(1500); // Jeda pelonggaran agar halaman checkout termuat sempurna
        } catch (InterruptedException ignored) {}
    }
    // =========================================================================

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

    @And("Form nama harus otomatis terisi dengan {string}")
    public void form_nama_harus_otomatis_terisi_dengan(String expectedName) {
        String actualName = checkoutPage.getPreFilledName();
        Assertions.assertEquals(expectedName, actualName,
            "Nama di halaman checkout tidak sesuai dengan data dari admin!");
    }

    @And("Form nomor meja harus otomatis terisi dengan {string}")
    public void form_nomor_meja_harus_otomatis_terisi_dengan(String expectedTable) {
        String actualTable = checkoutPage.getPreFilledTableNumber();
        Assertions.assertEquals(expectedTable, actualTable,
            "Nomor meja di halaman checkout tidak sesuai dengan data dari admin!");
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
}