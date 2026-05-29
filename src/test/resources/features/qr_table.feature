Feature: Generate QR Table oleh Admin
  Sebagai admin restoran,
  Saya ingin dapat men-generate QR Code untuk meja pelanggan
  Agar pelanggan dapat mengakses menu melalui scan QR.

  Background:
    Given Admin sudah login ke panel admin
    And Admin membuka halaman Generate QR Table

  @smoke @qr
  Scenario: Admin berhasil melihat halaman Generate QR Table
    Then Admin melihat form pilih nomor meja dan input nama pelanggan

  @smoke @qr
  Scenario: Admin berhasil generate QR untuk meja yang belum aktif
    When Admin memilih nomor meja "1"
    And Admin mengisi nama pelanggan "Budi Santoso"
    And Admin menekan tombol TAMBAH MEJA
    Then QR Code berhasil di-generate dan tampil di halaman

  @smoke @qr
  Scenario: URL QR yang di-generate mengandung token sesi yang valid
    When Admin memilih nomor meja "2"
    And Admin mengisi nama pelanggan "Siti Rahayu"
    And Admin menekan tombol TAMBAH MEJA
    Then URL QR mengandung parameter token dan nomor meja

  @regression @qr
  # Equivalence Partitioning: nama pelanggan kosong (invalid class)
  Scenario: Admin mencoba generate QR tanpa mengisi nama pelanggan
    When Admin memilih nomor meja "3"
    And Admin mengisi nama pelanggan ""
    And Admin menekan tombol TAMBAH MEJA
    Then Sistem menampilkan peringatan nama pelanggan harus diisi

  @regression @qr
  # Equivalence Partitioning: meja belum dipilih (invalid class)
  Scenario: Admin mencoba generate QR tanpa memilih nomor meja
    When Admin tidak memilih nomor meja
    And Admin mengisi nama pelanggan "Test Customer"
    And Admin menekan tombol TAMBAH MEJA
    Then Sistem menampilkan peringatan pilih nomor meja

  @regression @qr
  Scenario: Admin dapat melakukan regenerate QR untuk meja yang sudah aktif
    When Admin memilih nomor meja "1"
    And Admin mengisi nama pelanggan "Customer Baru"
    And Admin menekan tombol TAMBAH MEJA
    Then QR Code berhasil di-generate dan tampil di halaman
    When Admin menekan tombol REGENERATE pada QR card meja tersebut
    Then QR Code berhasil di-generate ulang dengan token baru
