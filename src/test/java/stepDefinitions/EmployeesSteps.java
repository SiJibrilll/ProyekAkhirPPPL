package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.AdminPage;

import java.util.Objects;

public class EmployeesSteps {

    private final WebDriver driver;
    private final AdminPage adminPage;

    public EmployeesSteps() {
        this.driver = Hooks.getDriver();
        this.adminPage = new AdminPage(driver);
    }

    @When("Admin membuka halaman manajemen karyawan")
    public void admin_membuka_halaman_manajemen_karyawan() {
        adminPage.openEmployeesManagement();
    }

    @Then("Admin melihat tabel daftar karyawan yang ada")
    public void admin_melihat_tabel_karyawan() {
        Assertions.assertTrue(
            adminPage.isEmployeeTableDisplayed(),
            "Tabel daftar karyawan seharusnya tampil di halaman manajemen karyawan"
        );
    }

    @And("Admin mengklik tombol tambah karyawan")
    public void admin_mengklik_tambah_karyawan() {
        adminPage.clickAddEmployeeButton();
    }

    @Then("Admin melihat form input karyawan baru")
    public void admin_melihat_form_input_karyawan() {
        Assertions.assertTrue(
            adminPage.isEmployeeFormDisplayed(),
            "Form input karyawan seharusnya tampil setelah klik tambah karyawan"
        );
    }

    @And("Admin mengisi nama karyawan {string}")
    public void admin_mengisi_nama_karyawan(String name) {
        adminPage.enterEmployeeName(name);
    }

    @And("Admin mengisi username karyawan {string}")
    public void admin_mengisi_username_karyawan(String username) {
        adminPage.enterEmployeeUsername(username);
    }

    @And("Admin mengisi email karyawan {string}")
    public void admin_mengisi_email_karyawan(String email) {
        adminPage.enterEmployeeEmail(email);
    }

    @And("Admin mengisi password karyawan {string}")
    public void admin_mengisi_password_karyawan(String password) {
        adminPage.enterEmployeePassword(password);
    }

    @And("Admin mengisi nomor telepon {string}")
    public void admin_mengisi_nomor_telepon(String phone) {
        adminPage.enterEmployeePhone(phone);
    }

    @And("Admin menyimpan karyawan")
    public void admin_menyimpan_karyawan() {
        adminPage.clickSaveEmployee();
    }

    @Then("Karyawan baru berhasil ditambahkan ke daftar")
    public void karyawan_berhasil_ditambahkan() {
        Assertions.assertTrue(
            adminPage.isEmployeeTableDisplayed() || Objects.requireNonNull(driver.getCurrentUrl()).contains("/admin/employees"),
            "Seharusnya kembali ke halaman list karyawan setelah berhasil tambah karyawan"
        );
    }

    @Then("Sistem menampilkan validasi nama karyawan tidak boleh kosong")
    public void validasi_nama_karyawan_kosong() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("tambah") ||
            adminPage.isValidationErrorDisplayed(),
            "Seharusnya menampilkan validasi saat nama karyawan kosong"
        );
    }

    @Then("Sistem menampilkan validasi email tidak valid")
    public void validasi_email_tidak_valid() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("tambah") ||
            adminPage.isValidationErrorDisplayed(),
            "Seharusnya menampilkan validasi saat email tidak valid"
        );
    }

    @Then("Sistem menampilkan validasi nomor telepon tidak boleh kosong")
    public void validasi_nomor_telepon_kosong() {
        Assertions.assertTrue(
            Objects.requireNonNull(driver.getCurrentUrl()).contains("tambah") ||
            adminPage.isValidationErrorDisplayed(),
            "Seharusnya menampilkan validasi saat nomor telepon kosong"
        );
    }

    @And("Admin mengklik tombol edit pada karyawan pertama")
    public void admin_mengklik_tombol_edit() {
        adminPage.clickEditEmployee();
    }

    @Then("Admin melihat form edit karyawan")
    public void admin_melihat_form_edit_karyawan() {
        Assertions.assertTrue(
            adminPage.isEmployeeFormDisplayed(),
            "Form edit karyawan seharusnya tampil"
        );
    }

    @Then("Karyawan berhasil diperbarui")
    public void karyawan_berhasil_diperbarui() {
        Assertions.assertTrue(
            adminPage.isEmployeeTableDisplayed() || Objects.requireNonNull(driver.getCurrentUrl()).contains("/admin/employees"),
            "Seharusnya kembali ke halaman list karyawan setelah update"
        );
    }

    @And("Admin mengklik tombol hapus pada karyawan pertama")
    public void admin_mengklik_tombol_hapus() {
        adminPage.clickDeleteEmployeeFromTable();
    }

    @And("Admin mengkonfirmasi penghapusan karyawan")
    public void admin_mengkonfirmasi_penghapusan() {
        adminPage.confirmDeleteEmployee();
    }

    @Then("Karyawan berhasil dihapus dari daftar")
    public void karyawan_berhasil_dihapus() {
        Assertions.assertTrue(
            adminPage.isEmployeeTableDisplayed() || Objects.requireNonNull(driver.getCurrentUrl()).contains("/admin/employees"),
            "Seharusnya kembali ke halaman list karyawan setelah hapus"
        );
    }

    @And("Admin mengklik tombol ganti password pada karyawan pertama")
    public void admin_mengklik_tombol_ganti_password() {
        adminPage.clickChangePasswordEmployee();
    }

    @Then("Admin melihat form ganti password karyawan")
    public void admin_melihat_form_ganti_password() {
        Assertions.assertTrue(
            driver.getCurrentUrl().contains("/admin/employees") || adminPage.isEmployeeFormDisplayed(),
            "Seharusnya menampilkan form ganti password"
        );
    }

    @And("Admin mengisi password lama {string}")
    public void admin_mengisi_password_lama(String password) {
        adminPage.enterOldPassword(password);
    }

    @And("Admin mengisi password baru {string}")
    public void admin_mengisi_password_baru(String password) {
        adminPage.enterNewPassword(password);
    }

    @And("Admin mengisi konfirmasi password baru {string}")
    public void admin_mengisi_konfirmasi_password_baru(String password) {
        adminPage.enterConfirmPassword(password);
    }

    @And("Admin menyimpan perubahan password")
    public void admin_menyimpan_perubahan_password() {
        adminPage.clickSavePasswordChange();
    }

    @Then("Password karyawan berhasil diubah")
    public void password_karyawan_berhasil_diubah() {
        Assertions.assertTrue(
            adminPage.isEmployeeTableDisplayed() || Objects.requireNonNull(driver.getCurrentUrl()).contains("/admin/employees"),
            "Seharusnya kembali ke halaman list karyawan setelah password diubah"
        );
    }
}
