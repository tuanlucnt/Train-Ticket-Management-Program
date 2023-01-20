-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: quanlyvetau
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vetau`
--

DROP TABLE IF EXISTS `vetau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vetau` (
  `MaVeTau` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `TenChuyenTau` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GaDi` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GaDen` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NgayDi` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Toa` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LoaiCho` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LoaiVe` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GiaVe` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CMND` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaVeTau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vetau`
--

LOCK TABLES `vetau` WRITE;
/*!40000 ALTER TABLE `vetau` DISABLE KEYS */;
INSERT INTO `vetau` VALUES ('A1','DN_HCM','Đà Nẵng','Hồ Chí Minh','2020-12-28','4','Điều Hòa','Thường ','2000000',123456789);
/*!40000 ALTER TABLE `vetau` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-20 22:10:22
