Feature: Admin Manajemen Karyawan
  Sebagai admin restoran,
  Saya ingin dapat mengelola data karyawan
  Agar data karyawan restoran selalu up-to-date dan terorganisir.

  Background:
    Given Admin sudah login ke panel admin

  @smoke @admin @employees
  Scenario: Admin berhasil mengakses halaman manajemen karyawan
    When Admin membuka halaman manajemen karyawan
    Then Admin melihat tabel daftar karyawan yang ada

  @smoke @admin @employees
  Scenario: Admin berhasil membuka form tambah karyawan baru
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol tambah karyawan
    Then Admin melihat form input karyawan baru

  @smoke @regression @admin @employees
  # Equivalence Partitioning: data karyawan valid
  Scenario: Admin berhasil menambah karyawan dengan data valid
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol tambah karyawan
    And Admin mengisi nama karyawan "Budi Santoso"
    And Admin mengisi username karyawan "budi.santoso"
    And Admin mengisi email karyawan "budi.santoso@example.com"
    And Admin mengisi password karyawan "BudiPassword123"
    And Admin mengisi nomor telepon "812 3456 7890"
    And Admin menyimpan karyawan
    Then Karyawan baru berhasil ditambahkan ke daftar

  @smoke @regression @admin @employees
  # Equivalence Partitioning: nama karyawan kosong (invalid class)
  Scenario: Admin mencoba menambah karyawan tanpa nama
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol tambah karyawan
    And Admin mengisi nama karyawan ""
    And Admin mengisi email karyawan "test@example.com"
    And Admin mengisi username karyawan "testuser"
    And Admin mengisi password karyawan "Test123Pass"
    And Admin mengisi nomor telepon "812 3456 7890"
    And Admin menyimpan karyawan
    Then Sistem menampilkan validasi nama karyawan tidak boleh kosong

  @smoke @regression @admin @employees
  # Equivalence Partitioning: format email tidak valid
  Scenario: Admin mencoba menambah karyawan dengan email tidak valid
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol tambah karyawan
    And Admin mengisi nama karyawan "Ahmad Pratama"
    And Admin mengisi username karyawan "ahmad.pratama"
    And Admin mengisi email karyawan "email-tidak-valid"
    And Admin mengisi password karyawan "Ahmad123Pass"
    And Admin mengisi nomor telepon "812 3456 7890"
    And Admin menyimpan karyawan
    Then Sistem menampilkan validasi email tidak valid

  @smoke @regression @admin @employees
  # Equivalence Partitioning: nomor telepon kosong (invalid class)
  Scenario: Admin mencoba menambah karyawan tanpa nomor telepon
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol tambah karyawan
    And Admin mengisi nama karyawan "Siti Rahayu"
    And Admin mengisi username karyawan "siti.rahayu"
    And Admin mengisi email karyawan "siti.rahayu@example.com"
    And Admin mengisi password karyawan "Siti123Pass"
    And Admin mengisi nomor telepon ""
    And Admin menyimpan karyawan
    Then Sistem menampilkan validasi nomor telepon tidak boleh kosong

  @smoke @regression @admin @employees
  Scenario: Admin berhasil mengedit data karyawan
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol edit pada karyawan pertama
    Then Admin melihat form edit karyawan
    When Admin mengisi nama karyawan "Budi Santoso Updated"
    And Admin menyimpan karyawan
    Then Karyawan berhasil diperbarui

  @smoke @regression @admin @employees
  Scenario: Admin berhasil menghapus karyawan
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol hapus pada karyawan pertama
    And Admin mengkonfirmasi penghapusan karyawan
    Then Karyawan berhasil dihapus dari daftar

  @smoke @admin @employees
  Scenario: Admin dapat mengganti password karyawan
    When Admin membuka halaman manajemen karyawan
    And Admin mengklik tombol ganti password pada karyawan pertama
    Then Admin melihat form ganti password karyawan
    When Admin mengisi password lama "password"
    And Admin mengisi password baru "PasswordBaru123"
    And Admin mengisi konfirmasi password baru "PasswordBaru123"
    And Admin menyimpan perubahan password
    Then Password karyawan berhasil diubah


