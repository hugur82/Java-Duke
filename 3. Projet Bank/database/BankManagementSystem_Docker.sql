-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: bankmanagementsystem
-- ------------------------------------------------------
-- Server version	8.0.44

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

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `iban` char(34) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_number` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_type` enum('CHECKING','SAVINGS') COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ACTIVE','BLOCKED','CLOSED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `customer_id` bigint NOT NULL,
  `opening_balance` decimal(18,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `iban` (`iban`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `fk_account_customer` (`customer_id`),
  CONSTRAINT `fk_account_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'FR7630006000010000000000010037','ACCT-000001','CHECKING',1856.00,'2019-03-14 10:18:00','ACTIVE',1,0.00),(2,'FR7630006000010000000000020074','ACCT-000002','CHECKING',1962.00,'2020-08-22 14:42:00','ACTIVE',2,0.00),(3,'FR7630006000010000000000030111','ACCT-000003','SAVINGS',3597.00,'2018-11-05 09:07:00','ACTIVE',3,0.00),(4,'FR7630006000010000000000040148','ACCT-000004','CHECKING',1901.00,'2021-01-17 16:25:00','ACTIVE',4,0.00),(5,'FR7630006000010000000000050185','ACCT-000005','CHECKING',2133.00,'2022-06-09 11:53:00','ACTIVE',5,0.00),(6,'FR7630006000010000000000060222','ACCT-000006','CHECKING',2460.00,'2019-12-01 08:36:00','ACTIVE',6,0.00),(7,'FR7630006000010000000000070259','ACCT-000007','CHECKING',2342.00,'2023-04-28 15:14:00','ACTIVE',7,0.00),(8,'FR7630006000010000000000080296','ACCT-000008','CHECKING',2557.00,'2020-02-13 10:49:00','BLOCKED',8,0.00),(9,'FR7630006000010000000000090333','ACCT-000009','CHECKING',2723.00,'2024-07-20 13:21:00','ACTIVE',9,0.00),(10,'FR7630006000010000000000100370','ACCT-000010','CHECKING',2686.00,'2021-10-04 17:05:00','ACTIVE',10,0.00),(11,'FR7630006000010000000000110407','ACCT-000011','CHECKING',2229.00,'2018-05-30 09:33:00','ACTIVE',11,0.00),(13,'FR7630006000010000000000130481','ACCT-000013','CHECKING',2740.00,'2019-06-24 14:57:00','ACTIVE',12,0.00),(14,'FR7630006000010000000000140518','ACCT-000014','SAVINGS',420.00,'2023-11-07 10:26:00','ACTIVE',12,0.00),(15,'FR7630006000010000000000150555','ACCT-000015','CHECKING',0.00,'2020-04-11 16:11:00','BLOCKED',12,0.00),(16,'FR7630006000010000000000160592','ACCT-000016','CHECKING',2475.00,'2021-08-29 08:45:00','ACTIVE',13,0.00),(17,'FR7630006000010000000000170629','ACCT-000017','SAVINGS',430.00,'2024-01-18 11:19:00','ACTIVE',13,0.00),(18,'FR7630006000010000000000180666','ACCT-000018','CHECKING',2602.00,'2022-03-06 15:38:00','ACTIVE',14,0.00),(19,'FR7630006000010000000000190703','ACCT-000019','SAVINGS',440.00,'2018-12-15 09:54:00','CLOSED',14,0.00),(20,'FR7630006000010000000000200740','ACCT-000020','CHECKING',2949.00,'2023-05-02 13:47:00','ACTIVE',15,0.00),(21,'FR7630006000010000000000210777','ACCT-000021','SAVINGS',450.00,'2019-09-27 10:02:00','ACTIVE',15,0.00),(22,'FR7630006000010000000000220814','ACCT-000022','CHECKING',0.00,'2024-10-09 16:29:00','BLOCKED',15,0.00),(23,'FR7630006000010000000000230851','ACCT-000023','CHECKING',2831.00,'2020-01-25 08:17:00','ACTIVE',16,0.00),(24,'FR7630006000010000000000240888','ACCT-000024','SAVINGS',460.00,'2021-07-12 14:04:00','ACTIVE',16,0.00),(25,'FR7630006000010000000000250925','ACCT-000025','CHECKING',2895.00,'2022-11-21 11:42:00','ACTIVE',17,0.00),(26,'FR7630006000010000000000260962','ACCT-000026','SAVINGS',470.00,'2019-02-08 17:16:00','CLOSED',17,0.00),(27,'FR7630006000010000000000270999','ACCT-000027','CHECKING',3380.00,'2023-06-15 09:28:00','ACTIVE',18,0.00),(28,'FR7630006000010000000000281036','ACCT-000028','SAVINGS',480.00,'2020-09-03 13:55:00','ACTIVE',18,0.00),(29,'FR7630006000010000000000291073','ACCT-000029','CHECKING',0.00,'2024-04-26 10:37:00','CLOSED',18,0.00),(32,'FR7630006000010000000000321184','ACCT-000032','CHECKING',0.00,'2018-08-10 12:24:00','ACTIVE',20,0.00),(33,'FR7630006000010000000000331221','ACCT-000033','SAVINGS',0.00,'2023-09-14 16:03:00','BLOCKED',20,0.00),(35,'CH5100000007056579794','7056579794','SAVINGS',15000.00,'2026-10-01 17:35:20','BLOCKED',3,0.00),(36,'CH2400000001313178561','1313178561','CHECKING',5000.00,'2026-10-01 17:45:55','ACTIVE',3,0.00),(37,'CH2500000000208919388','0208919388','SAVINGS',0.00,'2026-10-01 17:51:46','BLOCKED',5,0.00);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_transaction`
--

DROP TABLE IF EXISTS `bank_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_transaction` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` decimal(18,2) NOT NULL,
  `transaction_type` enum('DEPOSIT','WITHDRAWAL') COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('CREATED','PROCESSING','ACCEPTED','REJECTED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CREATED',
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_transaction_account` (`account_id`),
  CONSTRAINT `fk_transaction_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `chk_transaction_amount` CHECK ((`amount` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_transaction`
--

LOCK TABLES `bank_transaction` WRITE;
/*!40000 ALTER TABLE `bank_transaction` DISABLE KEYS */;
INSERT INTO `bank_transaction` VALUES (1,'2026-02-01 10:00:00',2325.00,'DEPOSIT','Monthly salary','ACCEPTED',1),(2,'2026-02-02 10:00:00',662.00,'WITHDRAWAL','Rent','ACCEPTED',1),(3,'2026-02-03 10:00:00',46.00,'WITHDRAWAL','Utilities','ACCEPTED',1),(4,'2026-02-04 10:00:00',36.00,'WITHDRAWAL','Groceries','ACCEPTED',1),(5,'2026-02-05 10:00:00',2450.00,'DEPOSIT','Monthly salary','ACCEPTED',2),(6,'2026-02-06 10:00:00',674.00,'WITHDRAWAL','Rent','ACCEPTED',2),(7,'2026-02-07 10:00:00',47.00,'WITHDRAWAL','Utilities','ACCEPTED',2),(8,'2026-02-08 10:00:00',40.00,'WITHDRAWAL','Groceries','ACCEPTED',2),(9,'2026-02-09 10:00:00',130.00,'DEPOSIT','Cash deposit','ACCEPTED',2),(10,'2026-02-10 10:00:00',57.00,'WITHDRAWAL','Restaurant','ACCEPTED',2),(11,'2026-02-11 10:00:00',2575.00,'DEPOSIT','Monthly salary','ACCEPTED',3),(12,'2026-02-12 10:00:00',686.00,'WITHDRAWAL','Rent','ACCEPTED',3),(13,'2026-02-13 10:00:00',48.00,'WITHDRAWAL','Utilities','ACCEPTED',3),(14,'2026-02-14 10:00:00',44.00,'WITHDRAWAL','Groceries','ACCEPTED',3),(15,'2026-02-15 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',3),(16,'2026-02-16 10:00:00',2700.00,'DEPOSIT','Monthly salary','ACCEPTED',4),(17,'2026-02-17 10:00:00',698.00,'WITHDRAWAL','Rent','ACCEPTED',4),(18,'2026-02-18 10:00:00',49.00,'WITHDRAWAL','Utilities','ACCEPTED',4),(19,'2026-02-19 10:00:00',48.00,'WITHDRAWAL','Groceries','ACCEPTED',4),(20,'2026-02-20 10:00:00',140.00,'DEPOSIT','Cash deposit','ACCEPTED',4),(21,'2026-02-21 10:00:00',59.00,'WITHDRAWAL','Restaurant','ACCEPTED',4),(22,'2026-02-22 10:00:00',2825.00,'DEPOSIT','Monthly salary','ACCEPTED',5),(23,'2026-02-23 10:00:00',710.00,'WITHDRAWAL','Rent','ACCEPTED',5),(24,'2026-02-24 10:00:00',50.00,'WITHDRAWAL','Utilities','ACCEPTED',5),(25,'2026-02-25 10:00:00',52.00,'WITHDRAWAL','Groceries','ACCEPTED',5),(26,'2026-02-26 10:00:00',2950.00,'DEPOSIT','Monthly salary','ACCEPTED',6),(27,'2026-02-27 10:00:00',722.00,'WITHDRAWAL','Rent','ACCEPTED',6),(28,'2026-02-28 10:00:00',51.00,'WITHDRAWAL','Utilities','ACCEPTED',6),(29,'2026-03-01 10:00:00',56.00,'WITHDRAWAL','Groceries','ACCEPTED',6),(30,'2026-03-02 10:00:00',150.00,'DEPOSIT','Cash deposit','ACCEPTED',6),(31,'2026-03-03 10:00:00',61.00,'WITHDRAWAL','Restaurant','ACCEPTED',6),(32,'2026-03-04 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',6),(33,'2026-03-05 10:00:00',3075.00,'DEPOSIT','Monthly salary','ACCEPTED',7),(34,'2026-03-06 10:00:00',734.00,'WITHDRAWAL','Rent','ACCEPTED',7),(35,'2026-03-07 10:00:00',52.00,'WITHDRAWAL','Utilities','ACCEPTED',7),(36,'2026-03-08 10:00:00',32.00,'WITHDRAWAL','Groceries','ACCEPTED',7),(37,'2026-03-09 10:00:00',3200.00,'DEPOSIT','Monthly salary','ACCEPTED',8),(38,'2026-03-10 10:00:00',746.00,'WITHDRAWAL','Rent','ACCEPTED',8),(39,'2026-03-11 10:00:00',53.00,'WITHDRAWAL','Utilities','ACCEPTED',8),(40,'2026-03-12 10:00:00',36.00,'WITHDRAWAL','Groceries','ACCEPTED',8),(41,'2026-03-13 10:00:00',160.00,'DEPOSIT','Cash deposit','ACCEPTED',8),(42,'2026-03-14 10:00:00',63.00,'WITHDRAWAL','Restaurant','ACCEPTED',8),(43,'2026-03-15 10:00:00',3325.00,'DEPOSIT','Monthly salary','ACCEPTED',9),(44,'2026-03-16 10:00:00',758.00,'WITHDRAWAL','Rent','ACCEPTED',9),(45,'2026-03-17 10:00:00',54.00,'WITHDRAWAL','Utilities','ACCEPTED',9),(46,'2026-03-18 10:00:00',40.00,'WITHDRAWAL','Groceries','ACCEPTED',9),(47,'2026-03-19 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',9),(48,'2026-03-20 10:00:00',3450.00,'DEPOSIT','Monthly salary','ACCEPTED',10),(49,'2026-03-21 10:00:00',770.00,'WITHDRAWAL','Rent','ACCEPTED',10),(50,'2026-03-22 10:00:00',55.00,'WITHDRAWAL','Utilities','ACCEPTED',10),(51,'2026-03-23 10:00:00',44.00,'WITHDRAWAL','Groceries','ACCEPTED',10),(52,'2026-03-24 10:00:00',170.00,'DEPOSIT','Cash deposit','ACCEPTED',10),(53,'2026-03-25 10:00:00',65.00,'WITHDRAWAL','Restaurant','ACCEPTED',10),(54,'2026-03-26 10:00:00',3575.00,'DEPOSIT','Monthly salary','ACCEPTED',11),(55,'2026-03-27 10:00:00',782.00,'WITHDRAWAL','Rent','ACCEPTED',11),(56,'2026-03-28 10:00:00',56.00,'WITHDRAWAL','Utilities','ACCEPTED',11),(57,'2026-03-29 10:00:00',48.00,'WITHDRAWAL','Groceries','ACCEPTED',11),(58,'2026-03-30 10:00:00',410.00,'DEPOSIT','Transfer to savings','ACCEPTED',11),(59,'2026-03-31 10:00:00',3700.00,'DEPOSIT','Monthly salary','ACCEPTED',13),(60,'2026-04-01 10:00:00',794.00,'WITHDRAWAL','Rent','ACCEPTED',13),(61,'2026-04-02 10:00:00',57.00,'WITHDRAWAL','Utilities','ACCEPTED',13),(62,'2026-04-03 10:00:00',52.00,'WITHDRAWAL','Groceries','ACCEPTED',13),(63,'2026-04-04 10:00:00',180.00,'DEPOSIT','Cash deposit','ACCEPTED',13),(64,'2026-04-05 10:00:00',67.00,'WITHDRAWAL','Restaurant','ACCEPTED',13),(65,'2026-04-06 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',13),(66,'2026-04-07 10:00:00',420.00,'DEPOSIT','Transfer to savings','ACCEPTED',13),(67,'2026-04-08 10:00:00',3825.00,'DEPOSIT','Monthly salary','ACCEPTED',16),(68,'2026-04-09 10:00:00',806.00,'WITHDRAWAL','Rent','ACCEPTED',16),(69,'2026-04-10 10:00:00',58.00,'WITHDRAWAL','Utilities','ACCEPTED',16),(70,'2026-04-11 10:00:00',56.00,'WITHDRAWAL','Groceries','ACCEPTED',16),(71,'2026-04-12 10:00:00',430.00,'DEPOSIT','Transfer to savings','ACCEPTED',16),(72,'2026-04-13 10:00:00',3950.00,'DEPOSIT','Monthly salary','ACCEPTED',18),(73,'2026-04-14 10:00:00',818.00,'WITHDRAWAL','Rent','ACCEPTED',18),(74,'2026-04-15 10:00:00',59.00,'WITHDRAWAL','Utilities','ACCEPTED',18),(75,'2026-04-16 10:00:00',32.00,'WITHDRAWAL','Groceries','ACCEPTED',18),(76,'2026-04-17 10:00:00',190.00,'DEPOSIT','Cash deposit','ACCEPTED',18),(77,'2026-04-18 10:00:00',69.00,'WITHDRAWAL','Restaurant','ACCEPTED',18),(78,'2026-04-19 10:00:00',440.00,'DEPOSIT','Transfer to savings','ACCEPTED',18),(79,'2026-04-20 10:00:00',4075.00,'DEPOSIT','Monthly salary','ACCEPTED',20),(80,'2026-04-21 10:00:00',830.00,'WITHDRAWAL','Rent','ACCEPTED',20),(81,'2026-04-22 10:00:00',60.00,'WITHDRAWAL','Utilities','ACCEPTED',20),(82,'2026-04-23 10:00:00',36.00,'WITHDRAWAL','Groceries','ACCEPTED',20),(83,'2026-04-24 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',20),(84,'2026-04-25 10:00:00',450.00,'DEPOSIT','Transfer to savings','ACCEPTED',20),(85,'2026-04-26 10:00:00',4200.00,'DEPOSIT','Monthly salary','ACCEPTED',23),(86,'2026-04-27 10:00:00',842.00,'WITHDRAWAL','Rent','ACCEPTED',23),(87,'2026-04-28 10:00:00',61.00,'WITHDRAWAL','Utilities','ACCEPTED',23),(88,'2026-04-29 10:00:00',40.00,'WITHDRAWAL','Groceries','ACCEPTED',23),(89,'2026-04-30 10:00:00',200.00,'DEPOSIT','Cash deposit','ACCEPTED',23),(90,'2026-05-01 10:00:00',71.00,'WITHDRAWAL','Restaurant','ACCEPTED',23),(91,'2026-05-02 10:00:00',460.00,'DEPOSIT','Transfer to savings','ACCEPTED',23),(92,'2026-05-03 10:00:00',4325.00,'DEPOSIT','Monthly salary','ACCEPTED',25),(93,'2026-05-04 10:00:00',854.00,'WITHDRAWAL','Rent','ACCEPTED',25),(94,'2026-05-05 10:00:00',62.00,'WITHDRAWAL','Utilities','ACCEPTED',25),(95,'2026-05-06 10:00:00',44.00,'WITHDRAWAL','Groceries','ACCEPTED',25),(96,'2026-05-07 10:00:00',470.00,'DEPOSIT','Transfer to savings','ACCEPTED',25),(97,'2026-05-08 10:00:00',4450.00,'DEPOSIT','Monthly salary','ACCEPTED',27),(98,'2026-05-09 10:00:00',866.00,'WITHDRAWAL','Rent','ACCEPTED',27),(99,'2026-05-10 10:00:00',63.00,'WITHDRAWAL','Utilities','ACCEPTED',27),(100,'2026-05-11 10:00:00',48.00,'WITHDRAWAL','Groceries','ACCEPTED',27),(101,'2026-05-12 10:00:00',210.00,'DEPOSIT','Cash deposit','ACCEPTED',27),(102,'2026-05-13 10:00:00',73.00,'WITHDRAWAL','Restaurant','ACCEPTED',27),(103,'2026-05-14 10:00:00',250.00,'DEPOSIT','Freelance income','ACCEPTED',27),(104,'2026-05-15 10:00:00',480.00,'DEPOSIT','Transfer to savings','ACCEPTED',27),(105,'2026-05-16 10:00:00',150.00,'DEPOSIT','Dinner reimbursement','ACCEPTED',1),(106,'2026-05-17 10:00:00',85.00,'DEPOSIT','Shared expenses','ACCEPTED',4),(107,'2026-05-18 10:00:00',200.00,'DEPOSIT','Family transfer','ACCEPTED',11),(108,'2026-05-19 10:00:00',120.00,'DEPOSIT','Gift','ACCEPTED',18),(109,'2026-05-20 10:00:00',95.00,'DEPOSIT','Shared purchase','ACCEPTED',23),(110,'2026-09-14 17:43:47',75.00,'WITHDRAWAL','Retrait test modifié','ACCEPTED',1),(111,'2026-09-14 18:12:52',2000.00,'WITHDRAWAL','Retrait supérieur au solde','REJECTED',1),(112,'2026-09-14 18:16:22',500.00,'DEPOSIT','Dépôt test','ACCEPTED',1),(113,'2026-09-14 18:20:41',100.00,'DEPOSIT','Test compte bloqué','REJECTED',33),(114,'2026-09-22 00:00:00',300.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',1),(115,'2026-09-22 00:00:00',200.00,'DEPOSIT','Balance adjustment','ACCEPTED',2),(116,'2026-09-22 00:00:00',170.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',4),(117,'2026-09-22 00:00:00',120.00,'DEPOSIT','Balance adjustment','ACCEPTED',5),(118,'2026-09-22 00:00:00',85.00,'DEPOSIT','Balance adjustment','ACCEPTED',7),(119,'2026-09-22 00:00:00',95.00,'DEPOSIT','Balance adjustment','ACCEPTED',8),(120,'2026-09-22 00:00:00',1070.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',11),(121,'2026-09-22 00:00:00',840.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',13),(122,'2026-09-22 00:00:00',420.00,'DEPOSIT','Balance adjustment','ACCEPTED',14),(123,'2026-09-22 00:00:00',860.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',16),(124,'2026-09-22 00:00:00',430.00,'DEPOSIT','Balance adjustment','ACCEPTED',17),(125,'2026-09-22 00:00:00',1120.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',18),(126,'2026-09-22 00:00:00',440.00,'DEPOSIT','Balance adjustment','ACCEPTED',19),(127,'2026-09-22 00:00:00',900.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',20),(128,'2026-09-22 00:00:00',450.00,'DEPOSIT','Balance adjustment','ACCEPTED',21),(129,'2026-09-22 00:00:00',1110.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',23),(130,'2026-09-22 00:00:00',460.00,'DEPOSIT','Balance adjustment','ACCEPTED',24),(131,'2026-09-22 00:00:00',940.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',25),(132,'2026-09-22 00:00:00',470.00,'DEPOSIT','Balance adjustment','ACCEPTED',26),(133,'2026-09-22 00:00:00',960.00,'WITHDRAWAL','Balance adjustment','ACCEPTED',27),(134,'2026-09-22 00:00:00',480.00,'DEPOSIT','Balance adjustment','ACCEPTED',28),(145,'2026-10-01 17:35:50',15000.00,'DEPOSIT','papa noel','ACCEPTED',35),(146,'2026-10-01 17:36:20',1500.00,'DEPOSIT','papa noel','ACCEPTED',3),(147,'2026-10-01 17:37:07',50.00,'DEPOSIT','maman','ACCEPTED',3),(148,'2026-10-01 17:46:21',5000.00,'DEPOSIT','maman','ACCEPTED',36),(149,'2026-10-01 17:47:01',5000000.00,'WITHDRAWAL','try it','REJECTED',36),(150,'2026-10-01 17:52:27',500.00,'DEPOSIT','test','REJECTED',37);
/*!40000 ALTER TABLE `bank_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `postal_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','SUSPENDED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Emma','Martin','1981-02-02','+33 6 10 20 01 01','emma.martin@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','8 Rue des Veaux','67000','Strasbourg','ACTIVE'),(2,'Jean 2','Dupont Modifié','1985-05-15','+33 6 99 88 77 66','jean.dupont@example.com','test456','21 Quai des Bateliers','67000','Strasbourg','ACTIVE'),(3,'Chloe','Robert','1983-04-04','+33 6 10 20 03 03','chloe.robert@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','4 Place Saint-Etienne','67000','Strasbourg','ACTIVE'),(4,'Thomas','Richard','1984-05-05','+33 6 10 20 04 04','thomas.richard@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','17 Rue du Bain-aux-Plantes','67000','Strasbourg','ACTIVE'),(5,'Lea','Petit','1985-06-06','+33 6 10 20 05 05','lea.petit@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','42 Rue Oberkampf','75011','Paris','ACTIVE'),(6,'Hugo','Durand','1986-07-07','+33 6 10 20 06 06','hugo.durand@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','18 Boulevard Voltaire','75011','Paris','ACTIVE'),(7,'Camille2','lestroy','1978-02-01','+33 6 11111111','camille.leroy@example2.com','1','9 Rue de char,mmmmils','67000','strasbourg','ACTIVE'),(8,'Louis','Moreau','1988-09-09','+33 6 10 20 08 08','louis.moreau@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','27 Avenue de Clichy','75017','Paris','ACTIVE'),(9,'Manon','Simon','1989-10-10','+33 6 10 20 09 09','manon.simon@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','16 Rue des Charmettes','69003','Lyon','ACTIVE'),(10,'Gabriel','Laurent','1990-11-11','+33 6 10 20 10 10','gabriel.laurent@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','5 Cours Julien','13006','Marseille','ACTIVE'),(11,'Sarah','Lefevre','1991-12-12','+33 6 10 20 11 11','sarah.lefevre@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','14 Rue des Remparts','33000','Bordeaux','ACTIVE'),(12,'Antoine','Michel','1992-01-13','+33 6 10 20 12 12','antoine.michel@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','32 Rue d\'Alsace-Lorraine','31000','Toulouse','ACTIVE'),(13,'Julie','Garcia','1993-02-14','+33 6 10 20 13 13','julie.garcia@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','7 Rue de la Paix','44000','Nantes','ACTIVE'),(14,'Nathan','David','1994-03-15','+33 6 10 20 14 14','nathan.david@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','23 Rue Nationale','59800','Lille','ACTIVE'),(15,'Sophie','Bertrand','1995-04-16','+33 6 10 20 15 15','sophie.bertrand@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','11 Avenue Malaussena','06000','Nice','ACTIVE'),(16,'Maxime','Roux','1996-05-17','+33 6 10 20 16 16','maxime.roux@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','6 Rue Saint-Georges','35000','Rennes','ACTIVE'),(17,'Laura','Vincent','1980-06-18','+33 6 10 20 17 17','laura.vincent@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','28 Rue de la Loge','34000','Montpellier','ACTIVE'),(18,'Paul','Fournier','1981-07-19','+33 6 10 20 18 18','paul.fournier@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','19 Boulevard Gambetta','38000','Grenoble','ACTIVE'),(20,'Alexandre','Bonnet','1983-09-21','+33 6 10 20 20 20','alexandre.bonnet@example.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','15 Rue de Vesle','51100','Reims','ACTIVE'),(24,'sam','gamji','1877-09-10','0388455669','email.test@test.fr','123456789','15 rue princupale de monaco','13000','marseille','ACTIVE'),(25,'manill','pacone','1980-09-11','9999999','test.k@test','dfgfdgfdg','15 impasse de la way carlito','15000','polo','ACTIVE'),(26,'jean','clode','1999-01-15','888888888888','septapoi@mail.com','$2a$10$YW0esVoeZUHPHbPg5PxFOOuvHuCOKHomaAr0LVmrWg2jI1RYXZE/C','14 rue des lila','67540','ostwald','ACTIVE'),(27,'firdstnqme ','lastname','1950-02-01','556655445566','56email@gmail.ch','$2a$10$ZHmvkaMghQqv0S.VDfT/nOeQs2.h0SgEzgciO6RPhGIoYrF50Dj5W','123456 rue de la rose','011111','50 - 1','ACTIVE');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `employee_role` enum('ADMIN','EMPLOYEE') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('ACTIVE','DISABLED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_at` datetime DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Admin','Admin','admin@admin.com','$2a$10$UQlpmysoi3QrD/tSoIeOD.dePVc58ZJqoAfXDpPR6SOcbcA090Yru','ADMIN','ACTIVE','2026-10-01 19:32:04',NULL),(8,'mongo','db','mongodb@gmail.com','$2a$10$ojQVmzE4l2e/o.4aqgxYB.Wcv3BNNGidi6xmiOnuJ3pF9YmwlOgUy','EMPLOYEE','ACTIVE','2026-10-05 20:20:33',NULL),(9,'test3','test25','test@test.com','$2a$10$MslerSC4l/e2P70HNTVDceq7fqJKj2rYg7AYYdQC2wA6vWZrRLQlm','EMPLOYEE','ACTIVE','2026-10-05 20:26:21',NULL),(18,'frederic','PETIT','fredlegrand@gmail.com','$2a$10$QNhFBr0Nz7x6C6hhkNm7MO/CGuHmVednOMvWVqlNGsB8r6Kj3ItI2','EMPLOYEE','DISABLED','2026-10-08 09:57:11',NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-08 21:03:04
