package pages;

import org.openqa.selenium.By;

/**
 * Locators adalah class terpusat yang menyimpan semua locator elemen UI.
 * Apabila ada perubahan locator (misal: id berubah karena update FE),
 * cukup ubah di sini — tidak perlu ubah di banyak tempat.
 * <p>
 * Aplikasi : Restoran Kita (https://resto-kita.vercel.app)
 */
public class Locators {

    // ================================================================
    // HOME / MENU PAGE
    // ================================================================

    // Navbar
    public static final By NAVBAR_LOGO = By.cssSelector("nav a[href='/']");
    public static final By NAVBAR_TABLE_NUMBER = By.cssSelector("nav p.font-bold.text-stone-800");
    public static final By NAVBAR_ORDER_ICON = By.cssSelector("nav button[aria-label='Lihat pesanan']");

    // Hero Section
    public static final By HERO_TITLE = By.cssSelector("section.hero-background h1");
    public static final By HERO_EXPLORE_BTN = By.xpath("//button[contains(text(),'Explore Menu')]");

    // Search & Category Filter
    public static final By SEARCH_INPUT = By.cssSelector("input[type='text'], input[placeholder]");
    public static final By CATEGORY_FILTER_BTN = By.cssSelector("button[class*='category'], button[class*='filter']");

    // Menu Cards
    public static final By MENU_CARD_LIST = By.cssSelector("article.bg-white.rounded-2xl");
    public static final By FIRST_MENU_ADD_BTN = By.cssSelector("article.bg-white button[aria-label]");
    public static final By MENU_ITEM_NAME = By.cssSelector("article.bg-white h3");
    public static final By MENU_ITEM_PRICE = By.cssSelector("article.bg-white span.font-bold");
    public static final By MENU_EMPTY_STATE = By.xpath("//*[contains(text(),'Menu tidak ditemukan') or contains(text(),'tidak ditemukan')]");

    // Floating Cart Button
    public static final By CART_FLOATING_BTN = By.cssSelector("div.fixed.bottom-8 button");

    // Cart Modal
    public static final By CART_MODAL = By.cssSelector("div[role='dialog']");
    public static final By CART_MODAL_TITLE = By.cssSelector("div[role='dialog'] h2");
    public static final By CART_MODAL_CLOSE = By.cssSelector("button[aria-label='Tutup keranjang']");
    public static final By CART_SUBTOTAL = By.xpath("//span[text()='Subtotal']/following-sibling::span");
    public static final By CART_ORDER_NOW_BTN = By.xpath("//button[.//span[contains(text(),'Order Now')] or contains(text(),'Order Now')]");
    public static final By CART_EMPTY_MSG = By.xpath("//*[contains(text(),'Keranjang masih kosong')]");

    // ================================================================
    // ADMIN LOGIN PAGE  (/login)
    // ================================================================
    public static final By ADMIN_EMAIL_INPUT = By.cssSelector("input[type='email']");
    public static final By ADMIN_PASSWORD_INPUT = By.cssSelector("input[type='password']");
    public static final By ADMIN_SUBMIT_BTN = By.cssSelector("button[type='submit']");
    public static final By LOGIN_ERROR_MSG = By.cssSelector(".text-red-500, [class*='error']");

    // ================================================================
    // ADMIN SHELL (sidebar & layout)
    // ================================================================
    public static final By ADMIN_SIDEBAR_MENU = By.cssSelector("aside nav a");
    public static final By ADMIN_DASHBOARD_TITLE = By.cssSelector("main h1");

    // ================================================================
    // ADMIN - QR TABLE PAGE  (/admin/tables)
    // ================================================================
    // Toolbar: pilih meja, nama pelanggan, tombol tambah
    public static final By QR_TABLE_SELECT = By.xpath("//label[contains(text(), 'PILIH NOMOR MEJA')]/following-sibling::select");
    public static final By QR_CUSTOMER_NAME_INPUT = By.xpath("//label[contains(text(), 'NAMA PELANGGAN')]/following-sibling::input");
    public static final By QR_TAMBAH_MEJA_BTN = By.xpath("//button[contains(normalize-space(), 'TAMBAH MEJA')]");

    // QR Card hasil generate
    public static final By QR_CARD = By.cssSelector("div.bg-white.rounded-2xl img[alt='QR']");
    // Locator untuk Link
    public static final By QR_URL_LINK = By.cssSelector("a[href*='token=']");
    // Locator untuk Tombol (Diperkuat)
    public static final By QR_REGENERATE_BTN = By.xpath("//button[contains(normalize-space(), 'REGENERATE')]");
    public static final By QR_DOWNLOAD_BTN = By.xpath("//button[.//svg[contains(@class, 'lucide-download')]]");
    // Locator Empty State
    public static final By QR_EMPTY_STATE = By.xpath("//*[contains(text(),'Belum ada meja aktif')]");

    // ================================================================
    // ADMIN - MENU MANAGEMENT  (/admin/menu)
    // ================================================================
    public static final By ADMIN_MENU_TABLE = By.cssSelector("table");
    public static final By ADMIN_ADD_MENU_BTN = By.cssSelector("a[href*='/tambah'], button[class*='tambah']");
    public static final By ADMIN_MENU_NAME_INPUT = By.xpath("//label[text()='Nama Menu']/following-sibling::input");
    public static final By ADMIN_DESKRIPSI_NAME_INPUT = By.xpath("//label[text()='Deskripsi']/following-sibling::textarea");
    public static final By ADMIN_KATEGORI_SELECT = By.xpath("//label[text()='Kategori']/following-sibling::select");
    public static final By ADMIN_MENU_PRICE_INPUT = By.xpath("//label[text()='Harga (IDR)']/following-sibling::div//input");
    public static final By ADMIN_MENU_IMAGE_INPUT = By.id("img-upload");
    public static final By ADMIN_MENU_SAVE_BTN = By.xpath("//button[contains(., 'Simpan Menu')]");

    // ================================================================
    // ORDERS PAGE  (/orders)
    // ================================================================
    public static final By ORDERS_PAGE_TITLE = By.cssSelector("main h1");
    // Sesuaikan kembali card list ini jika ternyata ringkasan total ikut terambil
    public static final By ORDERS_CARD_LIST = By.cssSelector("main div.flex.flex-col.gap-4 > div.rounded-3xl");
    public static final By ORDERS_TOTAL_LABEL = By.xpath("//div[contains(@class,'font-black')]//span[text()='Total']");
    public static final By ORDERS_ADD_MORE_BTN = By.xpath("//button[contains(.,'Add More Items')]");
    // FIX: Menghapus kondisi 'Confirm' yang bikin error
    public static final By ORDERS_PROCEED_BTN = By.xpath("//button[contains(.,'Proceed Checkout')]");
    public static final By ORDERS_EMPTY_MSG = By.xpath("//p[contains(.,'Belum ada pesanan')]");

    // ================================================================
    // CHECKOUT PAGE  (/checkout)
    // ================================================================
    public static final By CHECKOUT_TITLE = By.cssSelector("main h1");
    // FIX: Menggunakan relasi dari Label karena input tidak punya name/placeholder
    public static final By CHECKOUT_NAME_INPUT = By.xpath("//label[contains(.,'Nama Lengkap')]/following-sibling::input");
    public static final By CHECKOUT_TABLE_INPUT = By.xpath("//label[contains(.,'Nomor Meja')]/following-sibling::input"); // Contoh jika ingin ambil Nomor Meja

    public static final By CHECKOUT_CASH_BTN = By.xpath("//button[contains(.,'Cash') or contains(.,'Tunai')]");
    public static final By CHECKOUT_NONCASH_BTN = By.xpath("//button[contains(.,'Non Cash') or contains(.,'Non-Cash') or contains(.,'non_cash')]");
    public static final By CHECKOUT_TAX_ROW = By.xpath("//*[contains(text(),'Tax') or contains(text(),'Pajak')]");
    public static final By CHECKOUT_TOTAL = By.xpath("//div[contains(@class,'font-black')]//span[last()]");
    public static final By CHECKOUT_CONFIRM_BTN = By.xpath("//button[contains(.,'Confirm & Pay')]");
    public static final By CHECKOUT_ORDER_SUMMARY = By.xpath("//h2[contains(.,'Order Summary')]");
    public static final By CHECKOUT_MIDTRANS_INFO = By.xpath("//*[contains(text(),'Midtrans')]");

    // ================================================================
    // ADMIN - EMPLOYEES MANAGEMENT  (/admin/employees)
    // ================================================================
    public static final By ADMIN_EMPLOYEES_TABLE = By.tagName("table");
    public static final By ADMIN_ADD_EMPLOYEE_BTN = By.xpath("//button[contains(., 'Tambah Karyawan Baru')]");

    // Form inputs untuk halaman tambah/edit karyawan
    public static final By ADMIN_EMPLOYEE_NAME_INPUT = By.xpath("//label[contains(text(), 'NAMA LENGKAP')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_USERNAME_INPUT = By.xpath("//label[contains(text(), 'USERNAME')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_EMAIL_INPUT = By.xpath("//label[contains(text(), 'EMAIL')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_PASSWORD_INPUT = By.xpath("//label[contains(text(), 'PASSWORD')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_PHONE_INPUT = By.xpath("//label[contains(text(), 'NOMOR TELEPON')]/following-sibling::div//input");

    // Buttons untuk form
    public static final By ADMIN_EMPLOYEE_SAVE_BTN = By.xpath("//button[contains(text(), 'Simpan Karyawan') or contains(text(), 'Perbarui Data')]");
    public static final By ADMIN_EMPLOYEE_CANCEL_BTN = By.xpath("//button[contains(text(), 'Batal')]");
    public static final By ADMIN_EMPLOYEE_DELETE_BTN = By.xpath("//button[contains(text(), 'Hapus Karyawan')]");

    // Action buttons dalam tabel
    public static final By ADMIN_EMPLOYEE_CHANGE_PASSWORD_BTN = By.xpath("//button[@title='Ganti Password']");
    public static final By ADMIN_EMPLOYEE_EDIT_BTN = By.xpath("//button[@title='Edit']");
    public static final By ADMIN_EMPLOYEE_DELETE_FROM_TABLE_BTN = By.xpath("//button[@title='Hapus']");
    public static final By ADMIN_EMPLOYEE_CONFIRM_DELETE_BTN = By.xpath("//button[contains(text(), 'Hapus') and not(@title)]");

    // Change password form
    public static final By ADMIN_EMPLOYEE_OLD_PASSWORD_INPUT = By.xpath("//label[contains(text(), 'PASSWORD LAMA')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_NEW_PASSWORD_INPUT = By.xpath("//label[contains(text(), 'PASSWORD BARU')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_CONFIRM_PASSWORD_INPUT = By.xpath("//label[contains(text(), 'KONFIRMASI PASSWORD BARU')]/following-sibling::input");
    public static final By ADMIN_EMPLOYEE_SAVE_PASSWORD_BTN = By.xpath("//button[contains(text(), 'Simpan Perubahan')]");

    // Validation errors
    public static final By EMPLOYEE_VALIDATION_ERROR = By.cssSelector(".text-red-500, .text-red-600, [class*='error']");

    // ================================================================
    // ADMIN - PAYMENT CONFIRMATION PAGE  (/admin/payment-confirmation)
    // ================================================================
    public static final By PAYMENT_PAGE_TITLE = By.cssSelector("main h1, h1");
    public static final By PAYMENT_TABLE_ROWS = By.cssSelector("table tbody tr, div[class*='table'] div[class*='row']");

    // Tombol centang yang belum dikonfirmasi (abu-abu / outline)
    public static final By PAYMENT_UNCONFIRMED_BTN = By.xpath("//button[.//svg[contains(@class,'lucide-circle-check') or contains(@class,'check')] and not(contains(@class,'text-green')) and not(contains(@class,'bg-green'))]");

    // Semua tombol aksi di kolom AKSI
    public static final By PAYMENT_ALL_ACTION_BTNS = By.xpath("//table//button | //div[contains(@class,'table')]//button[contains(@class,'action')]");

    // Tombol yang sudah berstatus hijau (konfirmasi berhasil)
    public static final By PAYMENT_CONFIRMED_BTN = By.xpath("//button[contains(@class,'text-green') or contains(@class,'bg-green') or .//svg[contains(@class,'text-green')]]");

    // Baris pertama di tabel pembayaran
    public static final By PAYMENT_FIRST_ROW_ACTION_BTN = By.xpath("(//table//tr[td])[1]//button | (//tbody//tr)[1]//button");

    // Pesan sukses
    public static final By PAYMENT_SUCCESS_TOAST = By.xpath("//*[contains(@class,'toast') or contains(@class,'alert') or contains(@class,'success') or contains(@class,'Toastify')]");

    // Refresh data button
    public static final By PAYMENT_REFRESH_BTN = By.xpath("//button[contains(normalize-space(),'Refresh Data') or contains(normalize-space(),'Refresh')]");
}
