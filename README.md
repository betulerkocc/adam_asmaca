# Adam Asmaca (Hangman Game) 🎮

Bu proje, Python kullanılarak geliştirilmiş klasör yapısına sahip dinamik bir Adam Asmaca oyunudur. Kullanıcıların harf tahminleri yaparak kelimeleri bulmasını hedefler ve görsel bir arayüz/konsol tasarımı sunar.

## 📌 Özellikler
* **Dinamik Kelime Havuzu:** Farklı kategorilerden veya rastgele seçilen kelimeler.
* **Görsel Durum Takibi:** Her hatalı tahminde aşama aşama çizilen adam asmaca görseli.
* **Skor ve Hak Sistemi:** Kullanıcının kalan haklarını ve doğru tahminlerini anlık takip etme.
* **Hata Yönetimi:** Daha önce girilen harfler veya geçersiz karakterler için uyarılar.

## 📂 Proje Yapısı (Tree Yapısı)
```text
.
├── src/
│   ├── main.py          # Oyunun ana döngüsünün çalıştığı dosya
│   ├── logic.py         # Oyun kuralları ve tahmin kontrol mekanizmaları
│   └── words.py         # Kelime havuzunun tutulduğu veri dosyası
├── assets/
│   └── hangman_art.py   # Aşama aşama adam asmaca çizimleri (ASCII Art)
└── README.md            # Proje dokümantasyonu
```

## 🚀 Kurulum ve Çalıştırma
Projeyi yerel bilgisayarınızda çalıştırmak için aşağıdaki adımları takip edebilirsiniz.

Gereksinimler
Python 3.x sürümünün bilgisayarınızda kurulu olduğundan emin olun.

Çalıştırma Adımları
- Projeyi bilgisayarınıza indirin veya klonlayın:
  git clone [https://github.com/betulerkocc/adam_asmaca.git](https://github.com/betulerkocc/adam_asmaca.git)

- Proje dizinine gidin:
  cd adam_asmaca

- Ana oyunu başlatın:
  python src/main.py

## 🛠️ Kullanılan Teknolojiler
Dil: Python 3.x
Versiyon Kontrolü: Git & GitHub
Geliştirici: Betül Erkoç
