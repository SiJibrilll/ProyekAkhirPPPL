Feature: Checkout Pesanan
  Sebagai customer restoran,
  Saya ingin dapat melakukan checkout dengan memilih metode pembayaran
  Agar pesanan saya dapat diproses dan dibayar.

  Background:
    Given Customer membuka halaman checkout

  @smoke @checkout
  Scenario: Halaman checkout berhasil dimuat dengan form yang lengkap
    Then Customer melihat form checkout dengan order summary
    And Customer melihat kalkulasi pajak 10 persen

  @smoke @checkout
  Scenario: Customer berhasil memilih metode pembayaran Cash
    When Customer memilih metode pembayaran Cash
    Then Pilihan Cash terpilih dengan benar

  @smoke @checkout
  Scenario: Customer berhasil memilih metode pembayaran Non-Cash
    When Customer memilih metode pembayaran Non-Cash
    Then Customer melihat informasi pembayaran Midtrans

  @smoke @regression @checkout
  # Equivalence Partitioning: nama valid (alphabetic)
  Scenario: Customer mengisi form checkout dengan data valid
    When Customer mengisi nama "Budi Santoso"
    And Customer mengisi nomor WhatsApp "081234567890"
    And Customer memilih metode pembayaran Cash
    Then Tombol Confirm and Pay dapat diklik

  @smoke @regression @checkout
  # BVA: nama dengan 1 karakter (batas bawah — terlalu pendek)
  Scenario: Customer mengisi nama dengan hanya 1 karakter
    When Customer mengisi nama "A"
    And Customer memilih metode pembayaran Cash
    Then Sistem menampilkan validasi nama tidak valid

  @smoke @regression @checkout
  # Equivalence Partitioning: nomor WA invalid (non-numeric)
  Scenario: Customer mengisi nomor WhatsApp dengan karakter non-angka
    When Customer mengisi nama "Budi"
    And Customer mengisi nomor WhatsApp "abcdefghij"
    And Customer memilih metode pembayaran Cash
    Then Sistem menampilkan validasi nomor tidak valid

   @smoke @regression @checkout
  # BVA: nomor WA dengan 10 digit (batas bawah valid — minimal 10)
  Scenario Outline: Checkout dengan variasi panjang nomor WhatsApp
    When Customer mengisi nama "Budi"
    And Customer mengisi nomor WhatsApp "<nomor>"
    Then Sistem memvalidasi nomor tersebut

    Examples:
      | nomor        |
      | 0812345678   |
      | 08123456789  |
      | 081234567890 |
