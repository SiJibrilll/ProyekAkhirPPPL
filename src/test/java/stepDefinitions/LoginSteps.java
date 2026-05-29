package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;
import pages.LoginPage;

public class LoginSteps {

    private final WebDriver driver;
    private final LoginPage loginPage;
    private final AdminPage adminPage;

    public LoginSteps() {
        this.driver = Hooks.getDriver();
        this.loginPage = new LoginPage(driver);
        this.adminPage = new AdminPage(driver);
    }

    @Given("Admin membuka halaman login")
    public void admin_membuka_halaman_login() {
        loginPage.open();
    }

    @When("Admin memasukkan email {string} dan password {string}")
    public void admin_memasukkan_email_dan_password(String email, String password) {
        if (!email.isEmpty()) loginPage.enterEmail(email);
        if (!password.isEmpty()) loginPage.enterPassword(password);
    }

    @And("Admin menekan tombol Sign In")
    public void admin_menekan_tombol_sign_in() {
        loginPage.clickSignIn();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("Admin berhasil masuk ke halaman dashboard")
    public void admin_berhasil_masuk_ke_halaman_dashboard() {
        Assertions.assertTrue(
            driver.getCurrentUrl().contains("/admin"),
            "Seharusnya diarahkan ke halaman admin dashboard setelah login berhasil"
        );
    }

    @Then("Admin melihat pesan error login")
    public void admin_melihat_pesan_error_login() {
        Assertions.assertTrue(
            loginPage.isErrorDisplayed() || driver.getCurrentUrl().contains("/login"),
            "Seharusnya muncul pesan error atau tetap di halaman login saat login gagal"
        );
    }

    @Then("Form login tidak dapat disubmit")
    public void form_login_tidak_dapat_disubmit() {
        Assertions.assertTrue(
            driver.getCurrentUrl().contains("/login"),
            "Seharusnya tetap di halaman login jika email kosong"
        );
    }
}
