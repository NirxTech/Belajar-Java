package com.zahran.tugasakhirpbo;

import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StatisticsView extends JFrame {

    public StatisticsView() {
        setTitle("Laporan Statistik & Grafik (Pemilik View)");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 20", "[grow][grow]", "[][grow]"));
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("📊 Analisis Data Turbin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(title, "span 2, wrap, gapbottom 20");

        // 1. PIE CHART: Status Turbin (Aktif vs Mati)
        mainPanel.add(createStatusChart(), "grow");

        // 2. BAR CHART: Output Daya per Lokasi
        mainPanel.add(createPowerChart(), "grow");

        setContentPane(mainPanel);
    }

    private JPanel createStatusChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT status, COUNT(*) as total FROM turbines GROUP BY status");
            while (rs.next()) {
                dataset.setValue(rs.getString("status"), rs.getInt("total"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createPieChart("Persentase Status Operasional", dataset, true, true, false);
        return new ChartPanel(chart);
    }

    private JPanel createPowerChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            // Ambil data Turbin dan Dayanya
            ResultSet rs = stmt.executeQuery("SELECT nama_turbin, daya_mw FROM turbines ORDER BY daya_mw DESC LIMIT 5");
            while (rs.next()) {
                dataset.addValue(rs.getDouble("daya_mw"), "MW", rs.getString("nama_turbin"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart(
            "Top 5 Output Listrik Turbin", "Nama Turbin", "Daya (MW)", 
            dataset
        );
        return new ChartPanel(chart);
    }
}