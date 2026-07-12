/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classmodel;

/**
 *
 * @author Meisya
 */

/*
 * =============================================
 * Produk.java
 * Model class untuk tabel produk
 * =============================================
 */

public class Produk {
    private int kode;
    private int kodeProduk;
    private String nama;
    private int harga;
    private int stock;
    
    private String namaKategori;
    private String brand;
    
    public Produk() {
    }
    
    public Produk(int kode, int kodeProduk, String nama, int harga, int stock) {
        this.kode = kode;
        this.kodeProduk = kodeProduk;
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
    }
    
    public Produk(int kode, String nama, int harga, int stock, String namaKategori, String brand) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
        this.namaKategori = namaKategori;
        this.brand = brand;
    }
    
    public int getKode() {
        return kode;
    }
    
    public void setKode(int kode) {
        this.kode = kode;
    }
    
    public int getKodeProduk() {
        return kodeProduk;
    }
    
    public void setKodeProduk(int kodeProduk) {
        this.kodeProduk = kodeProduk;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public int getHarga() {
        return harga;
    }
    
    public void setHarga(int harga) {
        this.harga = harga;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public String getNamaKategori() {
        return namaKategori;
    }
    
    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
}