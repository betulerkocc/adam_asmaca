package com.mycompany.adamasmaca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SkorPaneli extends JPanel {

    private JTable tablo;
    private DefaultTableModel model;

    public SkorPaneli() {
        setLayout(new BorderLayout(10, 10));
        arayuzOlustur();
    }

    private void arayuzOlustur() {
        String[] sutunlar = {"Tarih", "Süre", "Sonuç"};
        model = new DefaultTableModel(sutunlar, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablo = new JTable(model);
        tablo.setFont(new Font("Arial", Font.PLAIN, 13));
        tablo.setRowHeight(25);
        tablo.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        verileriYukle();

        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Oyun Geçmişi"));
        add(scrollPane, BorderLayout.CENTER);

        JButton temizleBtn = new JButton("Temizle");
        temizleBtn.setFont(new Font("Arial", Font.BOLD, 13));
        temizleBtn.addActionListener(e -> temizle());

        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        altPanel.add(temizleBtn);
        add(altPanel, BorderLayout.SOUTH);
    }

    public void verileriYukle() {
        model.setRowCount(0);
        List<String[]> oyunlar = DosyaYoneticisi.oyunlariOku();
        for (String[] satir : oyunlar) {
            model.addRow(satir);
        }
    }

    private void temizle() {
        String girilen = JOptionPane.showInputDialog(this, "Şifreyi girin:");
        if (girilen == null) return;

        String gercekSifre = DosyaYoneticisi.sifreOku();
        if (girilen.equals(gercekSifre)) {
            DosyaYoneticisi.dosyaTemizle(DosyaYoneticisi.OYUNLAR_DOSYA);
            model.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Skorlar temizlendi!");
        } else {
            JOptionPane.showMessageDialog(this, "Hatalı şifre!");
        }
    }
}