package com.zahran.tugasakhirpbo;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class DashboardView extends JFrame {
    // Color Palette
    private static final Color CREAM_WHITE = new Color(253, 251, 247);
    private static final Color SIDEBAR_BG = new Color(248, 246, 242); // Sedikit lebih terang
    private static final Color SOFT_TEAL = new Color(38, 166, 154);
    private static final Color DARK_SLATE = new Color(55, 71, 79);
    private static final Color LIGHT_GRAY = new Color(150, 150, 150);
    private static final Color SUCCESS_GREEN = new Color(76, 175, 80);
    private static final Color SKY_BLUE = new Color(66, 165, 245);
    private static final Color WARM_ORANGE = new Color(255, 138, 101);

    private String userName;
    private String userRole;

    public DashboardView(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        initComponents();
    }

    private void initComponents() {
        setTitle("Wind Turbine Monitoring System - Dashboard");
        setSize(1300, 850); // Sedikit lebih tinggi
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM_WHITE);

        mainPanel.add(createSidebar(), BorderLayout.WEST);
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContent(), BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // ============ SIDEBAR ============
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(270, 800));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230))); // Garis pemisah tipis

        // 1. Logo Area
        JPanel logoPanel = new JPanel(new MigLayout("insets 30 25 30 20", "[]10[]"));
        logoPanel.setBackground(SIDEBAR_BG);
        
        JLabel logoIcon = new JLabel(FontIcon.of(FontAwesomeSolid.BOLT, 28, SOFT_TEAL));
        JLabel logoText = new JLabel("<html><span style='font-size:16px'>WTM</span><br><span style='font-size:12px;color:gray'>System</span></html>");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoText.setForeground(DARK_SLATE);
        
        logoPanel.add(logoIcon);
        logoPanel.add(logoText);
        
        sidebar.add(logoPanel);

        // 2. Menu Items
        sidebar.add(createMenuButton("Dashboard", FontAwesomeSolid.TACHOMETER_ALT, null));
        sidebar.add(Box.createVerticalStrut(8)); // Jarak antar menu diperbesar
        
        if ("Operator".equalsIgnoreCase(userRole)) {
            sidebar.add(createMenuButton("Data Turbin", FontAwesomeSolid.FAN, e -> new TurbineManagementView().setVisible(true)));
            sidebar.add(Box.createVerticalStrut(8));
            sidebar.add(createMenuButton("Maintenance Log", FontAwesomeSolid.TOOLS, e -> JOptionPane.showMessageDialog(this, "Fitur Coming Soon!")));
        } 
        else if ("Pemilik".equalsIgnoreCase(userRole)) {
            sidebar.add(createMenuButton("Laporan Statistik", FontAwesomeSolid.CHART_PIE, e -> new StatisticsView().setVisible(true)));
        }

        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(createMenuButton("User Profile", FontAwesomeSolid.USER_CIRCLE, null));
        
        // Spacer kebawah
        sidebar.add(Box.createVerticalGlue());
        
        // Logout
        sidebar.add(createMenuButton("Logout", FontAwesomeSolid.SIGN_OUT_ALT, e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        }));
        sidebar.add(Box.createVerticalStrut(40));

        return sidebar;
    }

    private JPanel createMenuButton(String text, FontAwesomeSolid iconCode, ActionListener action) {
        // Layout: gap left lebih besar biar rapi
        JPanel menuPanel = new JPanel(new MigLayout("fill, insets 12 30 12 20", "[][grow]", "[]")); 
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setMaximumSize(new Dimension(270, 55));
        menuPanel.setPreferredSize(new Dimension(270, 55));
        menuPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        FontIcon icon = FontIcon.of(iconCode, 20, DARK_SLATE); // Icon size 20
        JLabel iconLabel = new JLabel(icon);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textLabel.setForeground(DARK_SLATE);

        menuPanel.add(iconLabel, "gapright 20");
        menuPanel.add(textLabel);

        menuPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (action != null) action.actionPerformed(null);
            }
            public void mouseEntered(MouseEvent e) {
                menuPanel.setBackground(new Color(235, 245, 243)); // Highlight soft teal muda
                icon.setIconColor(SOFT_TEAL);
                textLabel.setForeground(SOFT_TEAL);
                textLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
                // Add border kiri sebagai indikator aktif
                menuPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, SOFT_TEAL));
                iconLabel.repaint();
            }
            public void mouseExited(MouseEvent e) {
                menuPanel.setBackground(SIDEBAR_BG);
                icon.setIconColor(DARK_SLATE);
                textLabel.setForeground(DARK_SLATE);
                textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                menuPanel.setBorder(null);
                iconLabel.repaint();
            }
        });

        return menuPanel;
    }

    // ============ HEADER ============
    private JPanel createHeader() {
        JPanel header = new JPanel(new MigLayout("fill, insets 20 40 20 40", "[grow][]", "[center]"));
        header.setBackground(CREAM_WHITE);
        // Shadow halus di bawah header
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel greetingLabel = new JLabel("Halo, " + userName);
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        greetingLabel.setForeground(DARK_SLATE);

        JLabel roleLabel = new JLabel("Role: " + userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(LIGHT_GRAY);

        JPanel textPanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        textPanel.setBackground(CREAM_WHITE);
        textPanel.add(greetingLabel);
        textPanel.add(roleLabel);

        // Status Indicator
        JLabel statusIcon = new JLabel(FontIcon.of(FontAwesomeSolid.CIRCLE, 12, SUCCESS_GREEN));
        JLabel statusText = new JLabel(" System Online");
        statusText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusText.setForeground(SUCCESS_GREEN);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(new Color(232, 245, 233)); // Background hijau sangat muda
        statusPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        // Membuat status panel terlihat seperti 'pill' / kapsul (opsional, perlu custom paint kalau mau rounded banget)
        statusPanel.add(statusIcon);
        statusPanel.add(statusText);

        header.add(textPanel, "grow");
        header.add(statusPanel);

        return header;
    }

    // ============ CONTENT ============
    private JPanel createContent() {
        // PERBAIKAN: Row constraint '[]40[]' memberi jarak 40px antar baris
        JPanel content = new JPanel(new MigLayout("fillx, insets 40", "[grow]", "[]40[]"));
        content.setBackground(CREAM_WHITE);

        // Summary Cards
        // PERBAIKAN: Hapus '[140!]' (tinggi fix), ganti '[]' (auto) atau '[pref!]'
        JPanel cardsPanel = new JPanel(new MigLayout("fillx, gap 30, insets 0", "[33%][33%][33%]", "[pref!]"));
        cardsPanel.setBackground(CREAM_WHITE);

        cardsPanel.add(createSummaryCard("Turbin Aktif", "48", "Unit", FontAwesomeSolid.FAN, SUCCESS_GREEN), "grow");
        cardsPanel.add(createSummaryCard("Kec. Angin", "12.5", "m/s", FontAwesomeSolid.WIND, SKY_BLUE), "grow");
        cardsPanel.add(createSummaryCard("Output Listrik", "2.4", "MW", FontAwesomeSolid.BOLT, WARM_ORANGE), "grow");

        // Welcome Box
        RoundedPanel welcomePanel = new RoundedPanel(25);
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setLayout(new MigLayout("fill, insets 50", "[center]", "[]15[]"));
        // Border tipis
        welcomePanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel welcomeIcon = new JLabel(FontIcon.of(FontAwesomeSolid.SMILE_BEAM, 60, SOFT_TEAL));
        
        JLabel welcomeTitle = new JLabel("Selamat Datang di Dashboard " + userRole);
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeTitle.setForeground(DARK_SLATE);
        
        JLabel welcomeDesc = new JLabel("Pilih menu di sidebar sebelah kiri untuk mulai mengelola data turbin.");
        welcomeDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeDesc.setForeground(LIGHT_GRAY);
        
        welcomePanel.add(welcomeIcon, "wrap, gapbottom 20");
        welcomePanel.add(welcomeTitle, "wrap");
        welcomePanel.add(welcomeDesc);

        content.add(cardsPanel, "grow, wrap");
        content.add(welcomePanel, "grow, h 100%"); // Isi sisa ruang

        return content;
    }

    private JPanel createSummaryCard(String title, String value, String unit, FontAwesomeSolid iconCode, Color accent) {
        RoundedPanel card = new RoundedPanel(20);
        card.setBackground(Color.WHITE);
        // PERBAIKAN LAYOUT KARTU:
        // [grow][] -> Kolom 1 (teks) ambil sisa ruang, Kolom 2 (icon) ambil secukupnya
        // gap 0 -> insets sudah cukup
        card.setLayout(new MigLayout("fill, insets 25 30 25 30", "[grow][]", "[]10[]5[]")); 
        
        // Border bawah berwarna
        card.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, accent));

        JLabel tLabel = new JLabel(title);
        tLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tLabel.setForeground(LIGHT_GRAY);
        
        // Icon Besar Transparan
        Color iconColor = new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40); // Lebih transparan
        JLabel iLabel = new JLabel(FontIcon.of(iconCode, 50, iconColor)); // Icon diperbesar jadi 50

        JLabel vLabel = new JLabel(value);
        vLabel.setFont(new Font("Segoe UI", Font.BOLD, 42)); // Font angka diperbesar
        vLabel.setForeground(DARK_SLATE);

        JLabel uLabel = new JLabel(unit);
        uLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        uLabel.setForeground(accent);

        // Add Components
        // tLabel di kiri atas
        card.add(tLabel, "cell 0 0"); 
        
        // iLabel di kanan, span 3 baris vertikal, align kanan tengah
        card.add(iLabel, "cell 1 0 1 3, align right center"); 
        
        // vLabel di kiri tengah
        card.add(vLabel, "cell 0 1");
        
        // uLabel di kiri bawah
        card.add(uLabel, "cell 0 2");

        return card;
    }

    // ============ UTILS ============
    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        public RoundedPanel(int radius) {
            super(); this.cornerRadius = radius; setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            super.paintComponent(g);
        }
    }
}