package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Hooks berisi @Before dan @After yang dijalankan otomatis
 * sebelum dan sesudah setiap scenario oleh Cucumber.
 *
 * Tanggung jawab utama:
 * - Setup ChromeDriver sebelum scenario
 * - Screenshot otomatis jika scenario gagal
 * - Tutup browser setelah scenario selesai
 */
public class Hooks {

    private static WebDriver driver;

    @Before
    public void setUp() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            // Uncomment untuk headless mode (CI/CD pipeline)
            // options.addArguments("--headless=new");
            options.addArguments("--window-size=1280,800");
            options.addArguments("--disable-notifications");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // Screenshot otomatis saat scenario gagal (nilai tambahan untuk bug report)
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png",
                "FAILED - " + scenario.getName());
        }

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Menyediakan instance WebDriver yang sama
     * untuk seluruh Step Definition class dalam satu scenario.
     */
    public static WebDriver getDriver() {
        return driver;
    }
}
