package com.mycompany.adamasmaca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class OyunPaneli extends JPanel {

    private String gizliKelime;
    private JLabel[] harfLabellar;
    private JTextField harfField;
    private JTextField kelimeField;
    private JLabel resimLabel;
    private JLabel sureLbl;
    private JLabel duruumLbl;
    private JPanel harfPanel;
    private int yanlisSayisi = 0;
    private int gecenSaniye = 0;
    private Timer sayacTimer;
    private Set<Character> tahminEdilenHarfler = new HashSet<>();
    private boolean oyunBitti = false;

    public OyunPaneli() {
        setLayout(new BorderLayout(10, 10));
        arayuzOlustur();
        oyunuBaslat();
    }

    private void arayuzOlustur() {
        // Üst panel - süre
        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sureLbl = new JLabel("Süre: 0 saniye");
        sureLbl.setFont(new Font("Arial", Font.BOLD, 14));
        ustPanel.add(sureLbl);
        add(ustPanel, BorderLayout.NORTH);

        // Sol panel - resim
        JPanel solPanel = new JPanel(new BorderLayout());
        resimLabel = new JLabel();
        resimLabel.setPreferredSize(new Dimension(220, 280));
        resimLabel.setHorizontalAlignment(JLabel.CENTER);
        solPanel.add(resimLabel, BorderLayout.CENTER);
        add(solPanel, BorderLayout.WEST);

        // Orta panel - harfler
        JPanel ortaPanel = new JPanel(new BorderLayout(5, 10));

        harfPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        harfPanel.setBorder(BorderFactory.createTitledBorder("Kelime"));
        ortaPanel.add(harfPanel, BorderLayout.CENTER);

        // Alt tahmin paneli
        JPanel tahminPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        tahminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        harfField = new JTextField();
        kelimeField = new JTextField();

        JButton harfBtn = new JButton("Harf Tahmin Et");
        JButton kelimeBtn = new JButton("Kelime Tahmin Et");

        duruumLbl = new JLabel(" ");
        duruumLbl.setFont(new Font("Arial", Font.BOLD, 13));
        duruumLbl.setForeground(Color.RED);

        tahminPanel.add(new JLabel("Harf:"));
        tahminPanel.add(harfField);
        tahminPanel.add(new JLabel("Kelime:"));
        tahminPanel.add(kelimeField);
        tahminPanel.add(harfBtn);
        tahminPanel.add(kelimeBtn);

        ortaPanel.add(tahminPanel, BorderLayout.SOUTH);
        ortaPanel.add(duruumLbl, BorderLayout.NORTH);

        add(ortaPanel, BorderLayout.CENTER);

        // Buton aksiyonları
        harfBtn.addActionListener(e -> harfTahminEt());
        kelimeBtn.addActionListener(e -> kelimeTahminEt());

        harfField.addActionListener(e -> harfTahminEt());
        kelimeField.addActionListener(e -> kelimeTahminEt());
    }

    public void oyunuBaslat() {
        // Timer durdur
        if (sayacTimer != null) sayacTimer.stop();

        // Değişkenleri sıfırla
        yanlisSayisi = 0;
        gecenSaniye = 0;
        tahminEdilenHarfler = new HashSet<>();
        oyunBitti = false;

        harfField.setEnabled(true);
        kelimeField.setEnabled(true);
        duruumLbl.setText(" ");

        // Rastgele kelime seç
        List<String> kelimeler = DosyaYoneticisi.kelimeleriOku();
        if (kelimeler.isEmpty()) {
            JOptionPane.showMessageDialog(this, "kelimeler.txt boş veya bulunamadı!");
            return;
        }
        Random r = new Random();
        gizliKelime = kelimeler.get(r.nextInt(kelimeler.size()));

        // Harf panelini oluştur
        harfPanelOlustur(gizliKelime.length());

        // İlk resmi göster
        resmiGuncelle(1);

        // Sayacı başlat
        sayacTimer = new Timer(1000, e -> {
    gecenSaniye++;
    sureLbl.setText("Süre: " + gecenSaniye + " saniye");
    sureLbl.revalidate();
    sureLbl.repaint();
});
sayacTimer.setRepeats(true);
sayacTimer.start();
    }

    private void harfPanelOlustur(int uzunluk) {
        harfPanel.removeAll();
        harfLabellar = new JLabel[uzunluk];
        for (int i = 0; i < uzunluk; i++) {
            harfLabellar[i] = new JLabel("*");
            harfLabellar[i].setFont(new Font("Arial", Font.BOLD, 26));
            harfLabellar[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            harfLabellar[i].setPreferredSize(new Dimension(35, 40));
            harfLabellar[i].setHorizontalAlignment(JLabel.CENTER);
            harfPanel.add(harfLabellar[i]);
        }
        harfPanel.revalidate();
        harfPanel.repaint();
    }

   private void harfTahminEt() {
    if (oyunBitti) return;

    String girdi = harfField.getText().trim().toUpperCase();
    harfField.setText("");

    if (girdi.isEmpty()) {
        duruumLbl.setText("Bir harf girin!");
        return;
    }

    // Sadece ilk karakteri al
    char harf = girdi.charAt(0);

    if (!Character.isLetter(harf)) {
        duruumLbl.setText("Sadece harf girin!");
        return;
    }

    if (tahminEdilenHarfler.contains(harf)) {
        duruumLbl.setForeground(Color.ORANGE);
        duruumLbl.setText("Bu harfi zaten denediniz: " + harf);
        return;
    }

    tahminEdilenHarfler.add(harf);

    boolean harfVar = false;
    for (int i = 0; i < gizliKelime.length(); i++) {
        if (gizliKelime.charAt(i) == harf) {
            harfLabellar[i].setText(String.valueOf(harf));
            harfVar = true;
        }
    }

    if (harfVar) {
        duruumLbl.setForeground(new Color(0, 150, 0));
        duruumLbl.setText("✓ '" + harf + "' harfi kelimede var!");
        kazandiMiKontrol();
    } else {
        yanlisSayisi++;
        resmiGuncelle(yanlisSayisi);
        duruumLbl.setForeground(Color.RED);
        duruumLbl.setText("✗ '" + harf + "' harfi yok! Yanlış: " + yanlisSayisi + "/11");
        if (yanlisSayisi >= 11) oyunKaybet();
    }
}

    private void kelimeTahminEt() {
        if (oyunBitti) return;

        String girdi = kelimeField.getText().trim().toUpperCase();
        kelimeField.setText("");

        if (girdi.isEmpty()) {
            duruumLbl.setText("Kelime girin!");
            return;
        }

        if (girdi.equals(gizliKelime)) {
            // Tüm harfleri aç
            for (int i = 0; i < gizliKelime.length(); i++) {
                harfLabellar[i].setText(String.valueOf(gizliKelime.charAt(i)));
            }
            oyunKazan();
        } else {
            yanlisSayisi++;
            resmiGuncelle(yanlisSayisi);
            duruumLbl.setForeground(Color.RED);
            duruumLbl.setText("✗ Yanlış kelime! Yanlış: " + yanlisSayisi + "/11");
            if (yanlisSayisi >= 11) oyunKaybet();
        }
    }

    private void kazandiMiKontrol() {
        for (JLabel lbl : harfLabellar) {
            if (lbl.getText().equals("*")) return;
        }
        oyunKazan();
    }

    private void oyunKazan() {
        oyunBitti = true;
        sayacTimer.stop();
        harfField.setEnabled(false);
        kelimeField.setEnabled(false);
        DosyaYoneticisi.oyunKaydet(String.valueOf(gecenSaniye), "KAZANDI");
        duruumLbl.setForeground(new Color(0, 150, 0));
        duruumLbl.setText("🎉 Tebrikler! Kelime: " + gizliKelime + " | Süre: " + gecenSaniye + "s");
        JOptionPane.showMessageDialog(this, "Tebrikler! Kelimeyi buldunuz: " + gizliKelime + "\nSüre: " + gecenSaniye + " saniye");
    }

    private void oyunKaybet() {
        oyunBitti = true;
        sayacTimer.stop();
        harfField.setEnabled(false);
        kelimeField.setEnabled(false);
        resmiGuncelle(11);
        DosyaYoneticisi.oyunKaydet(String.valueOf(gecenSaniye), "KAYBETTİ");
        duruumLbl.setForeground(Color.RED);
        duruumLbl.setText("💀 Oyun bitti! Kelime: " + gizliKelime);
        JOptionPane.showMessageDialog(this, "Oyun bitti! Doğru kelime: " + gizliKelime);
    }

    private void resmiGuncelle(int adim) {
    if (adim < 1) adim = 1;
    if (adim > 11) adim = 11;

    String yol = DosyaYoneticisi.RESIMLER_YOLU + adim + ".jpg";
    java.io.File dosya = new java.io.File(yol);
    
    if (!dosya.exists()) {
        yol = DosyaYoneticisi.RESIMLER_YOLU + adim + ".JPG";
        dosya = new java.io.File(yol);
    }
    
    if (!dosya.exists()) {
        System.out.println("Resim bulunamadı: " + yol);
        return;
    }

    ImageIcon icon = new ImageIcon(dosya.getAbsolutePath());
    Image img = icon.getImage().getScaledInstance(200, 260, Image.SCALE_SMOOTH);
    resimLabel.setIcon(new ImageIcon(img));
    resimLabel.revalidate();
    resimLabel.repaint();
}
}