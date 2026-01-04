package com.zahran.tugasakhirpbo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatLightLaf;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class LandingPage extends JFrame {

    // ============ COLOR PALETTE ============
    private static final Color CREAM_WHITE = new Color(253, 251, 247);
    private static final Color SOFT_TEAL = new Color(38, 166, 154);
    private static final Color LIGHT_TEAL = new Color(178, 223, 219);
    private static final Color WARM_ORANGE = new Color(255, 138, 101);
    private static final Color DARK_SLATE = new Color(55, 71, 79);
    private static final Color DARK_GRAY = new Color(33, 33, 33);
    private static final Color TEXT_GRAY = new Color(102, 102, 102);
    private static final Color BORDER_GRAY = new Color(220, 220, 220);
    private static final Color LIGHT_GRAY = new Color(180, 180, 180);

    private static final int CORNER_RADIUS = 15;
    private JScrollPane scrollPane;

    public LandingPage() {
        setTitle("WTM System - Wind Turbine Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(1280, 850); 
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM_WHITE);

        // 1. Navbar
        mainPanel.add(createNavBar(), BorderLayout.NORTH);

        // 2. Content
        JPanel contentPanel = new JPanel(new MigLayout("fillx, wrap 1, insets 0, gap 0", "[grow, fill]", "[]0[]0[]0[]"));
        contentPanel.setBackground(CREAM_WHITE);

        // --- MENYUSUN SECTION ---
        
        // Hero Section
        contentPanel.add(createHeroSection(), "growx, h 400!"); 
        
        // About Section
        contentPanel.add(createAboutSection(), "growx"); 
        
        // Profile Section (Include Dosen)
        contentPanel.add(createProfileSection(), "growx");
        
        // Spacer sebelum footer
        contentPanel.add(Box.createVerticalStrut(60), "growx");
        
        // Footer (Dijamin Full Width karena parent pakai 'fill')
        contentPanel.add(createFooterSection(), "growx");

        // 3. ScrollPane Setup
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        
        SwingUtilities.invokeLater(() -> contentPanel.requestFocusInWindow());
    }

    // ============ SECTION 1: NAVBAR ============
    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new MigLayout("fillx, insets 15 50 15 50", "[left][grow][right]", ""));
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_GRAY));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        logoPanel.setBackground(Color.WHITE);
        
        JLabel logoIcon = new JLabel(FontIcon.of(FontAwesomeSolid.BOLT, 24, SOFT_TEAL));
        JLabel logoText = new JLabel("WTM System");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logoText.setForeground(SOFT_TEAL);
        
        logoPanel.add(logoIcon);
        logoPanel.add(logoText);

        JPanel menuPanel = new JPanel(new MigLayout("insets 0", "", ""));
        menuPanel.setBackground(Color.WHITE);

        JLabel aboutBtn = createNavButton("About", FontAwesomeSolid.INFO_CIRCLE);
        aboutBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { scrollToComponent("about"); }
        });

        JLabel progBtn = createNavButton("Programmer", FontAwesomeSolid.CODE);
        progBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { scrollToComponent("programmer"); }
        });

        RoundedButton loginBtn = new RoundedButton("Login", WARM_ORANGE, CORNER_RADIUS);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_IN_ALT, 16, Color.WHITE));
        loginBtn.setIconTextGap(8);
        loginBtn.setPreferredSize(new Dimension(120, 40));
        loginBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        menuPanel.add(aboutBtn, "gapright 30");
        menuPanel.add(progBtn, "gapright 30");
        menuPanel.add(loginBtn);

        navBar.add(logoPanel, "cell 0 0");
        navBar.add(menuPanel, "cell 2 0, align right");

        return navBar;
    }

    private JLabel createNavButton(String text, FontAwesomeSolid iconCode) {
        JLabel btn = new JLabel(text);
        btn.setIcon(FontIcon.of(iconCode, 16, DARK_SLATE));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(DARK_SLATE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(8);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(SOFT_TEAL);
                btn.setIcon(FontIcon.of(iconCode, 16, SOFT_TEAL));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(DARK_SLATE);
                btn.setIcon(FontIcon.of(iconCode, 16, DARK_SLATE));
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            }
        });
        return btn;
    }

    // ============ SECTION 2: HERO ============
    private JPanel createHeroSection() {
        GradientPanel heroPanel = new GradientPanel(SOFT_TEAL, LIGHT_TEAL);
        heroPanel.setLayout(new MigLayout("fill, wrap 1, center, insets 0", "[center]", "[]10[]10[]40[]")); 
        
        JPanel container = new JPanel(new BorderLayout());
        container.add(heroPanel, BorderLayout.CENTER);
        container.setPreferredSize(new Dimension(1200, 400));

        JLabel heroIcon = new JLabel(FontIcon.of(FontAwesomeSolid.FAN, 70, Color.WHITE));
        
        JLabel titleLabel = new JLabel("Monitoring Turbin Angin Cerdas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 44));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Solusi pemantauan energi terbarukan secara real-time, akurat, dan terintegrasi.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(255, 255, 255, 230));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        RoundedButton ctaBtn = new RoundedButton("Mulai Sekarang", WARM_ORANGE, 25);
        ctaBtn.setFont(new Font("Segoe UI", Font.BOLD, 17));
        ctaBtn.setForeground(Color.WHITE);
        ctaBtn.setIcon(FontIcon.of(FontAwesomeSolid.ARROW_RIGHT, 18, Color.WHITE));
        ctaBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        ctaBtn.setIconTextGap(12);
        ctaBtn.setPreferredSize(new Dimension(220, 55));
        ctaBtn.addActionListener(e -> {
             new LoginView().setVisible(true);
             dispose();
        });

        heroPanel.add(heroIcon, "wrap");
        heroPanel.add(titleLabel, "wrap");
        heroPanel.add(subtitleLabel, "wrap");
        heroPanel.add(ctaBtn);

        return container;
    }

    // ============ SECTION 3: ABOUT ============
    private JPanel createAboutSection() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBackground(CREAM_WHITE);
        aboutPanel.putClientProperty("about", "about");

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(CREAM_WHITE);
        JLabel titleLabel = new JLabel("Tentang WTM System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(DARK_SLATE);
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.QUESTION_CIRCLE, 30, SOFT_TEAL));
        titleLabel.setIconTextGap(12);
        titlePanel.add(titleLabel);

        // Desc
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descPanel.setBackground(CREAM_WHITE);
        String appDescription = "<html><body style='width: 600px; text-align: center;'>" + 
            "Wind Turbine Monitoring System (WTM) adalah aplikasi berbasis desktop yang dirancang " +
            "untuk memantau kinerja, status operasional, dan output daya turbin angin secara real-time." +
            "</body></html>";
        JLabel descLabel = new JLabel(appDescription);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setForeground(TEXT_GRAY);
        descPanel.add(descLabel);

        aboutPanel.add(Box.createVerticalStrut(40));
        aboutPanel.add(titlePanel);
        aboutPanel.add(Box.createVerticalStrut(10));
        aboutPanel.add(descPanel);
        aboutPanel.add(Box.createVerticalStrut(40));

        // Cards Grid
        JPanel gridContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridContainer.setBackground(CREAM_WHITE);
        
        JPanel cardsWrapper = new JPanel(new GridLayout(1, 3, 30, 0)); 
        cardsWrapper.setBackground(CREAM_WHITE);
        cardsWrapper.setPreferredSize(new Dimension(1000, 250));

        cardsWrapper.add(createFeatureCard("Monitoring Real-Time", 
            "Pantau kecepatan angin, daya listrik (MW), dan status aktif turbin.", 
            FontAwesomeSolid.DESKTOP));
        
        cardsWrapper.add(createFeatureCard("Manajemen Data", 
            "Fitur CRUD lengkap untuk mengelola inventaris unit turbin.", 
            FontAwesomeSolid.DATABASE));
        
        cardsWrapper.add(createFeatureCard("Analisis Visual", 
            "Visualisasi data statistik menggunakan grafik interaktif.", 
            FontAwesomeSolid.CHART_PIE));

        gridContainer.add(cardsWrapper);
        aboutPanel.add(gridContainer);

        return aboutPanel;
    }

    private JPanel createFeatureCard(String title, String desc, FontAwesomeSolid iconCode) {
        RoundedPanel card = new RoundedPanel(Color.WHITE, CORNER_RADIUS);
        card.setLayout(new MigLayout("fill, wrap 1, insets 20, al center", "[center]", "[]15[]10[]")); 
        card.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));

        JLabel iconLabel = new JLabel(FontIcon.of(iconCode, 45, SOFT_TEAL));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(DARK_SLATE);

        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + desc + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(iconLabel, "wrap");
        card.add(titleLabel, "wrap");
        card.add(descLabel, "grow");

        return card;
    }

    // ============ SECTION 4: PROGRAMMER PROFILE ============
    private JPanel createProfileSection() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(CREAM_WHITE);
        profilePanel.putClientProperty("programmer", "programmer");

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(CREAM_WHITE);
        JLabel titleLabel = new JLabel("Developer Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(DARK_SLATE);
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.CODE, 30, WARM_ORANGE));
        titleLabel.setIconTextGap(15);
        titlePanel.add(titleLabel);
        
        profilePanel.add(Box.createVerticalStrut(40));
        profilePanel.add(titlePanel);
        profilePanel.add(Box.createVerticalStrut(20));

        // Card Wrapper
        JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardContainer.setBackground(CREAM_WHITE);

        RoundedPanel profileCard = new RoundedPanel(Color.WHITE, CORNER_RADIUS);
        profileCard.setLayout(new MigLayout("insets 40, gap 40", "[center]20[grow]", "[center]"));
        profileCard.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));
        profileCard.setPreferredSize(new Dimension(850, 300)); 

        // Foto
        JPanel photoPanel = new JPanel(new GridBagLayout());
        photoPanel.setBackground(Color.WHITE);
        JLabel photoIcon = new JLabel(FontIcon.of(FontAwesomeSolid.USER_TIE, 100, SOFT_TEAL));
        photoPanel.add(photoIcon);
        
        RoundedPanel circleBg = new RoundedPanel(LIGHT_TEAL, 100); 
        circleBg.setPreferredSize(new Dimension(150, 150));
        circleBg.setLayout(new BorderLayout());
        circleBg.add(photoIcon, BorderLayout.CENTER);

        // Info
        JPanel infoWrapper = new JPanel(new MigLayout("wrap 1, insets 0", "[left]"));
        infoWrapper.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Muhammad Zahran");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        nameLabel.setForeground(DARK_SLATE);
        
        JLabel projectLabel = new JLabel("Tugas Akhir Pemrograman Berorientasi Objek (PBO)");
        projectLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        projectLabel.setForeground(WARM_ORANGE);
        
        JPanel detailGrid = new JPanel(new GridLayout(2, 2, 30, 5));
        detailGrid.setBackground(Color.WHITE);
        detailGrid.add(createDetailItem(FontAwesomeSolid.ID_CARD, "24343077"));
        detailGrid.add(createDetailItem(FontAwesomeSolid.LAPTOP_CODE, "Informatika"));
        detailGrid.add(createDetailItem(FontAwesomeSolid.BUILDING, "Fakultas Teknik"));
        detailGrid.add(createDetailItem(FontAwesomeSolid.UNIVERSITY, "UNIVERSITAS NEGERI PADANG"));

        // Dosen Pengampu (Ditambahkan disini)
        JPanel dosenPanel = createDetailItem(FontAwesomeSolid.CHALKBOARD_TEACHER, "Dosen Pengampu: Drs. Denny Kurniadi, M.Kom.");

        JLabel bioLabel = new JLabel("<html><i>\"Mahasiswa Informatika yang berdedikasi tinggi dalam pengembangan perangkat lunak modern.\"</i></html>");
        bioLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        bioLabel.setForeground(TEXT_GRAY);

        infoWrapper.add(nameLabel, "wrap");
        infoWrapper.add(projectLabel, "wrap, gapbottom 10");
        infoWrapper.add(detailGrid, "wrap, gapbottom 10");
        infoWrapper.add(dosenPanel, "wrap, gapbottom 15"); // Dosen Added
        infoWrapper.add(bioLabel, "wrap");

        profileCard.add(circleBg);
        profileCard.add(infoWrapper);

        cardContainer.add(profileCard);
        profilePanel.add(cardContainer);

        return profilePanel;
    }
    
    private JPanel createDetailItem(FontAwesomeSolid icon, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setBackground(Color.WHITE);
        JLabel ico = new JLabel(FontIcon.of(icon, 16, SOFT_TEAL));
        JLabel txt = new JLabel(" " + text);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(DARK_SLATE);
        p.add(ico);
        p.add(txt);
        return p;
    }

    // ============ SECTION 5: FOOTER (FULL WIDTH FIX) ============
    private JPanel createFooterSection() {

        JPanel footerPanel = new JPanel(new MigLayout("fillx, wrap 1, insets 50 20 30 20, align center", "[center]"));
        footerPanel.setBackground(DARK_GRAY);

        JLabel titleLabel = new JLabel("Kontak & Informasi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        JLabel addressLabel = new JLabel("Jl. Prof. Dr. Hamka, Air Tawar Bar., Kec. Padang Utara, Kota Padang");
        addressLabel.setIcon(FontIcon.of(FontAwesomeSolid.MAP_MARKER_ALT, 16, LIGHT_GRAY));
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        addressLabel.setForeground(LIGHT_GRAY);
        addressLabel.setIconTextGap(10);
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        JLabel websiteLabel = new JLabel("www.unp.ac.id");
        websiteLabel.setIcon(FontIcon.of(FontAwesomeSolid.GLOBE, 16, LIGHT_TEAL));
        websiteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        websiteLabel.setForeground(LIGHT_TEAL);
        websiteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        websiteLabel.setIconTextGap(10);
        websiteLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        websiteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { openWebsite("https://unp.ac.id"); }
        });

        JLabel copyrightLabel = new JLabel("© 2025 Wind Turbine Monitoring System. All rights reserved.");
        copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        copyrightLabel.setForeground(new Color(150, 150, 150));
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        footerPanel.add(titleLabel, "wrap, gapbottom 10");
        footerPanel.add(addressLabel, "wrap");
        footerPanel.add(websiteLabel, "wrap, gapbottom 25");
        footerPanel.add(copyrightLabel);

        return footerPanel;
    }

    private void scrollToComponent(String identifier) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = ((JPanel) scrollPane.getViewport().getView()).getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    Object prop = ((JPanel) comp).getClientProperty(identifier);
                    if (prop != null) {
                        scrollPane.getVerticalScrollBar().setValue(comp.getY());
                        break;
                    }
                }
            }
        });
    }

    private void openWebsite(String url) {
        try { Desktop.getDesktop().browse(new URI(url)); } 
        catch (IOException | URISyntaxException ex) { 
            JOptionPane.showMessageDialog(this, "Tidak dapat membuka link: " + url);
        }
    }

    // ============ CUSTOM CLASSES ============
    
    private static class RoundedButton extends JButton {
        private final int cornerRadius;
        private final Color backgroundColor;
        public RoundedButton(String text, Color bg, int radius) {
            super(text); 
            this.backgroundColor = bg; 
            this.cornerRadius = radius;
            setOpaque(false); setContentAreaFilled(false); 
            setFocusPainted(false); setBorderPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? backgroundColor.darker() : backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            super.paintComponent(g);
        }
    }

    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        public RoundedPanel(Color bg, int radius) {
            super(); this.cornerRadius = radius; setBackground(bg); setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            super.paintComponent(g);
        }
    }

    private static class GradientPanel extends JPanel {
        private final Color c1, c2;
        public GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LandingPage().setVisible(true));
    }
}