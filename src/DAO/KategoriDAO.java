/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import classmodel.Kategori;
import KoneksiKeDB.DBKoneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Meisya
 */
public class KategoriDAO {
    
    private Connection connection;
    
    public KategoriDAO() {
        this.connection = DBKoneksi.getConnection();
    }
    
    public List<Kategori> getAll() {
        List<Kategori> listKategori = new ArrayList<>();
        String query = "SELECT * FROM kategori ORDER BY kode";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Kategori kategori = new Kategori();
                kategori.setKode(rs.getInt("kode"));
                kategori.setKategori(rs.getString("kategori"));
                kategori.setBrand(rs.getString("brand"));
                listKategori.add(kategori);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listKategori;
    }
    
    public Kategori getById(int id) {
        Kategori kategori = null;
        String query = "SELECT * FROM kategori WHERE kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                kategori = new Kategori();
                kategori.setKode(rs.getInt("kode"));
                kategori.setKategori(rs.getString("kategori"));
                kategori.setBrand(rs.getString("brand"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error getting data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return kategori;
    }
    
    public boolean insert(Kategori kategori) {
        if (isDuplicate(kategori.getKategori(), kategori.getBrand())) {
            JOptionPane.showMessageDialog(null,
                "❌ Kombinasi '" + kategori.getKategori() + " - " + kategori.getBrand() + 
                "' sudah ada!\nTidak boleh ada duplikat.",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String query = "INSERT INTO kategori (kategori, brand) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kategori.getKategori());
            pstmt.setString(2, kategori.getBrand());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data kategori berhasil ditambahkan!",
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
    
    public boolean update(Kategori kategori) {
        if (isDuplicateForUpdate(kategori.getKode(), kategori.getKategori(), kategori.getBrand())) {
            JOptionPane.showMessageDialog(null,
                "❌ Kombinasi '" + kategori.getKategori() + " - " + kategori.getBrand() + 
                "' sudah ada!\nTidak boleh ada duplikat.",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String query = "UPDATE kategori SET kategori = ?, brand = ? WHERE kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kategori.getKategori());
            pstmt.setString(2, kategori.getBrand());
            pstmt.setInt(3, kategori.getKode());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data kategori berhasil diupdate!",
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
        if (hasRelatedProducts(id)) {
            JOptionPane.showMessageDialog(null,
                "❌ Kategori tidak dapat dihapus!\n" +
                "Masih ada produk yang menggunakan kategori ini.\n" +
                "Hapus produk terlebih dahulu.",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String query = "DELETE FROM kategori WHERE kode = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Data kategori berhasil dihapus!",
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
    
    public boolean isDuplicate(String kategori, String brand) {
        String query = "SELECT COUNT(*) FROM kategori WHERE kategori = ? AND brand = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kategori);
            pstmt.setString(2, brand);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isDuplicateForUpdate(int id, String kategori, String brand) {
        String query = "SELECT COUNT(*) FROM kategori WHERE kategori = ? AND brand = ? AND kode != ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kategori);
            pstmt.setString(2, brand);
            pstmt.setInt(3, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean hasRelatedProducts(int kategoriId) {
        String query = "SELECT COUNT(*) FROM produk WHERE kode_produk = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, kategoriId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Kategori> getForComboBox() {
        List<Kategori> listKategori = new ArrayList<>();
        String query = "SELECT * FROM kategori ORDER BY kategori, brand";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Kategori kategori = new Kategori();
                kategori.setKode(rs.getInt("kode"));
                kategori.setKategori(rs.getString("kategori"));
                kategori.setBrand(rs.getString("brand"));
                listKategori.add(kategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKategori;
    }
    
    public int getCount() {
        String query = "SELECT COUNT(*) FROM kategori";
        
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