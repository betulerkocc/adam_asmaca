package com.mycompany.adamasmaca;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DosyaYoneticisi {

private static final String TXT_YOLU = "C:\\P2Oyun\\TXTDosyalar\\";
public static final String RESIMLER_YOLU = "C:\\P2Oyun\\Resimler\\";
public static final String KELIMELER_DOSYA = TXT_YOLU + "kelimeler.txt";
public static final String SIFRE_DOSYA = TXT_YOLU + "sifre.txt";
public static final String LOG_DOSYA = TXT_YOLU + "log.txt";
public static final String OYUNLAR_DOSYA = TXT_YOLU + "oyunlar.txt";

    public static String sifreOku() {
        try (BufferedReader br = new BufferedReader(new FileReader(SIFRE_DOSYA))) {
            String satir = br.readLine();
            if (satir != null && !satir.trim().isEmpty()) return satir.trim();
        } catch (IOException e) {}
        return null;
    }

    public static void sifreKaydet(String sifre) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SIFRE_DOSYA, false))) {
            pw.println(sifre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> kelimeleriOku() {
        List<String> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(KELIMELER_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().isEmpty()) liste.add(satir.trim().toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static void logKaydet(String mesaj) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_DOSYA, true))) {
            pw.println(tarih + " | " + mesaj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void oyunKaydet(String sure, String sonuc) {
        String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        try (PrintWriter pw = new PrintWriter(new FileWriter(OYUNLAR_DOSYA, true))) {
            pw.println(tarih + " | " + sure + " saniye | " + sonuc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> oyunlariOku() {
        List<String[]> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(OYUNLAR_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().isEmpty()) {
                    String[] parcalar = satir.split("\\|");
                    if (parcalar.length >= 3) {
                        liste.add(new String[]{parcalar[0].trim(), parcalar[1].trim(), parcalar[2].trim()});
                    }
                }
            }
        } catch (IOException e) {}
        return liste;
    }

    public static List<String[]> loglariOku() {
        List<String[]> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().isEmpty()) {
                    String[] parcalar = satir.split("\\|");
                    if (parcalar.length >= 2) {
                        liste.add(new String[]{parcalar[0].trim(), parcalar[1].trim()});
                    }
                }
            }
        } catch (IOException e) {}
        return liste;
    }

    public static void dosyaTemizle(String dosyaYolu) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dosyaYolu, false))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}