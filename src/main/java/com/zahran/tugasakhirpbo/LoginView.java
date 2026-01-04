package com.zahran.tugasakhirpbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {
    // Color Palette
    private static final Color CREAM_WHITE = new Color(253, 251, 247);
    private static final Color SOFT_TEAL = new Color(38, 166, 154);
    private static final Color WARM_ORANGE = new Color(255, 138, 101);
    private static final Color DARK_SLATE = new Color(55, 71, 79);
    private static final Color LIGHT_TEAL = new Color(178, 223, 219);

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;

    public LoginView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Wind Turbine Monitoring System - Login");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM_WHITE);

        // Left Panel - Welcome Section dengan Gradient
        JPanel leftPanel = createWelcomePanel();
        
        // Right Panel - Form Login
        JPanel rightPanel = createFormPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createWelcomePanel() {
        GradientPanel welcomePanel = new GradientPanel();
        welcomePanel.setPreferredSize(new Dimension(450, 600));
        welcomePanel.setLayout(new MigLayout("fill, insets 40", "[center]", "[center]"));

        // Icon/Logo
        JLabel iconLabel = new JLabel("🌀");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        iconLabel.setForeground(Color.WHITE);

        // Welcome Text
        JLabel welcomeLabel = new JLabel("Wind Turbine");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        welcomeLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Monitoring System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        subtitleLabel.setForeground(new Color(255, 255, 255, 230));

        JLabel descLabel = new JLabel("Memantau energi bersih untuk masa depan");
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        descLabel.setForeground(new Color(255, 255, 255, 200));

        welcomePanel.add(iconLabel, "wrap, gapbottom 20");
        welcomePanel.add(welcomeLabel, "wrap, gapbottom 5");
        welcomePanel.add(subtitleLabel, "wrap, gapbottom 20");
        welcomePanel.add(descLabel, "wrap");

        return welcomePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new MigLayout("fill, insets 60", "[grow]", "[]30[]15[]15[]15[]30[]"));
        formPanel.setBackground(CREAM_WHITE);

        // Title
        JLabel titleLabel = new JLabel("Selamat Datang");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(DARK_SLATE);

        JLabel subtitleLabel = new JLabel("Silakan login untuk melanjutkan");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(120, 120, 120));

        // Username Field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(DARK_SLATE);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(350, 45));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Password Field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(DARK_SLATE);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(350, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Role ComboBox
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(DARK_SLATE);

        String[] roles = {"Operator", "Pemilik"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleComboBox.setPreferredSize(new Dimension(350, 45));

        // Login Button (Rounded)
        loginButton = new RoundedButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(350, 50));
        loginButton.setBackground(SOFT_TEAL);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(32, 140, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(SOFT_TEAL);
            }
        });

        // Action Listener
        loginButton.addActionListener(e -> handleLogin());

        // Add components to panel
        formPanel.add(titleLabel, "wrap");
        formPanel.add(subtitleLabel, "wrap, gapbottom 40");
        
        formPanel.add(userLabel, "wrap");
        formPanel.add(usernameField, "wrap, width 350!");
        
        formPanel.add(passLabel, "wrap");
        formPanel.add(passwordField, "wrap, width 350!");
        
        formPanel.add(roleLabel, "wrap");
        formPanel.add(roleComboBox, "wrap, width 350!");
        
        formPanel.add(loginButton, "wrap, width 350!, gaptop 20");

        return formPanel;
    }

    private void handleLogin() {
        String inputUsername = usernameField.getText();
        String inputPassword = new String(passwordField.getPassword());
        String inputRole = (String) roleComboBox.getSelectedItem();

        // Validasi input kosong
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username & Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- PROSES CEK KE DATABASE LARAGON ---
        try {
            // 1. Buka koneksi
            Connection conn = DatabaseConnection.connect();
            if (conn != null) {
                // 2. Siapkan mantra SQL (Query)
                String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
                
                // 3. Masukkan data input ke dalam mantra SQL (biar aman dari hack)
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, inputUsername);
                pst.setString(2, inputPassword);
                pst.setString(3, inputRole);

                // 4. Jalankan pengecekan
                ResultSet rs = pst.executeQuery();

                // 5. Cek hasilnya
                if (rs.next()) {
                    // KETEMU! Login Sukses
                    JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat Datang, " + inputUsername);
                    
                    // Buka Dashboard dan kirim data user
                    new DashboardView(inputUsername, inputRole).setVisible(true);
                    this.dispose(); // Tutup jendela login
                } else {
                    // TIDAK KETEMU
                    JOptionPane.showMessageDialog(this, "Username, Password, atau Role SALAH!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                }
                
                // Tutup koneksi
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Sistem: " + e.getMessage());
        }
    }

    // Custom JPanel dengan Gradient Background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int width = getWidth();
            int height = getHeight();
            
            GradientPaint gp = new GradientPaint(0, 0, SOFT_TEAL, 0, height, LIGHT_TEAL);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    // Custom JButton dengan Rounded Corners
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
