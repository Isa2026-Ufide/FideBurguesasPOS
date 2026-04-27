-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fideburguesas_pos
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '986099f6-3864-11f1-a458-5cff35da1502:1-94';

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturas` (
  `id` varchar(20) NOT NULL,
  `orden_id` varchar(10) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `impuesto` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `metodo_pago` varchar(20) DEFAULT NULL,
  `monto_pagado` double DEFAULT NULL,
  `vuelto` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orden_id` (`orden_id`),
  CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`orden_id`) REFERENCES `ordenes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
INSERT INTO `facturas` VALUES ('0006','6',5500,715,6215,'EFECTIVO',7000,785);
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineas_orden`
--

DROP TABLE IF EXISTS `lineas_orden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lineas_orden` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orden_id` varchar(10) DEFAULT NULL,
  `producto_id` varchar(10) DEFAULT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` double NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orden_id` (`orden_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `lineas_orden_ibfk_1` FOREIGN KEY (`orden_id`) REFERENCES `ordenes` (`id`),
  CONSTRAINT `lineas_orden_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineas_orden`
--

LOCK TABLES `lineas_orden` WRITE;
/*!40000 ALTER TABLE `lineas_orden` DISABLE KEYS */;
INSERT INTO `lineas_orden` VALUES (1,'2',NULL,1,1800,'Nuggets',1800),(2,'3',NULL,1,2500,'Burger Clásica',2500),(3,'4',NULL,3,1200,'Papas Medianas',3600),(4,'5',NULL,1,2500,'Burger Clásica',2500),(5,'6',NULL,1,2500,'Burger Clásica',2500),(6,'6',NULL,1,1200,'Papas Medianas',1200),(7,'6',NULL,1,1800,'Nuggets',1800);
/*!40000 ALTER TABLE `lineas_orden` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes` (
  `id` varchar(10) NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `cajero_id` varchar(10) DEFAULT NULL,
  `sucursal_id` varchar(10) DEFAULT NULL,
  `estado` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cajero_id` (`cajero_id`),
  KEY `sucursal_id` (`sucursal_id`),
  CONSTRAINT `ordenes_ibfk_1` FOREIGN KEY (`cajero_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `ordenes_ibfk_2` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursales` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes`
--

LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;
INSERT INTO `ordenes` VALUES ('1','2026-04-27 09:44:36',NULL,'1','LISTA'),('2','2026-04-27 12:19:52',NULL,'2','PENDIENTE'),('3','2026-04-27 12:22:51',NULL,'2','EN_PREPARACION'),('4','2026-04-27 12:53:39',NULL,'1','PENDIENTE'),('5','2026-04-27 14:30:37',NULL,'1','PENDIENTE'),('6','2026-04-27 14:48:38',NULL,'1','FACTURADA');
/*!40000 ALTER TABLE `ordenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` double NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES ('P1','Burger Clásica',2500,'Hamburguesas',1),('P2','Papas Medianas',1200,'Acompañamientos',1),('P3','Nuggets',1800,'Acompañamientos',1);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursales`
--

DROP TABLE IF EXISTS `sucursales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sucursales` (
  `id` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursales`
--

LOCK TABLES `sucursales` WRITE;
/*!40000 ALTER TABLE `sucursales` DISABLE KEYS */;
INSERT INTO `sucursales` VALUES ('1','San Pedro','San José'),('2','Curridabat','San José');
/*!40000 ALTER TABLE `sucursales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(256) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `sucursal_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `sucursal_id` (`sucursal_id`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursales` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('A1','Administrador','admin','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','ADMIN',1,NULL),('C1','Cajero San Pedro','isa.o','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','CAJERO',1,'1'),('C2','Cajero Curridabat','isa.c','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','CAJERO',1,'2'),('K1','Cocina','cocina','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','COCINA',1,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-27 15:58:26
