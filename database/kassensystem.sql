-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server-Version:               10.4.28-MariaDB - mariadb.org binary distribution
-- Server-Betriebssystem:        Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Exportiere Datenbank-Struktur für kassensystem
CREATE DATABASE IF NOT EXISTS `kassensystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `kassensystem`;

-- Exportiere Struktur von Tabelle kassensystem.einkaeufe
CREATE TABLE IF NOT EXISTS `einkaeufe` (
  `einkaufsnr` int(11) NOT NULL,
  `produktnr` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `anzahl` int(11) DEFAULT NULL,
<<<<<<< HEAD
  `bezahlt` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`einkaufsnummer`)
=======
  PRIMARY KEY (`einkaufsnr`) USING BTREE,
  KEY `produktnr` (`produktnr`),
  CONSTRAINT `produktnr` FOREIGN KEY (`produktnr`) REFERENCES `produkt` (`produktnr`) ON DELETE NO ACTION ON UPDATE CASCADE
>>>>>>> 341a057 (datenbank jetzt actually)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle kassensystem.einkaeufe: ~1 rows (ungefähr)
DELETE FROM `einkaeufe`;
INSERT INTO `einkaeufe` (`einkaufsnr`, `produktnr`, `timestamp`, `anzahl`) VALUES
	(1, 1, '2025-10-21 08:51:49', 4);

-- Exportiere Struktur von Tabelle kassensystem.produkt
CREATE TABLE IF NOT EXISTS `produkt` (
  `produktnr` int(11) NOT NULL,
  `bezeichnung` varchar(100) NOT NULL DEFAULT '0',
  `verkaufspreis` double NOT NULL DEFAULT 0,
  `bestand` double DEFAULT NULL,
  PRIMARY KEY (`produktnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle kassensystem.produkt: ~1 rows (ungefähr)
DELETE FROM `produkt`;
INSERT INTO `produkt` (`produktnr`, `bezeichnung`, `verkaufspreis`, `bestand`) VALUES
	(1, 'test_produkt', 2, 69);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
