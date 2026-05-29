Feature: Admin Login
  Sebagai admin restoran,
  Saya ingin dapat login ke panel admin
  Agar saya dapat mengelola menu dan pesanan restoran.

  Background:
    Given Admin membuka halaman login

  @smoke @login
  Scenario: Login berhasil dengan kredensial valid
    When Admin memasukkan email "admin@gmail.com" dan password "password123"
    And Admin menekan tombol Sign In
    Then Admin berhasil masuk ke halaman dashboard

  @regression @login
  Scenario: Login gagal dengan password salah
    When Admin memasukkan email "admin@gmail.com" dan password "salahpassword"
    And Admin menekan tombol Sign In
    Then Admin melihat pesan error login

  @regression @login
  Scenario: Login gagal dengan email tidak terdaftar
    When Admin memasukkan email "tidakada@gmail.com" dan password "password123"
    And Admin menekan tombol Sign In
    Then Admin melihat pesan error login

  @regression @login
  # Equivalence Partitioning: email kosong (invalid class)
  Scenario: Login gagal dengan email kosong
    When Admin memasukkan email "" dan password "password123"
    And Admin menekan tombol Sign In
    Then Form login tidak dapat disubmit

  @regression @login
  # BVA: password dengan 1 karakter (batas bawah — terlalu pendek)
  Scenario: Login gagal dengan password terlalu pendek
    When Admin memasukkan email "admin@gmail.com" dan password "a"
    And Admin menekan tombol Sign In
    Then Admin melihat pesan error login
