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
 * Kategori.java
 * Model class untuk tabel kategori
 * =============================================
 */

public class Kategori {
    private int kode;
    private String kategori;
    private String brand;
    
    public Kategori() {
    }
    
    public Kategori(int kode, String kategori, String brand) {
        this.kode = kode;
        this.kategori = kategori;
        this.brand = brand;
    }
    
    public Kategori(String kategori, String brand) {
        this.kategori = kategori;
        this.brand = brand;
    }
    
    public int getKode() {
        return kode;
    }
    
    public void setKode(int kode) {
        this.kode = kode;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    @Override
    public String toString() {
        return kode + " - " + kategori + " (" + brand + ")";
    }
}