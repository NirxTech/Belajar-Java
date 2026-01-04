/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zahran.tugasakhirpbo;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set FlatLaf Look and Feel
        try {
            FlatLightLaf.setup();
            
            // Kustomisasi warna FlatLaf
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.arc", 20);
            UIManager.put("TextComponent.arc", 20);
            UIManager.put("Button.background", new Color(38, 166, 154)); // Soft Teal
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.hoverBackground", new Color(32, 140, 130));
            UIManager.put("Button.pressedBackground", new Color(28, 120, 110));
            UIManager.put("Panel.background", new Color(253, 251, 247)); // Cream White
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("PasswordField.background", Color.WHITE);
            UIManager.put("ComboBox.background", Color.WHITE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Jalankan aplikasi di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LandingPage landingPage = new LandingPage();
            landingPage.setVisible(true);
        });
    }
}

