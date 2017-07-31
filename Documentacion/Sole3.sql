-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: sole3
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `contribuyente`
--

LOCK TABLES `contribuyente` WRITE;
/*!40000 ALTER TABLE `contribuyente` DISABLE KEYS */;
/*!40000 ALTER TABLE `contribuyente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `deduccion`
--

LOCK TABLES `deduccion` WRITE;
/*!40000 ALTER TABLE `deduccion` DISABLE KEYS */;
INSERT INTO `deduccion` VALUES (1,'002','001','ISR'),(2,'001','012','Seguridad social');
/*!40000 ALTER TABLE `deduccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `deuccion_percepcion`
--

LOCK TABLES `deuccion_percepcion` WRITE;
/*!40000 ALTER TABLE `deuccion_percepcion` DISABLE KEYS */;
/*!40000 ALTER TABLE `deuccion_percepcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `percepcion`
--

LOCK TABLES `percepcion` WRITE;
/*!40000 ALTER TABLE `percepcion` DISABLE KEYS */;
INSERT INTO `percepcion` VALUES (1,'001','001','Sueldos, Salarios Rayas y Jornales'),(2,'049','014','Premios de asistencia'),(3,'010','013','Premios de puntualidad');
/*!40000 ALTER TABLE `percepcion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-31 14:31:29
