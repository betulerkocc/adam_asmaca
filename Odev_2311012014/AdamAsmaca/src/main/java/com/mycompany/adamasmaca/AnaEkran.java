package com.mycompany.adamasmaca;

import javax.swing.*;
import java.awt.*;

public class AnaEkran extends JFrame {

    private OyunPaneli oyunPaneli;
    private SkorPaneli skorPaneli;
    private LogPaneli logPaneli;
    private JTabbedPane tabbedPane;

    public AnaEkran() {
        setTitle("Adam Asmaca");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuOlustur();
        panelleriOlustur();
    }

    private void menuOlustur() {
        JMenuBar menuBar = new JMenuBar();
        JMenu oyunMenu = new JMenu("Oyun");

        JMenuItem yeniOyun = new JMenuItem("Oyuna Başla");
        JMenuItem yenidenBaslat = new JMenuItem("Oyunu Yeniden Başlat");
        JMenuItem cikis = new JMenuItem("Çıkış");

        yeniOyun.addActionListener(e -> {
            tabbedPane.setSelectedIndex(0);
            oyunPaneli.oyunuBaslat();
        });

        yenidenBaslat.addActionListener(e -> {
            oyunPaneli.oyunuBaslat();
            tabbedPane.setSelectedIndex(0);
        });

        cikis.addActionListener(e -> System.exit(0));

        oyunMenu.add(yeniOyun);
        oyunMenu.add(yenidenBaslat);
        oyunMenu.addSeparator();
        oyunMenu.add(cikis);
        menuBar.add(oyunMenu);
        setJMenuBar(menuBar);
    }

    private void panelleriOlustur() {
        tabbedPane = new JTabbedPane();

        oyunPaneli = new OyunPaneli();
        skorPaneli = new SkorPaneli();
        logPaneli = new LogPaneli();

        tabbedPane.addTab("🎮 Oyun", oyunPaneli);
        tabbedPane.addTab("🏆 Skorlar", skorPaneli);
        tabbedPane.addTab("📋 Loglar", logPaneli);

        add(tabbedPane, BorderLayout.CENTER);
    }
}