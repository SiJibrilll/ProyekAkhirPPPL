Feature: End-to-End Customer Flow (Scan QR → Menu → Cart → Orders → Checkout)
  Sebagai customer restoran,
  Saya ingin dapat memesan makanan mulai dari scan QR hingga checkout
  Agar proses pemesanan berjalan lancar dan terintegrasi.

  @smoke @e2e
  Scenario: Flow lengkap: Admin generate QR → Customer akses menu → Tambah ke cart → Pesan → Checkout
    # Step 1: Admin login dan generate QR
    Given Admin sudah login ke panel admin
    When Admin memilih nomor meja "1"
    And Admin mengisi nama pelanggan "E2E Test Customer"
    And Admin menekan tombol TAMBAH MEJA
    Then QR Code berhasil di-generate dan tampil di halaman
    And URL QR mengandung parameter token dan nomor meja

    # Step 2: Customer akses menu via URL QR
    When Customer membuka URL QR yang di-generate admin
    Then Customer melihat halaman menu dengan nomor meja yang sesuai
    And Customer melihat daftar menu tersedia

    # Step 3: Customer tambah item ke cart
    When Customer mengklik tombol tambah pada menu pertama
    Then Customer melihat floating cart button muncul

    # Step 4: Customer checkout via cart modal
    When Customer mengklik floating cart button
    Then Cart modal terbuka dengan item di dalamnya
    When Customer menekan tombol Order Now di cart modal
    Then Customer diarahkan ke halaman Orders

    # Step 5: Customer proceed ke checkout
    When Customer mengklik tombol Confirm and Proceed ke checkout
    Then Customer diarahkan ke halaman Checkout
    And Customer melihat kalkulasi pajak 10 persen

  @smoke @e2e
  Scenario: Customer mengakses menu dengan token QR yang valid
    Given Admin sudah login ke panel admin
    And Admin sudah generate QR untuk meja "2" dengan nama "Flow Test"
    When Customer membuka URL QR yang di-generate admin
    Then Customer melihat halaman menu dengan nomor meja yang sesuai
    And Navbar menampilkan nomor meja yang benar

  @regression @e2e
  Scenario: Customer mengakses halaman menu tanpa token QR
    Given Customer membuka halaman menu tanpa token QR
    Then Sistem menampilkan pesan sesi meja tidak valid
