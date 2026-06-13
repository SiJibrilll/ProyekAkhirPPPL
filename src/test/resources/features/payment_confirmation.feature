Feature: Konfirmasi Pembayaran Admin
  Sebagai admin restoran,
  Saya ingin dapat mengkonfirmasi pembayaran setiap meja
  Agar status pembayaran tercatat dengan benar.

  Background:
    Given Admin sudah login sebagai administrator
    And Admin membuka halaman konfirmasi pembayaran

  @smoke @payment
  Scenario: Admin berhasil mengkonfirmasi pembayaran dengan klik tombol centang
    Given Terdapat pembayaran yang belum dikonfirmasi
    When Admin mengklik tombol centang pada baris yang belum dikonfirmasi
    Then Tombol berubah menjadi hijau menandakan pembayaran terkonfirmasi

  @regression @payment
  Scenario: Admin dapat merefresh data pembayaran
    When Admin mengklik tombol Refresh Data
    Then Tabel daftar pembayaran tampil