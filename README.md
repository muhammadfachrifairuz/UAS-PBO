# 🏪 Aplikasi Manajemen Toko

Aplikasi desktop berbasis Java Swing untuk mengelola data produk dan kategori pada sistem inventori/penjualan. Dibangun dengan arsitektur MVC dan DAO Pattern.

---

## 📋 Daftar Isi

- [Fitur](#-fitur)
- [Teknologi](#-teknologi)
- [Arsitektur](#-arsitektur)
- [Struktur Database](#-struktur-database)
- [Cara Instalasi](#-cara-instalasi)
- [Cara Menjalankan](#-cara-menjalankan)
- [Struktur Project](#-struktur-project)
- [Kontributor](#-kontributor)

---

## ✨ Fitur

| Modul | Fitur |
|-------|-------|
| **Produk** | Tambah, Edit, Hapus, Lihat produk (6 kolom: Kode, Nama, Harga, Stock, Kategori, Brand) |
| **Kategori** | Tambah, Edit, Hapus, Lihat kategori |
| **Validasi** | Field kosong, tipe angka, nilai negatif, nilai maksimum, duplikat, seleksi data, konfirmasi hapus |
| **Khusus** | Auto refresh, cek foreign key, form terintegrasi, update dengan dialog internal |

---

## 🛠 Teknologi

| Komponen | Teknologi |
|----------|-----------|
| GUI | Java Swing |
| Database | MySQL |
| Arsitektur | MVC + DAO Pattern |
| Koneksi | JDBC |
| Model | POJO (Plain Old Java Object) |
| IDE | NetBeans |

---

## 🏗 Arsitektur

### MVC (Model-View-Controller)

| Komponen | Package | Contoh Class | Fungsi |
|----------|---------|--------------|--------|
| Model | `ClassModel` | `Produk.java`, `Kategori.java` | Data & logika bisnis |
| View | `GUI` | `FormProduk.java`, `FormKategori.java`, `DialogInputProduk.java` | Tampilan GUI |
| Controller | `GUI` + `Dao` | Event handler, `ProdukDAO`, `KategoriDAO` | Logika aplikasi & akses database |

### DAO Pattern

**KategoriDAO**:
- `getAllKategori()` - mengambil semua data kategori
- `insert(Kategori)` - menambah kategori baru
- `update(Kategori)` - mengubah data kategori
- `delete(int)` - menghapus kategori
- `isDuplikat()` - cek duplikat data
- `isDipakaiProduk()` - cek relasi foreign key

**ProdukDAO**:
- `getAllProduk()` - mengambil semua produk (dengan JOIN kategori)
- `getProdukById(int)` - mengambil satu produk
- `insert(Produk)` - menambah produk baru
- `update(Produk)` - mengubah data produk
- `delete(int)` - menghapus produk

**DBKoneksi**: Menggunakan Singleton Pattern (koneksi dibuat sekali)

---

## 🗄 Struktur Database

### Tabel Kategori

| Field | Tipe Data | Keterangan |
|-------|-----------|------------|
| `kode` | INT | Primary Key, Auto Increment |
| `kategori` | VARCHAR(100) | Nama kategori (Not Null) |
| `brand` | VARCHAR(100) | Merek/brand (Not Null) |

### Tabel Produk

| Field | Tipe Data | Keterangan |
|-------|-----------|------------|
| `kode` | INT | Primary Key, Auto Increment |
| `kode_produk` | INT | Foreign Key → kategori.kode |
| `nama` | VARCHAR(100) | Nama produk (Not Null, UNIQUE) |
| `harga` | INT | Harga jual (Not Null) |
| `stock` | INT | Jumlah stok (Not Null) |

### Relasi

```
KATEGORI (1) ───── (N) PRODUK
  kode (PK) ────┐
                │
  kode_produk (FK) ────┘
```

- **Relasi**: Satu Kategori dapat memiliki BANYAK Produk (One-to-Many)
- **Foreign Key**: `produk.kode_produk` merujuk ke `kategori.kode`
- **JOIN**: Data produk ditampilkan lengkap dengan nama kategori dan brand

---

## 📥 Cara Instalasi

### 1. Persiapan Database

```bash
1. Buka XAMPP Control Panel
2. Start Apache & MySQL
3. Buka phpMyAdmin: http://localhost/phpmyadmin/
4. Buat database: db_toko
5. Import file db_toko.sql
```

Atau buat tabel secara manual:

```sql
-- Tabel Kategori
CREATE TABLE kategori (
    kode INT PRIMARY KEY AUTO_INCREMENT,
    kategori VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL
);

-- Tabel Produk
CREATE TABLE produk (
    kode INT PRIMARY KEY AUTO_INCREMENT,
    kode_produk INT,
    nama VARCHAR(100) NOT NULL UNIQUE,
    harga INT NOT NULL,
    stock INT NOT NULL,
    FOREIGN KEY (kode_produk) REFERENCES kategori(kode)
);
```

### 2. Buka Project di IDE

```bash
1. Buka NetBeans
2. File → Open Project
3. Arahkan ke folder project
```

### 3. Tambahkan Library MySQL Driver

```bash
1. Klik kanan Libraries → Add JAR/Folder
2. Tambahkan mysql-connector-java-xxx.jar
```

---

## 🚀 Cara Menjalankan

```bash
1. Buka GUI/FormProduk.java
2. Klik kanan → Run File (Shift + F6)
3. Jendela aplikasi muncul di layar
```

---

## 📁 Struktur Project

```
UASPBOSemester2/
├── src/
│   ├── ClassModel/
│   │   ├── Kategori.java
│   │   └── Produk.java
│   ├── Dao/
│   │   ├── KategoriDAO.java
│   │   └── ProdukDAO.java
│   ├── DBKoneksi/
│   │   └── DBKoneksi.java
│   └── GUI/
│       ├── DialogInputProduk.java
│       ├── FormKategori.java
│       └── FormProduk.java
├── db_toko.sql
└── README.md
```

---

## 🔍 Validasi pada Aplikasi

| No | Jenis Validasi | Lokasi | Tujuan |
|----|---------------|--------|--------|
| 1 | Field Kosong | DialogInputProduk, FormKategori | Mencegah input kosong |
| 2 | Tipe Data Angka | DialogInputProduk | Mencegah input bukan angka |
| 3 | Nilai Negatif | DialogInputProduk, FormProduk | Mencegah nilai negatif |
| 4 | Nilai Maksimum | DialogInputProduk, FormProduk | Mencegah nilai terlalu besar |
| 5 | Duplikat Insert | FormKategori | Mencegah data ganda |
| 6 | Duplikat Update | FormKategori | Mencegah data ganda (kecuali data sendiri) |
| 7 | Seleksi Delete | FormKategori, FormProduk | Pastikan ada baris dipilih |
| 8 | Seleksi Update | FormKategori, FormProduk | Pastikan ada baris dipilih |
| 9 | Konfirmasi Hapus | FormKategori, FormProduk | Mencegah hapus tidak sengaja |

---

## 🔗 Penanganan Foreign Key

Aplikasi mengecek ketergantungan sebelum menghapus kategori:

```sql
-- Cek apakah kategori masih dipakai produk
SELECT COUNT(*) FROM produk WHERE kode_produk = ?
```

**Alur Penanganan:**

1. User klik Delete pada kategori
2. Sistem ambil kode kategori
3. Sistem cek apakah ada produk yang menggunakan
4. **Jika ada** → tampilkan error, batalkan hapus
5. **Jika tidak ada** → tampilkan konfirmasi, lanjutkan hapus

---

## 📋 Model Class

### Kategori.java

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| `kode` | int | ID kategori |
| `kategori` | String | Nama kategori |
| `brand` | String | Merek/brand |

### Produk.java

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| `kode` | int | ID produk |
| `kodeProduk` | int | Foreign Key ke kategori |
| `nama` | String | Nama produk |
| `harga` | int | Harga jual |
| `stock` | int | Jumlah stok |
| `namaKategori` | String | Hasil JOIN (tidak di database) |
| `brand` | String | Hasil JOIN (tidak di database) |

---

## 🎯 Fitur Khusus

### Auto Refresh Data
Data di tabel otomatis diperbarui setelah Insert, Update, Delete, atau klik Load.

### Tidak Bisa Hapus Kategori yang Masih Dipakai
Sistem mengecek foreign key sebelum menghapus kategori untuk menjaga integritas data.

### Form Terintegrasi
Tombol "Kategori" di FormProduk membuka FormKategori. Data kategori otomatis muncul di combobox.

### Update dengan Dialog Internal
Dialog update muncul dengan data lama (pre-filled), user tinggal mengubah yang diperlukan.

---

## 👥 Kontributor

| Nama | NIM |
|------|-----|
| Muhammad Fachri Fairuz | 25104410043 |
| Fidlela Latifa Salsabila | 25104410059 |
| Raffi Ahmad Alfahrezy | 25104410050 |
| Mohamad Rifky Ramadani | 25104410065 |
| Fibi Dita Salsabilla | 25104410051 |

**Dosen:** Saiful Nur Budiman, S.Kom., M.Kom

---

## 📝 Catatan

- Aplikasi dibuat untuk memenuhi tugas UAS Pemrograman Berorientasi Objek
- Database: MySQL dengan nama `db_toko`
- Driver MySQL: `mysql-connector-java-8.0.xx.jar`

---

## 📄 Lisensi

Hak Cipta © 2026. Dibuat untuk keperluan pendidikan.

**Universitas Islam Balitar**
**Prodi Teknik Informatika**
**Fakultas Teknologi dan Informasi**
**Tahun 2026**
```
