package com.zahran.tugasakhirpbo;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class TurbineManagementView extends JFrame {
    private JTable turbineTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;
    private int selectedId = -1; // ID Turbin dari Database

    public TurbineManagementView() {
        initComponents();
        loadDataFromDB(); // Load data saat aplikasi dibuka
    }

    private void initComponents() {
        setTitle("Manajemen Data Turbin (Operator Access)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 20", "[grow]", "[]20[]20[grow]"));
        mainPanel.setBackground(new Color(253, 251, 247));

        JLabel title = new JLabel("⚡ Manajemen Turbin Angin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(title, "wrap");

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        addButton = new JButton("➕ Tambah");
        editButton = new JButton("✏️ Edit");
        deleteButton = new JButton("🗑️ Hapus");
        
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        btnPanel.add(addButton);
        btnPanel.add(editButton);
        btnPanel.add(deleteButton);
        mainPanel.add(btnPanel, "wrap");

        // Table
        String[] cols = {"ID", "Nama Turbin", "Lokasi", "Tipe", "Status", "Daya (MW)"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        turbineTable = new JTable(tableModel);
        turbineTable.setRowHeight(30);
        
        turbineTable.getSelectionModel().addListSelectionListener(e -> {
            if (turbineTable.getSelectedRow() != -1) {
                selectedId = Integer.parseInt(tableModel.getValueAt(turbineTable.getSelectedRow(), 0).toString());
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });

        mainPanel.add(new JScrollPane(turbineTable), "grow");
        setContentPane(mainPanel);

        // Actions
        addButton.addActionListener(e -> showFormDialog(null));
        editButton.addActionListener(e -> showFormDialog(selectedId));
        deleteButton.addActionListener(e -> deleteData());
    }

    // --- DATABASE OPERATIONS ---

    private void loadDataFromDB() {
        tableModel.setRowCount(0); // Clear table
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM turbines");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama_turbin"),
                    rs.getString("lokasi"),
                    rs.getString("tipe"),
                    rs.getString("status"),
                    rs.getDouble("daya_mw")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus ID: " + selectedId + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.connect()) {
                PreparedStatement pst = conn.prepareStatement("DELETE FROM turbines WHERE id=?");
                pst.setInt(1, selectedId);
                pst.executeUpdate();
                loadDataFromDB();
                JOptionPane.showMessageDialog(this, "Data dihapus!");
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void showFormDialog(Integer idToEdit) {
        JDialog dialog = new JDialog(this, (idToEdit == null ? "Tambah" : "Edit") + " Turbin", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        
        JPanel form = new JPanel(new MigLayout("fill, insets 20, wrap 2", "[][grow]", "[]10[]10[]10[]10[]20[]"));
        
        JTextField nameField = new JTextField();
        JTextField locField = new JTextField();
        JTextField typeField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Aktif", "Mati"});
        JTextField powerField = new JTextField("0.0");

        // Jika Edit, Load Data Lama
        if (idToEdit != null) {
            try (Connection conn = DatabaseConnection.connect()) {
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM turbines WHERE id=?");
                pst.setInt(1, idToEdit);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    nameField.setText(rs.getString("nama_turbin"));
                    locField.setText(rs.getString("lokasi"));
                    typeField.setText(rs.getString("tipe"));
                    statusCombo.setSelectedItem(rs.getString("status"));
                    powerField.setText(String.valueOf(rs.getDouble("daya_mw")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        form.add(new JLabel("Nama Turbin:")); form.add(nameField, "grow");
        form.add(new JLabel("Lokasi:")); form.add(locField, "grow");
        form.add(new JLabel("Tipe:")); form.add(typeField, "grow");
        form.add(new JLabel("Status:")); form.add(statusCombo, "grow");
        form.add(new JLabel("Daya (MW):")); form.add(powerField, "grow");

        JButton saveBtn = new JButton("Simpan");
        saveBtn.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                String sql;
                if (idToEdit == null) {
                    sql = "INSERT INTO turbines (nama_turbin, lokasi, tipe, status, daya_mw) VALUES (?, ?, ?, ?, ?)";
                } else {
                    sql = "UPDATE turbines SET nama_turbin=?, lokasi=?, tipe=?, status=?, daya_mw=? WHERE id=?";
                }
                
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, nameField.getText());
                pst.setString(2, locField.getText());
                pst.setString(3, typeField.getText());
                pst.setString(4, (String) statusCombo.getSelectedItem());
                pst.setDouble(5, Double.parseDouble(powerField.getText()));
                if (idToEdit != null) pst.setInt(6, idToEdit);

                pst.executeUpdate();
                loadDataFromDB();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Berhasil disimpan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        form.add(saveBtn, "span 2, grow, gaptop 10");
        dialog.add(form);
        dialog.setVisible(true);
    }
}