/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import classmodel.Kategori;
import DAO.KategoriDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;import java.util.List;

/**
 *
 * @author Meisya
 */
public class FromKategori extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FromKategori.class.getName());
    
    private KategoriDAO kategoriDAO;
    private DefaultTableModel tableModel;
    private Kategori selectedKategori = null;
    
    private JDialog inputDialog;
    private JTextField txtKategori;
    private JTextField txtBrand;
    private JButton btnSave;
    private JButton btnCancel;
    
    /**
     * Creates new form FromKategori
     */
    public FromKategori() {
        initComponents();
        initialize();
    }
    
    // =============================================
    // METHOD INITIALIZE
    // =============================================
    private void initialize() {
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
        tableModel.setColumnIdentifiers(new Object[]{"Kode", "Nama Kategori", "Brand"});
        jTable1.setModel(tableModel);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
    }
    
    private void setupTableMouseListener() {
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow >= 0) {
                    int kode = (int) tableModel.getValueAt(selectedRow, 0);
                    selectedKategori = kategoriDAO.getById(kode);
                }
            }
        });
    }
    
    // =============================================
    // LOAD DATA
    // =============================================
    private void loadData() {
        tableModel.setRowCount(0);
        List<Kategori> listKategori = kategoriDAO.getAll();
        for (Kategori k : listKategori) {
            tableModel.addRow(new Object[]{
                k.getKode(),
                k.getKategori(),
                k.getBrand()
            });
        }
        selectedKategori = null;
    }
    
     private void showInputDialog(String title, Kategori kategori) {
        inputDialog = new JDialog(this, title, true);
        inputDialog.setSize(400, 200);
        inputDialog.setLocationRelativeTo(this);
        inputDialog.setLayout(null);
        
        JLabel lblKategori = new JLabel("Nama Kategori:");
        lblKategori.setBounds(30, 30, 100, 25);
        inputDialog.add(lblKategori);
        
        JLabel lblBrand = new JLabel("Brand:");
        lblBrand.setBounds(30, 65, 100, 25);
        inputDialog.add(lblBrand);
        
        txtKategori = new JTextField();
        txtKategori.setBounds(150, 30, 200, 25);
        inputDialog.add(txtKategori);
        
        txtBrand = new JTextField();
        txtBrand.setBounds(150, 65, 200, 25);
        inputDialog.add(txtBrand);
        
        btnSave = new JButton("Save");
        btnSave.setBounds(200, 110, 80, 30);
        inputDialog.add(btnSave);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(290, 110, 80, 30);
        inputDialog.add(btnCancel);
        
        if (kategori != null) {
            txtKategori.setText(kategori.getKategori());
            txtBrand.setText(kategori.getBrand());
        }
        
        btnSave.addActionListener(e -> {
            if (kategori == null) {
                insertData();
            } else {
                updateData(kategori.getKode());
            }
        });
        
        btnCancel.addActionListener(e -> inputDialog.dispose());
        inputDialog.setVisible(true);
    }
    
    // =============================================
    // INSEERT DATA
    // =============================================
    private void insertData() {
        String namaKategori = txtKategori.getText().trim();
        String brand = txtBrand.getText().trim();
        
        if (namaKategori.isEmpty() || brand.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Nama Kategori dan Brand harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Kategori kategori = new Kategori(namaKategori, brand);
        if (kategoriDAO.insert(kategori)) {
            inputDialog.dispose();
            loadData();
        }
    }
    
    // =============================================
    // UPDATE DATA
    // =============================================
     private void updateData(int id) {
        String namaKategori = txtKategori.getText().trim();
        String brand = txtBrand.getText().trim();
        
        if (namaKategori.isEmpty() || brand.isEmpty()) {
            JOptionPane.showMessageDialog(inputDialog,
                "❌ Nama Kategori dan Brand harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Kategori kategori = new Kategori(id, namaKategori, brand);
        if (kategoriDAO.update(kategori)) {
            inputDialog.dispose();
            loadData();
        }
    }
    
    // =============================================
    // DELETE DATA
    // =============================================
    private void deleteData() {
        if (selectedKategori == null) {
            JOptionPane.showMessageDialog(this,
                "❌ Pilih data yang akan dihapus!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus kategori:\n" +
            "Kode: " + selectedKategori.getKode() + "\n" +
            "Kategori: " + selectedKategori.getKategori() + "\n" +
            "Brand: " + selectedKategori.getBrand(),
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (kategoriDAO.delete(selectedKategori.getKode())) {
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

        btnLoad = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FromKategori");

        btnLoad.setText("load");
        btnLoad.addActionListener(this::btnLoadActionPerformed);

        btnInsert.setText("insert");
        btnInsert.addActionListener(this::btnInsertActionPerformed);

        btnUpdate.setText("update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode", "Nama Kategori", "Brand"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLoad)
                        .addGap(18, 18, 18)
                        .addComponent(btnInsert)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoad)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        loadData();
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
         showInputDialog("Tambah Kategori", null);
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedKategori == null) {
            JOptionPane.showMessageDialog(this,
                "❌ Pilih data yang akan diupdate!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        showInputDialog("Update Kategori", selectedKategori);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FromKategori().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
