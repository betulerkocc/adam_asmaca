package com.mycompany.adamasmaca;

import javax.swing.SwingUtilities;

public class AdamAsmaca {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SifreEkrani sifreEkrani = new SifreEkrani();
            sifreEkrani.setVisible(true);

            if (sifreEkrani.isGirisBasarili()) {
                AnaEkran anaEkran = new AnaEkran();
                anaEkran.setVisible(true);
            }
        });
    }
}