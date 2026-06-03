Feature: Checkout Pesanan
  Sebagai customer restoran,
  Saya ingin dapat melakukan checkout dengan memilih metode pembayaran
  Agar pesanan saya dapat diproses dan dibayar.

  Background:
    Given Customer membuka halaman checkout

  @smoke @checkout
  Scenario: Halaman checkout menampilkan data nama dan nomor meja yang benar dari admin
    Then Form nama harus otomatis terisi dengan "Paijo"
    And Form nomor meja harus otomatis terisi dengan "6"
    And Tombol Confirm and Pay dapat diklik

  @smoke @checkout
  Scenario: Customer berhasil memilih metode pembayaran Cash
    When Customer memilih metode pembayaran Cash
    Then Pilihan Cash terpilih dengan benar