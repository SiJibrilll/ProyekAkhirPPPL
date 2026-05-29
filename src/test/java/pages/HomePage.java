package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * HomePage merepresentasikan halaman utama Resto Kita (katalog menu).
 * Customer mengakses halaman ini melalui URL QR yang di-generate admin,
 * yang mengandung token sesi dan nomor meja.
 *
 * URL format: https://resto-kita.vercel.app/?token=XXX&table=Y&name=Z
 */
public class HomePage extends BasePage {

    public static final String BASE_URL = "https://resto-kita.vercel.app";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Membuka halaman menu melalui URL QR yang sudah di-generate admin.
     * Ini adalah cara customer masuk — bukan langsung ke "/".
     *
     * @param qrUrl URL lengkap dari QR card di halaman admin/tables
     */
    public void openFromQRUrl(String qrUrl) {
        driver.get(qrUrl);
    }

    /**
     * Membuka halaman menu dengan parameter manual (simulasi scan QR).
     * Sama seperti membuka link QR — token, tableNumber, dan name
     * akan disimpan ke sessionStorage oleh aplikasi.
     */
    public void openWithSession(String token, String tableNumber, String customerName) {
        String url = BASE_URL + "/?token=" + token
                   + "&table=" + tableNumber
                   + "&name=" + java.net.URLEncoder.encode(customerName, java.nio.charset.StandardCharsets.UTF_8);
        driver.get(url);
    }

    /** Membuka halaman utama tanpa sesi (tanpa token) */
    public void open() {
        driver.get(BASE_URL + "/");
    }

    // ─── Hero ──────────────────────────────────────────────────────────

    public String getHeroTitle() {
        return getText(Locators.HERO_TITLE);
    }

    public void clickExploreMenu() {
        scrollDown();
        click(Locators.HERO_EXPLORE_BTN);
    }

    // ─── Table Info ────────────────────────────────────────────────────

    /** Mengambil nomor meja yang tampil di navbar */
    public String getTableNumberFromNavbar() {
        return getText(Locators.NAVBAR_TABLE_NUMBER);
    }

    // ─── Menu Catalog ──────────────────────────────────────────────────

    public void searchMenu(String keyword) {
        inputText(Locators.SEARCH_INPUT, keyword);
    }

    public int getMenuCardCount() {
        List<WebElement> cards = driver.findElements(Locators.MENU_CARD_LIST);
        return cards.size();
    }

    public boolean isMenuEmptyStateDisplayed() {
        return isDisplayed(Locators.MENU_EMPTY_STATE);
    }

    /** Klik tombol tambah ke cart pada menu card pertama */
    public void addFirstMenuToCart() {
        List<WebElement> addBtns = driver.findElements(Locators.FIRST_MENU_ADD_BTN);
        if (!addBtns.isEmpty()) {
            addBtns.get(0).click();
        }
    }

    // ─── Cart ──────────────────────────────────────────────────────────

    /** Klik floating cart button (muncul setelah ada item di cart) */
    public void openCartModal() {
        click(Locators.CART_FLOATING_BTN);
    }

    public boolean isCartFloatingBtnDisplayed() {
        return isDisplayed(Locators.CART_FLOATING_BTN);
    }

    public boolean isCartModalOpen() {
        return isDisplayed(Locators.CART_MODAL);
    }

    public void closeCartModal() {
        click(Locators.CART_MODAL_CLOSE);
    }

    public boolean isCartEmpty() {
        return isDisplayed(Locators.CART_EMPTY_MSG);
    }

    /** Klik tombol "Order Now" di dalam cart modal untuk lanjut ke Orders */
    public void clickOrderNow() {
        click(Locators.CART_ORDER_NOW_BTN);
    }

    // ─── Navbar ────────────────────────────────────────────────────────

    /** Klik icon Orders di navbar untuk ke halaman /orders */
    public void clickOrdersIcon() {
        click(Locators.NAVBAR_ORDER_ICON);
    }
}
