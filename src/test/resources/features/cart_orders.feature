Feature: Keranjang dan Order List
  Sebagai customer restoran,
  Saya ingin dapat mengelola keranjang dan melihat daftar pesanan
  Agar saya dapat memastikan pesanan sebelum checkout.

  Background:
    Given Admin sudah login ke panel admin
    And Admin sudah generate QR untuk meja "1" dengan nama "Menu Test"
    And Customer membuka URL QR yang di-generate admin

  @smoke @cart
  Scenario: Customer berhasil melihat halaman Order List setelah memesan
    When Customer mengklik tombol tambah pada menu pertama
    And Customer mengklik floating cart button
    And Customer menekan tombol Order Now di cart modal
    Then Customer diarahkan ke halaman Orders

  @smoke @orders
  Scenario: Halaman Orders menampilkan judul yang benar
    Given Customer membuka halaman orders
    Then Customer melihat judul halaman "Your Orders"

  @smoke @regression @cart
  Scenario: Customer dapat menutup cart modal tanpa memesan
    When Customer mengklik tombol tambah pada menu pertama
    And Customer mengklik floating cart button
    And Customer menutup cart modal
    Then Cart modal tertutup dan customer tetap di halaman menu

    @smoke @regression @orders
  Scenario: Customer dapat kembali ke menu dari halaman Orders
    Given Customer membuka halaman orders
    When Customer mengklik tombol Add More Items
    Then Customer diarahkan kembali ke halaman menu

  @smoke @orders
  Scenario: Customer dapat lanjut ke checkout dari halaman Orders
    Given Customer membuka halaman orders
    When Customer mengklik tombol Confirm and Proceed ke checkout
    Then Customer diarahkan ke halaman Checkout
