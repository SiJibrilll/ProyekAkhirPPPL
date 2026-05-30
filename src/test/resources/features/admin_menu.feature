Feature: Admin Manajemen Menu
  Sebagai admin restoran,
  Saya ingin dapat mengelola data menu
  Agar katalog menu yang ditampilkan ke customer selalu up-to-date.

  Background:
    Given Admin sudah login ke panel admin

  @smoke @admin
  Scenario: Admin berhasil mengakses halaman manajemen menu
    When Admin membuka halaman manajemen menu
    Then Admin melihat tabel daftar menu yang ada

  @smoke @admin
  Scenario: Admin berhasil membuka form tambah menu baru
    When Admin membuka halaman manajemen menu
    And Admin mengklik tombol tambah menu
    Then Admin melihat form input menu baru

  @smoke @regression @admin
  # Equivalence Partitioning: harga valid (angka positif)
  Scenario: Admin berhasil menambah menu dengan data valid
    When Admin membuka halaman manajemen menu
    And Admin mengklik tombol tambah menu
    And Admin mengisi nama menu "Es Teh Manis Test"
    And Admin mengisi deskripsi menu "Apalah"
    And Admin memilih kategori menu "Beverages"
    And Admin mengisi harga menu "15000"
    And Admin mengunggah gambar menu "src/test/resources/gambar/esteh.jpg"
    And Admin menyimpan menu
    Then Menu baru berhasil ditambahkan ke daftar

  @smoke @regression @admin
  # BVA: harga 0 (batas bawah tidak valid — harga tidak boleh 0)
  Scenario: Admin mencoba menambah menu dengan harga 0
    When Admin membuka halaman manajemen menu
    And Admin mengklik tombol tambah menu
    And Admin mengisi nama menu "Menu Test Harga Nol"
    And Admin memilih kategori menu "Main Courses"
    And Admin mengisi deskripsi menu "Apalah"
    And Admin mengisi harga menu "0"
    And Admin mengunggah gambar menu "src/test/resources/gambar/esteh.jpg"
    And Admin menyimpan menu
    Then Sistem menampilkan validasi harga tidak valid

  @smoke @regression @admin
  # Equivalence Partitioning: nama menu kosong (invalid class)
  Scenario: Admin mencoba menambah menu tanpa nama
    When Admin membuka halaman manajemen menu
    And Admin mengklik tombol tambah menu
    And Admin mengisi nama menu ""
    And Admin memilih kategori menu "Main Courses"
    And Admin mengisi deskripsi menu "Apalah"
    And Admin mengisi harga menu "25000"
    And Admin mengunggah gambar menu "src/test/resources/gambar/esteh.jpg"
    And Admin menyimpan menu
    Then Sistem menampilkan validasi nama menu tidak boleh kosong
