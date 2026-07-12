-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 12, 2026 at 11:19 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_toko`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `kode` int(11) NOT NULL,
  `kategori` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`kode`, `kategori`, `brand`) VALUES
(1, 'Minuman', 'Coca Cola'),
(2, 'Minuman', 'Ultramilk'),
(3, 'Minuman', 'Pepsi'),
(4, 'Minuman', 'Teh Botol Sosro'),
(5, 'Minuman', 'Aqua'),
(6, 'Minuman', 'Fruit Tea'),
(7, 'Minuman', 'Good Day'),
(8, 'Minuman', 'Nutrisari'),
(9, 'Makanan Ringan', 'Indomie'),
(10, 'Makanan Ringan', 'Chitato'),
(11, 'Makanan Ringan', 'Cheetos'),
(12, 'Makanan Ringan', 'Taro'),
(13, 'Makanan Ringan', 'Pringles'),
(14, 'Makanan Ringan', 'Lays'),
(15, 'Snack', 'Oreo'),
(16, 'Snack', 'Cadbury'),
(17, 'Snack', 'Silverqueen'),
(18, 'Susu', 'Dancow'),
(19, 'Susu', 'Indomilk'),
(20, 'Susu', 'Bear Brand'),
(21, 'Rokok', 'Sampoerna'),
(22, 'Rokok', 'Djarum'),
(23, 'Rokok', 'Gudang Garam'),
(24, 'Perawatan', 'Pepsodent'),
(25, 'Perawatan', 'Lifebuoy'),
(26, 'Perawatan', 'Pantene'),
(27, 'Minuman', 'Mizone'),
(28, 'Minuman', 'Pocari Sweat'),
(29, 'Makanan', 'Roti Aoka'),
(30, 'Minuman', 'Coca Cola'),
(31, 'Minuman', 'Ultramilk'),
(32, 'Minuman', 'Coca Cola'),
(33, 'Minuman', 'Ultramilk'),
(34, 'Minuman', 'Pepsi'),
(35, 'Minuman', 'Teh Botol Sosro'),
(36, 'Minuman', 'Aqua'),
(37, 'Minuman', 'Fruit Tea'),
(38, 'Minuman', 'Good Day'),
(39, 'Minuman', 'Nutrisari'),
(40, 'Makanan Ringan', 'Indomie'),
(41, 'Makanan Ringan', 'Chitato'),
(42, 'Makanan Ringan', 'Cheetos'),
(43, 'Makanan Ringan', 'Taro'),
(44, 'Makanan Ringan', 'Pringles'),
(45, 'Makanan Ringan', 'Lays'),
(46, 'Snack', 'Oreo'),
(47, 'Snack', 'Cadbury'),
(48, 'Snack', 'Silverqueen'),
(49, 'Susu', 'Dancow'),
(50, 'Susu', 'Indomilk'),
(51, 'Susu', 'Bear Brand'),
(52, 'Rokok', 'Sampoerna'),
(53, 'Rokok', 'Djarum'),
(54, 'Rokok', 'Gudang Garam'),
(55, 'Perawatan', 'Pepsodent'),
(56, 'Perawatan', 'Lifebuoy'),
(57, 'Perawatan', 'Pantene'),
(58, 'Minuman', 'Mizone'),
(59, 'Minuman', 'Pocari Sweat'),
(60, 'Makanan', 'Roti Aoka');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `kode` int(11) NOT NULL,
  `kode_produk` int(11) DEFAULT NULL,
  `nama` varchar(100) NOT NULL,
  `harga` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`kode`, `kode_produk`, `nama`, `harga`, `stock`) VALUES
(100, 32, 'Coca Cola 330ml', 7500, 150),
(101, 32, 'Coca Cola 1.5L', 15000, 80),
(102, 32, 'Coca Cola Zero 330ml', 8000, 100),
(103, 33, 'Ultramilk UHT 250ml', 5500, 200),
(104, 33, 'Ultramilk UHT 1L', 18000, 60),
(105, 34, 'Pepsi 330ml', 7000, 120),
(106, 34, 'Pepsi 1.5L', 14000, 70),
(107, 35, 'Teh Botol Sosro 350ml', 6000, 180),
(108, 35, 'Teh Botol Sosro 1.5L', 13000, 90),
(109, 36, 'Aqua 600ml', 4000, 250),
(110, 36, 'Aqua 1.5L', 7500, 150),
(111, 36, 'Aqua 19L Galon', 20000, 30),
(112, 37, 'Fruit Tea 350ml', 5500, 160),
(113, 38, 'Good Day Cappucino 200ml', 4500, 140),
(114, 39, 'Nutrisari Jeruk 200ml', 4000, 130),
(115, 40, 'Indomie Goreng', 3500, 300),
(116, 40, 'Indomie Kari Ayam', 3500, 280),
(117, 40, 'Indomie Soto Mie', 3500, 260),
(118, 40, 'Indomie Mi Goreng Jumbo', 5000, 150),
(119, 41, 'Chitato Klasik 68g', 8500, 100),
(120, 41, 'Chitato Balado 68g', 8500, 90),
(121, 41, 'Chitato Keju 68g', 8500, 95),
(122, 42, 'Cheetos Keju 45g', 6000, 110),
(123, 42, 'Cheetos Pedas 45g', 6000, 105),
(124, 43, 'Taro Net 45g', 5500, 120),
(125, 44, 'Pringles Original 107g', 15000, 50),
(126, 44, 'Pringles Sour Cream 107g', 15500, 45),
(127, 45, 'Lays Original 75g', 12000, 80),
(128, 45, 'Lays BBQ 75g', 12000, 75),
(129, 46, 'Oreo Klasik 137g', 11000, 140),
(130, 46, 'Oreo Strawberry 137g', 11500, 130),
(131, 47, 'Cadbury Dairy Milk 100g', 15000, 60),
(132, 48, 'Silverqueen 60g', 12000, 70),
(133, 48, 'Silverqueen 120g', 22000, 40),
(134, 49, 'Dancow 1+ 400g', 30000, 50),
(135, 49, 'Dancow 3+ 400g', 30000, 45),
(136, 50, 'Indomilk UHT 250ml', 5000, 200),
(137, 51, 'Bear Brand 300ml', 25000, 30),
(138, 52, 'Sampoerna Mild 20', 25000, 100),
(139, 52, 'Sampoerna Kretek 12', 18000, 90),
(140, 53, 'Djarum Super 16', 22000, 85),
(141, 53, 'Djarum Black 16', 24000, 80),
(142, 54, 'Gudang Garam Surya 12', 20000, 95),
(143, 54, 'Gudang Garam Signature 16', 23000, 75),
(144, 55, 'Pepsodent Pasta Gigi 100g', 12000, 80),
(145, 55, 'Pepsodent Pasta Gigi 190g', 20000, 60),
(146, 56, 'Lifebuoy Sabun Mandi 90g', 5000, 150),
(147, 56, 'Lifebuoy Sabun Cair 450ml', 25000, 40),
(148, 57, 'Pantene Sampo 300ml', 30000, 50),
(149, 57, 'Pantene Conditioner 300ml', 30000, 45),
(150, 58, 'Mizone 600ml', 7000, 100),
(151, 59, 'Pocari Sweat 350ml', 8000, 90),
(152, 59, 'Pocari Sweat 1.5L', 16000, 50),
(153, 60, 'Roti Aoka Coklat', 4500, 120),
(154, 60, 'Roti Aoka Strawberry', 4500, 115);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`kode`),
  ADD UNIQUE KEY `nama` (`nama`),
  ADD KEY `kode_produk` (`kode_produk`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `kode` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `kode` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `produk`
--
ALTER TABLE `produk`
  ADD CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`kode_produk`) REFERENCES `kategori` (`kode`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
