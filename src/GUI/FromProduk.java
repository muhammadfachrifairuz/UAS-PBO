/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import classmodel.Kategori;
import classmodel.Produk;
import DAO.ProdukDAO;
import DAO.KategoriDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author Meisya
 */
public class FromProduk extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FromProduk.class.getName());
    
    private ProdukDAO produkDAO;
    private KategoriDAO kategoriDAO;
    private DefaultTableModel tableModel;
    private Produk selectedProduk = null;
    
    private JDialog inputDialog;
    private JComboBox<Kategori> cbKategori;
    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtStock;
    private JButton btnSave;
    private JButton btnCancel;
    
    /**
     * Creates new form FromProduk
     */
    public FromProduk() {
        initComponents();
        initialize();
    }
    
    private void initialize() {
        produkDAO = new ProdukDAO();
        kategoriDAO = new KategoriDAO();
        setupTableModel();
        setupTableMouseListener();
        loadData();
    }
    
    private void setupTableModel() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"Kode", "Nama Produk", "Harga", "Stock", "Kategori", "Brand"});
        tbllistproduk.setModel(tableModel);
        tbllistproduk.getColumnModel().getColumn(0).setMaxWidth(60);
        tbllistproduk.getColumnModel().getColumn(2).setMaxWidth(100);
        tbllistproduk.getColumnModel().getColumn(3).setMaxWidth(80);
    }
    
    private void setupTableMouseListener() {
        tbllistproduk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbllistproduk.getSelectedRow();
                if (selectedRow >= 0) {
                    int kode = (int) tableModel.getValueAt(selectedRow, 0);
                    selectedProduk = produkDAO.getById(kode);
                }
            }
        });
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Produk> listProduk = produkDAO.getAll();
        for (Produk p : listProduk) {
            tableModel.addRow(new Object[]{
                p.getKode(),
                p.getNama(),
                p.getHarga(),
                p.getStock(),
                p.getNamaKategori(),
                p.getBrand()
            });
        }
        selectedProduk = null;
    }
    
     private void showInputDialog(String title, Produk produk) {
        inputDialog = new JDialog(this, title, true);
        inputDialog.setSize(450, 280);
        inputDialog.setLocationRelativeTo(this);
        inputDialog.setLayout(null);
        
        // Labels
        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(30, 30, 100, 25);
        inputDialog.add(lblKategori);
        
        JLabel lblNama = new JLabel("Nama Produk:");
        lblNama.setBounds(30, 65, 100, 25);
        inputDialog.add(lblNama);
        
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(30, 100, 100, 25);
        inputDialog.add(lblHarga);
        
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(30, 135, 100, 25);
        inputDialog.add(lblStock);
        
        // ComboBox Kategori (format: Kode - Nama Kategori (Brand))
        cbKategori = new JComboBox<>();
        cbKategori.setBounds(150, 30, 250, 25);
        loadKategoriToComboBox();
        inputDialog.add(cbKategori);
        
        // Text Fields
        txtNama = new JTextField();
        txtNama.setBounds(150, 65, 250, 25);
        inputDialog.add(txtNama);
        
        txtHarga = new JTextField();
        txtHarga.setBounds(150, 100, 250, 25);
        inputDialog.add(txtHarga);
        
        txtStock = new JTextField();
        txtStock.setBounds(150, 135, 250, 25);
        inputDialog.add(txtStock);
        
        // Buttons
        btnSave = new JButton("Save");
        btnSave.setBounds(250, 190, 80, 30);
        inputDialog.add(btnSave);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(340, 190, 80, 30);
        inputDialog.add(btnCancel);
        
        // If update, fill with existing data
        if (produk != null) {
            // Set kategori yang sesuai
            for (int i = 0; i < cbKategori.getItemCount(); i++) {
                Kategori k = cbKategori.getItemAt(i);
                if (k.getKode() == produk.getKodeProduk()) {
                    cbKategori.setSelectedIndex(i);
                    break;
                }
            }
            txtNama.setText(produk.getNama());
            txtHarga.setText(String.valueOf(produk.getHarga()));
            txtStock.setText(String.valueOf(produk.getStock()));
        }
        
        // Button Actions
        btnSave.addActionListener(e -> {
            if (produk == null) {
                insertData();
            } else {
                updateData(produk.getKode());
            }
        });
        
        btnCancel.addActionListener(e -> inputDialog.dispose());
        
        inputDialog.setVisible(true);
    }
    
    private void loadKategoriToComboBox() {
        cbKategori.removeAllItems();
        List<Kategori> listKategori = kategoriDAO.getForComboBox();
        for (Kategori k : listKategori) {
            cbKategori.addItem(k);
        }
    }
    
    private void insertData() {
        Kategori selectedKategori = (Kategori) cbKategori.getSelectedItem();
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stockStr = txtStock.getText().trim();
        
        // Validasi input
        if (selectedKategori == null) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Pilih kategori!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Nama produk harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Harga harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Stock harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int harga = Integer.parseInt(hargaStr);
            int stock = Integer.parseInt(stockStr);
            
            Produk produk = new Produk();
            produk.setKodeProduk(selectedKategori.getKode());
            produk.setNama(nama);
            produk.setHarga(harga);
            produk.setStock(stock);
            
            if (produkDAO.insert(produk)) {
                inputDialog.dispose();
                loadData();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Harga dan Stock harus berupa angka!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateData(int id) {
        Kategori selectedKategori = (Kategori) cbKategori.getSelectedItem();
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stockStr = txtStock.getText().trim();
        
        // Validasi input
        if (selectedKategori == null) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Pilih kategori!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Nama produk harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Harga harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Stock harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int harga = Integer.parseInt(hargaStr);
            int stock = Integer.parseInt(stockStr);
            
            Produk produk = new Produk();
            produk.setKode(id);
            produk.setKodeProduk(selectedKategori.getKode());
            produk.setNama(nama);
            produk.setHarga(harga);
            produk.setStock(stock);
            
            if (produkDAO.update(produk)) {
                inputDialog.dispose();
                loadData();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Harga dan Stock harus berupa angka!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteData() {
        if (selectedProduk == null) {
            JOptionPane.showMessageDialog(this,
                "❌ Pilih data yang akan dihapus!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus produk:\n" +
            "Kode: " + selectedProduk.getKode() + "\n" +
            "Nama: " + selectedProduk.getNama(),
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (produkDAO.delete(selectedProduk.getKode())) {
                loadData();
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnkategori = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbllistproduk = new javax.swing.JTable();
        btnload = new javax.swing.JButton();
        btninsert = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("list produk");

        btnkategori.setText("kategori");
        btnkategori.addActionListener(this::btnkategoriActionPerformed);

        tbllistproduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "Nama Produk", "Harga", "Stock", "Kategori", "Brand"
            }
        ));
        jScrollPane1.setViewportView(tbllistproduk);

        btnload.setText("load");
        btnload.addActionListener(this::btnloadActionPerformed);

        btninsert.setText("insert");
        btninsert.addActionListener(this::btninsertActionPerformed);

        btnupdate.setText("update");
        btnupdate.addActionListener(this::btnupdateActionPerformed);

        btndelete.setText("delete");
        btndelete.addActionListener(this::btndeleteActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnload)
                        .addGap(18, 18, 18)
                        .addComponent(btninsert)
                        .addGap(18, 18, 18)
                        .addComponent(btnupdate)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnkategori)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnkategori))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnload)
                    .addComponent(btninsert)
                    .addComponent(btnupdate)
                    .addComponent(btndelete)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkategoriActionPerformed
        new FromKategori().setVisible(true);
    }//GEN-LAST:event_btnkategoriActionPerformed

    private void btnloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloadActionPerformed
        loadData();
    }//GEN-LAST:event_btnloadActionPerformed

    private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
        showInputDialog("Tambah Produk", null);
    }//GEN-LAST:event_btninsertActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        if (selectedProduk == null) {
            JOptionPane.showMessageDialog(this,
                "❌ Pilih data yang akan diupdate!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        showInputDialog("Update Produk", selectedProduk);
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        deleteData();
    }//GEN-LAST:event_btndeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FromProduk().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btninsert;
    private javax.swing.JButton btnkategori;
    private javax.swing.JButton btnload;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbllistproduk;
    // End of variables declaration//GEN-END:variables
}
