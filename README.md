# RestoKita — Selenium Cucumber BDD Test Suite

Proyek pengujian otomatis untuk aplikasi **Resto Kita** menggunakan
Selenium WebDriver, Cucumber BDD, dan Page Object Model (POM).

## URL Aplikasi
- **Frontend (Customer)**: https://resto-kita.vercel.app/
- **Admin Panel**        : https://resto-kita.vercel.app/login
- **Admin Credentials**  : `test@example.com` / `password`

## User Flow yang Diuji (6 Halaman)

```
/login (Admin)
  ↓ generate QR
/admin/tables
  ↓ customer scan URL QR
/ (Menu Catalog)
  ↓ tambah ke cart → Order Now
/orders
  ↓ Confirm & Proceed
/checkout
  ↓ Confirm & Pay
```

## Struktur Project

```
RestoKita_Test/
├── pom.xml
└── src/test/
    ├── java/
    │   ├── pages/
    │   │   ├── BasePage.java          ← Interaksi umum (click, input, wait)
    │   │   ├── Locators.java          ← Centralized Locators semua halaman
    │   │   ├── HomePage.java          ← Menu & Cart (akses via QR URL)
    │   │   ├── LoginPage.java         ← Admin Login  /login
    │   │   ├── OrdersPage.java        ← Your Orders  /orders
    │   │   ├── CheckoutPage.java      ← Checkout     /checkout
    │   │   └── AdminPage.java         ← Admin Panel + QR Generate
    │   ├── stepDefinitions/
    │   │   ├── Hooks.java             ← @Before / @After + auto screenshot
    │   │   ├── LoginSteps.java        ← Steps login admin
    │   │   ├── QRTableSteps.java      ← Steps generate QR meja
    │   │   ├── MenuSteps.java         ← Steps katalog menu & cart
    │   │   ├── CartOrdersSteps.java   ← Steps orders & navigasi checkout
    │   │   ├── CheckoutSteps.java     ← Steps form & pembayaran checkout
    │   │   ├── AdminSteps.java        ← Steps admin manajemen menu
    │   │   └── EndToEndSteps.java     ← Steps E2E flow lengkap
    │   └── runners/
    │       ├── TestRunner.java        ← @smoke  (happy path)
    │       ├── RegressionTestRunner.java ← @regression (BVA + EP)
    │       └── E2ETestRunner.java     ← @e2e (full flow)
    └── resources/
        └── features/
            ├── login.feature          ← 5 skenario (BVA + EP)
            ├── qr_table.feature       ← 6 skenario QR generate
            ├── menu.feature           ← 6 skenario katalog menu
            ├── cart_orders.feature    ← 5 skenario cart & orders
            ├── checkout.feature       ← 7 skenario (BVA + EP)
            ├── admin_menu.feature     ← 5 skenario manajemen menu
            └── end_to_end.feature     ← 3 skenario full flow
```

## Metode Test Case

| Metode                   | Digunakan pada                                  |
|--------------------------|-------------------------------------------------|
| Equivalence Partitioning | Login (valid/invalid email-password), nama kosong, harga 0 |
| Boundary Value Analysis  | Panjang password (1 char), nama (1 char), nomor WA (10-12 digit) |

## Cara Menjalankan

### Prasyarat
1. Java 17+, Maven 3.6+
2. Google Chrome (versi terbaru)
3. Koneksi internet (aplikasi sudah di-deploy di Vercel)

### Smoke Test (Happy Path)
```bash
mvn test -Dtest=runners.TestRunner
```

### Regression Test (BVA + EP)
```bash
mvn test -Dtest=runners.RegressionTestRunner
```

### End-to-End Test (Full Flow)
```bash
mvn test -Dtest=runners.E2ETestRunner
```

### Filter berdasarkan Tag
```bash
mvn test -Dcucumber.filter.tags="@login"
mvn test -Dcucumber.filter.tags="@qr"
mvn test -Dcucumber.filter.tags="@menu"
mvn test -Dcucumber.filter.tags="@checkout"
mvn test -Dcucumber.filter.tags="@admin"
mvn test -Dcucumber.filter.tags="@smoke or @regression"
```

## Laporan Otomatis (Auto-Generated Report)

Setelah test selesai, laporan tersedia di folder `target/cucumber-reports/`:

| File                        | Isi                          |
|-----------------------------|------------------------------|
| `smoke-report.html`         | Laporan smoke test (HTML)    |
| `regression-report.html`    | Laporan regression test (HTML)|
| `e2e-report.html`           | Laporan E2E test (HTML)      |
| `smoke.json`                | JSON untuk CI/CD integration |

Laporan HTML bisa langsung dibuka di browser setelah `mvn test` selesai.
