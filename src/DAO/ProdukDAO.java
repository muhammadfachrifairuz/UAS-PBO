/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import classmodel.Produk;
import KoneksiKeDB.DBKoneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Meisya
 */
public class ProdukDAO {
    
    private Connection connection;
    
    public ProdukDAO() {
        this.connection = DBKoneksi.getConnection();
    }
    
    public List<Produk> getAll() {
        List<Produk> listProduk = new ArrayList<>();
        String query = "SELECT p.kode, p.nama, p.harga, p.stock, k.kategori, k.brand " +
                       "FROM produk p INNER JOIN kategori k ON p.kode_produk = k.kode " +
                       "ORDER BY p.kode";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Produk produk = new Produk();
                produk.setKode(rs.getInt("kode"));
                produk.setNama(rs.getString("nama"));
                produk.setHarga(rs.getInt("harga"));
                produk.setStock(rs.getInt("stock"));
                produk.setNamaKategori(rs.getString("kategori"));
                produk.setBrand(rs.getString("brand"));
                listProduk.add(produk);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listProduk;
    }
    
    public Produk getById(int id) {
        Produk produk = null;
        String query = "SELECT p.kode, p.kode_produk, p.nama, p.harga, p.stock, k.kategori, k.brand " +
                       "FROM produk p INNER JOIN kategori k ON p.kode_produk = k.kode " +
                       "WHERE p.kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                produk = new Produk();
                produk.setKode(rs.getInt("kode"));
                produk.setKodeProduk(rs.getInt("kode_produk"));
                produk.setNama(rs.getString("nama"));
                produk.setHarga(rs.getInt("harga"));
                produk.setStock(rs.getInt("stock"));
                produk.setNamaKategori(rs.getString("kategori"));
                produk.setBrand(rs.getString("brand"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error getting data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return produk;
    }
    
    public boolean insert(Produk produk) {
        if (produk.getStock() < 0) {
            JOptionPane.showMessageDialog(null,
                "❌ Stock tidak boleh negatif!",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (produk.getHarga() < 0) {
            JOptionPane.showMessageDialog(null,
                "❌ Harga tidak boleh negatif!",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String query = "INSERT INTO produk (kode_produk, nama, harga, stock) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, produk.getKodeProduk());
            pstmt.setString(2, produk.getNama());
            pstmt.setInt(3, produk.getHarga());
            pstmt.setInt(4, produk.getStock());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data produk berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "❌ Gagal menambahkan data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean update(Produk produk) {
        if (produk.getStock() < 0) {
            JOptionPane.showMessageDialog(null,
                "❌ Stock tidak boleh negatif!",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (produk.getHarga() < 0) {
            JOptionPane.showMessageDialog(null,
                "❌ Harga tidak boleh negatif!",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String query = "UPDATE produk SET kode_produk = ?, nama = ?, harga = ?, stock = ? WHERE kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, produk.getKodeProduk());
            pstmt.setString(2, produk.getNama());
            pstmt.setInt(3, produk.getHarga());
            pstmt.setInt(4, produk.getStock());
            pstmt.setInt(5, produk.getKode());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data produk berhasil diupdate!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "❌ Gagal mengupdate data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(int id) {
        String query = "DELETE FROM produk WHERE kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data produk berhasil dihapus!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "❌ Gagal menghapus data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }
    
    public int getCount() {
        String query = "SELECT COUNT(*) FROM produk";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}