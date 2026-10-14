-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 14, 2026 at 06:42 AM
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
-- Database: `rumah_subsidi`
--

-- --------------------------------------------------------

--
-- Table structure for table `dokumen`
--

CREATE TABLE `dokumen` (
  `id_dokumen` varchar(10) NOT NULL,
  `nik_pembeli` varchar(16) NOT NULL,
  `nama_dokumen` varchar(100) NOT NULL DEFAULT 'KTP',
  `status_verifikasi` varchar(30) NOT NULL DEFAULT 'Belum Terverifikasi'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dokumen`
--

INSERT INTO `dokumen` (`id_dokumen`, `nik_pembeli`, `nama_dokumen`, `status_verifikasi`) VALUES
('D001', '6472010101010001', 'KTP', 'Terverifikasi'),
('D002', '6472010505050005', 'KTP', 'Terverifikasi'),
('D003', '1234567891011121', 'KTP', 'Terverifikasi');

-- --------------------------------------------------------

--
-- Table structure for table `pembeli`
--

CREATE TABLE `pembeli` (
  `nik` varchar(16) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `penghasilan` decimal(15,2) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `status_punya_rumah` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembeli`
--

INSERT INTO `pembeli` (`nik`, `nama`, `penghasilan`, `no_hp`, `status_punya_rumah`) VALUES
('1234567891011121', 'Ayu', 10000000.00, '085629876623', 'Tidak'),
('6472010101010001', 'Andi Pratama', 5000000.00, '089786576613', 'Tidak'),
('6472010505050005', 'Rizky Saputra', 4500000.00, '081234567805', 'Ya');

-- --------------------------------------------------------

--
-- Table structure for table `pengajuan`
--

CREATE TABLE `pengajuan` (
  `id_pengajuan` varchar(10) NOT NULL,
  `nik_pembeli` varchar(16) NOT NULL,
  `id_rumah` varchar(10) NOT NULL,
  `status_pengajuan` varchar(30) NOT NULL DEFAULT 'Menunggu Verifikasi',
  `metode_pembayaran` varchar(20) NOT NULL DEFAULT 'Belum Ada',
  `jumlah_bayar` decimal(15,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengajuan`
--

INSERT INTO `pengajuan` (`id_pengajuan`, `nik_pembeli`, `id_rumah`, `status_pengajuan`, `metode_pembayaran`, `jumlah_bayar`) VALUES
('P001', '6472010101010001', 'R001', 'Disetujui', 'Cicilan', 1050000.00),
('P002', '6472010505050005', 'R002', 'Ditolak', 'Belum Ada', 0.00),
('P003', '1234567891011121', 'R001', 'Disetujui', 'Cicilan', 3000000.00);

-- --------------------------------------------------------

--
-- Table structure for table `rumah`
--

CREATE TABLE `rumah` (
  `id_rumah` varchar(10) NOT NULL,
  `unit` varchar(20) NOT NULL,
  `tipe_rumah` varchar(30) NOT NULL,
  `harga` decimal(15,2) NOT NULL,
  `jumlah_unit` int(11) NOT NULL,
  `blok_rumah` varchar(50) NOT NULL,
  `status_rumah` varchar(20) NOT NULL DEFAULT 'Tersedia',
  `tanggal_jual` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rumah`
--

INSERT INTO `rumah` (`id_rumah`, `unit`, `tipe_rumah`, `harga`, `jumlah_unit`, `blok_rumah`, `status_rumah`, `tanggal_jual`) VALUES
('R001', 'A01', 'Tipe 36', 150000000.00, 8, 'Blok Lily', 'Terjual', '2026-07-13'),
('R002', 'A02', 'Tipe 36', 160000000.00, 5, 'Blok Mawar', 'Terjual', '2025-07-13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dokumen`
--
ALTER TABLE `dokumen`
  ADD PRIMARY KEY (`id_dokumen`),
  ADD KEY `nik_pembeli` (`nik_pembeli`);

--
-- Indexes for table `pembeli`
--
ALTER TABLE `pembeli`
  ADD PRIMARY KEY (`nik`);

--
-- Indexes for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD PRIMARY KEY (`id_pengajuan`),
  ADD KEY `nik_pembeli` (`nik_pembeli`),
  ADD KEY `id_rumah` (`id_rumah`);

--
-- Indexes for table `rumah`
--
ALTER TABLE `rumah`
  ADD PRIMARY KEY (`id_rumah`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dokumen`
--
ALTER TABLE `dokumen`
  ADD CONSTRAINT `dokumen_ibfk_1` FOREIGN KEY (`nik_pembeli`) REFERENCES `pembeli` (`nik`) ON UPDATE CASCADE;

--
-- Constraints for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD CONSTRAINT `pengajuan_ibfk_1` FOREIGN KEY (`nik_pembeli`) REFERENCES `pembeli` (`nik`) ON UPDATE CASCADE,
  ADD CONSTRAINT `pengajuan_ibfk_2` FOREIGN KEY (`id_rumah`) REFERENCES `rumah` (`id_rumah`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
