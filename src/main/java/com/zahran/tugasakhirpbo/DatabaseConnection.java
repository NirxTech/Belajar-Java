package com.zahran.tugasakhirpbo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    // Laragon default port biasanya 3306
    private static final String URL = "jdbc:mysql://localhost:3306/db_wind_turbine";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Default Laragon passwordnya kosong

    public static Connection connect() {
        try {
            // Panggil drivernya (Opsional di Java baru, tapi bagus buat memastikan)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Coba hubungkan
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            // Kalau gagal, muncul popup error
            JOptionPane.showMessageDialog(null, 
                "Gagal Konek ke Database!\nPastikan Laragon sudah START.\nError: " + e.getMessage());
            return null;
        }
    }
}