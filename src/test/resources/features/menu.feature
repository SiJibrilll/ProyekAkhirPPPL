Feature: Katalog Menu
  Sebagai customer restoran,
  Saya ingin dapat melihat dan mencari menu setelah scan QR
  Agar saya dapat memilih menu yang sesuai keinginan.

  Background:
    Given Admin sudah login ke panel admin
    And Admin sudah generate QR untuk meja "1" dengan nama "Menu Test"
    And Customer membuka URL QR yang di-generate admin

  @smoke @menu
  Scenario: Halaman menu berhasil dimuat setelah customer scan QR
    Then Customer melihat hero section dengan judul restoran
    And Customer melihat daftar menu tersedia

  @smoke @menu
  Scenario: Customer berhasil mencari menu dengan kata kunci valid
    When Customer mengetik "Nasi" pada search bar
    Then Customer melihat hasil pencarian yang relevan

  @regression @menu
  # Equivalence Partitioning: kata kunci tidak ada di menu (invalid class)
  Scenario: Pencarian menu dengan kata kunci tidak ditemukan
    When Customer mengetik "xyzabc123" pada search bar
    Then Customer melihat pesan menu tidak ditemukan

  @smoke @menu
  Scenario: Customer berhasil menambahkan menu ke keranjang
    When Customer mengklik tombol tambah pada menu pertama
    Then Customer melihat floating cart button muncul

  @regression @menu
  Scenario: Customer membuka cart modal dan melihat item yang ditambahkan
    When Customer mengklik tombol tambah pada menu pertama
    And Customer mengklik floating cart button
    Then Cart modal terbuka dengan item di dalamnya

  @regression @menu
  # BVA: search dengan 1 karakter (batas bawah)
  Scenario Outline: Pencarian menu dengan berbagai panjang kata kunci
    When Customer mengetik "<keyword>" pada search bar
    Then Sistem memproses pencarian tanpa error

    Examples:
      | keyword |
      | N       |
      | Na      |
      | Nasi    |
