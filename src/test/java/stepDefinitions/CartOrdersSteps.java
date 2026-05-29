package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrdersPage;

import java.util.Objects;

public class CartOrdersSteps {

    private final WebDriver driver;
    private final HomePage homePage;
    private final OrdersPage ordersPage;
    private final CheckoutPage checkoutPage;

    public CartOrdersSteps() {
        this.driver = Hooks.getDriver();
        this.homePage = new HomePage(driver);
        this.ordersPage = new OrdersPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
    }

    @And("Customer menekan tombol Order Now di cart modal")
    public void customer_menekan_order_now() {
        homePage.clickOrderNow();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("Customer diarahkan ke halaman Orders")
    public void customer_diarahkan_ke_halaman_orders() {
        ordersPage.waitForUrlContains("/orders");
        Assertions.assertTrue(
            ordersPage.isOnOrdersPage(),
            "Seharusnya diarahkan ke halaman /orders setelah menekan Order Now"
        );
    }

    @Given("Customer membuka halaman orders")
    public void customer_membuka_halaman_orders() {
        ordersPage.open();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    @Given("Customer membuka halaman orders tanpa sesi aktif")
    public void customer_membuka_halaman_orders_tanpa_sesi() {
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("sessionStorage.clear();");
        ordersPage.open();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    @Then("Customer melihat judul halaman {string}")
    public void customer_melihat_judul_halaman(String expectedTitle) {
        String actualTitle = ordersPage.getPageTitle();
        Assertions.assertTrue(
            actualTitle.contains(expectedTitle),
            "Judul halaman seharusnya mengandung: " + expectedTitle + " (actual: " + actualTitle + ")"
        );
    }

    @Then("Customer melihat pesan belum ada pesanan")
    public void customer_melihat_pesan_belum_ada_pesanan() {
        Assertions.assertTrue(
            ordersPage.isEmptyStateDisplayed(),
            "Seharusnya tampil pesan 'Belum ada pesanan' saat tidak ada pesanan"
        );
    }

    @When("Customer mengklik tombol Add More Items")
    public void customer_mengklik_add_more_items() {
        ordersPage.clickAddMoreItems();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    @Then("Customer diarahkan kembali ke halaman menu")
    public void customer_diarahkan_kembali_ke_halaman_menu() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("resto-kita.vercel.app"),
            "Seharusnya kembali ke halaman menu utama"
        );
    }

    @When("Customer mengklik tombol Confirm and Proceed ke checkout")
    public void customer_mengklik_confirm_proceed() {
        ordersPage.clickProceedToCheckout();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    @Then("Customer diarahkan ke halaman Checkout")
    public void customer_diarahkan_ke_halaman_checkout() {
        checkoutPage.waitForUrlContains("/checkout");
        Assertions.assertTrue(
            checkoutPage.isOnCheckoutPage(),
            "Seharusnya diarahkan ke halaman /checkout"
        );
    }
}
