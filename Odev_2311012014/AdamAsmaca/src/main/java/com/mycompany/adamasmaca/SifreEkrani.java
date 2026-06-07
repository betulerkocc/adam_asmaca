package com.mycompany.adamasmaca;

import javax.swing.*;
import java.awt.*;

public class SifreEkrani extends JDialog {

    private boolean girisBasarili = false;

    public SifreEkrani() {
        setTitle("Şifre");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        String mevcutSifre = DosyaYoneticisi.sifreOku();

        if (mevcutSifre == null) {
            sifreBelirleEkrani();
        } else {
            sifreGirisEkrani(mevcutSifre);
        }
    }

    private void sifreBelirleEkrani() {
        setTitle("Yeni Şifre Belirle");
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPasswordField yeniSifre = new JPasswordField();
        JPasswordField tekrarSifre = new JPasswordField();

        panel.add(new JLabel("Yeni Şifre:"));
        panel.add(yeniSifre);
        panel.add(new JLabel("Tekrar:"));
        panel.add(tekrarSifre);

        JButton kaydetBtn = new JButton("Kaydet");
        panel.add(new JLabel(""));
        panel.add(kaydetBtn);

        kaydetBtn.addActionListener(e -> {
            String s1 = new String(yeniSifre.getPassword());
            String s2 = new String(tekrarSifre.getPassword());

            if (s1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Şifre boş olamaz!");
                return;
            }
            if (!s1.equals(s2)) {
                JOptionPane.showMessageDialog(this, "Şifreler eşleşmiyor!");
                return;
            }

            DosyaYoneticisi.sifreKaydet(s1);
            DosyaYoneticisi.logKaydet("Yeni şifre belirlendi");
            JOptionPane.showMessageDialog(this, "Şifre kaydedildi!");
            girisBasarili = true;
            dispose();
        });

        add(panel);
    }

    private void sifreGirisEkrani(String mevcutSifre) {
        setTitle("Giriş");
        int[] hakSayisi = {3};

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPasswordField sifreField = new JPasswordField();
        JLabel hakLabel = new JLabel("Kalan hak: 3");

        panel.add(new JLabel("Şifre:"));
        panel.add(sifreField);
        panel.add(hakLabel);
        panel.add(new JLabel(""));

        JButton girisBtn = new JButton("Giriş");
        panel.add(new JLabel(""));
        panel.add(girisBtn);

        girisBtn.addActionListener(e -> {
            String girilen = new String(sifreField.getPassword());

            if (girilen.equals(mevcutSifre)) {
                DosyaYoneticisi.logKaydet("Başarılı giriş");
                girisBasarili = true;
                dispose();
            } else {
                hakSayisi[0]--;
                DosyaYoneticisi.logKaydet("Hatalı şifre denemesi");
                sifreField.setText("");

                if (hakSayisi[0] <= 0) {
                    JOptionPane.showMessageDialog(this, "3 kez hatalı giriş! Program kapanıyor.");
                    System.exit(0);
                } else {
                    hakLabel.setText("Kalan hak: " + hakSayisi[0]);
                    JOptionPane.showMessageDialog(this, "Hatalı şifre! Kalan hak: " + hakSayisi[0]);
                }
            }
        });

        add(panel);
    }

    public boolean isGirisBasarili() {
        return girisBasarili;
    }
}