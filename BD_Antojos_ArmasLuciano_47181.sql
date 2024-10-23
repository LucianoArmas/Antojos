-- MySQL dump 10.13  Distrib 8.0.15-5, for Linux (x86_64)
--
-- Host: localhost    Database: b98t1weohq60v7wxm6wz
-- ------------------------------------------------------
-- Server version	8.0.15-5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*!50717 SELECT COUNT(*) INTO @rocksdb_has_p_s_session_variables FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'performance_schema' AND TABLE_NAME = 'session_variables' */;
/*!50717 SET @rocksdb_get_is_supported = IF (@rocksdb_has_p_s_session_variables, 'SELECT COUNT(*) INTO @rocksdb_is_supported FROM performance_schema.session_variables WHERE VARIABLE_NAME=\'rocksdb_bulk_load\'', 'SELECT 0') */;
/*!50717 PREPARE s FROM @rocksdb_get_is_supported */;
/*!50717 EXECUTE s */;
/*!50717 DEALLOCATE PREPARE s */;
/*!50717 SET @rocksdb_enable_bulk_load = IF (@rocksdb_is_supported, 'SET SESSION rocksdb_bulk_load = 1', 'SET @rocksdb_dummy_bulk_load = 0') */;
/*!50717 PREPARE s FROM @rocksdb_enable_bulk_load */;
/*!50717 EXECUTE s */;
/*!50717 DEALLOCATE PREPARE s */;

--
-- Table structure for table `direction`
--

DROP TABLE IF EXISTS `direction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `direction` (
  `number` int(11) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `user_dni` varchar(255) NOT NULL,
  PRIMARY KEY (`number`,`postal_code`,`street`,`user_dni`),
  KEY `FKsxvow4mn4k6tdlw0ifwkyjljo` (`user_dni`),
  CONSTRAINT `FKsxvow4mn4k6tdlw0ifwkyjljo` FOREIGN KEY (`user_dni`) REFERENCES `user` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direction`
--

LOCK TABLES `direction` WRITE;
/*!40000 ALTER TABLE `direction` DISABLE KEYS */;
/*!40000 ALTER TABLE `direction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_entity`
--

DROP TABLE IF EXISTS `order_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_entity` (
  `cod` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tot_price` double DEFAULT NULL,
  `user_dni` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod`),
  KEY `FKbmn24k78chc5t2xdo1bxc9o2n` (`user_dni`),
  CONSTRAINT `FKbmn24k78chc5t2xdo1bxc9o2n` FOREIGN KEY (`user_dni`) REFERENCES `user` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_entity`
--

LOCK TABLES `order_entity` WRITE;
/*!40000 ALTER TABLE `order_entity` DISABLE KEYS */;
INSERT INTO `order_entity` VALUES (2,'2024-01-19 13:12:02.017000','pending',0,'1'),(3,'2024-01-21 16:09:29.960000','delivered',546,'126'),(5,'2024-02-13 23:35:33.152000','delivered',51,'126'),(6,'2024-02-19 01:02:16.979000','cancelled',76,'126'),(7,'2024-02-19 01:38:42.035000','cancelled',61,'126'),(8,'2024-02-20 23:33:45.109000','cancelled',13,'126'),(10,'2024-02-20 23:40:08.952000','cancelled',12,'126'),(11,'2024-02-20 23:40:18.659000','accepted',21,'126'),(12,'2024-03-06 14:25:22.363000','cancelled',0,'2'),(13,'2024-03-06 14:25:47.292000','cancelled',0,'2'),(14,'2024-03-22 18:38:12.463000','pending',0,'3'),(15,'2024-03-22 18:57:52.344000','pending',0,'2'),(18,'2024-04-08 16:25:29.079000','pending',0,'234'),(21,'2024-04-16 12:14:08.420000','pending',3,'126'),(22,'2024-04-16 12:51:31.743000','cancelled',7,'789'),(23,'2024-04-16 12:54:46.450000','delivered',15,'789'),(24,'2024-04-16 12:56:34.229000','accepted',4,'789'),(25,'2024-04-16 13:02:55.401000','pending',0,'789'),(26,'2024-07-25 15:43:24.057000','pending',0,'78');
/*!40000 ALTER TABLE `order_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_line`
--

DROP TABLE IF EXISTS `order_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_line` (
  `number` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity_prods` int(11) DEFAULT NULL,
  `sub_tot_price` float DEFAULT NULL,
  `order_cod` bigint(20) DEFAULT NULL,
  `prod_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FKfvlhl9tb2mewamng4qq57k260` (`order_cod`),
  KEY `FK5hbgawefrs2lycau83bu1x534` (`prod_id`),
  CONSTRAINT `FK5hbgawefrs2lycau83bu1x534` FOREIGN KEY (`prod_id`) REFERENCES `product` (`prod_id`),
  CONSTRAINT `FKfvlhl9tb2mewamng4qq57k260` FOREIGN KEY (`order_cod`) REFERENCES `order_entity` (`cod`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_line`
--

LOCK TABLES `order_line` WRITE;
/*!40000 ALTER TABLE `order_line` DISABLE KEYS */;
INSERT INTO `order_line` VALUES (1,37,148,3,7),(2,15,75,3,1),(3,12,48,3,6),(4,9,36,3,8),(5,7,21,3,9),(6,10,20,3,11),(7,16,48,3,12),(8,30,150,3,13),(9,2,8,NULL,7),(10,1,4,NULL,6),(11,3,4,5,7),(12,8,0,5,6),(13,2,0,5,1),(14,1,2,5,11),(15,4,8,5,8),(16,4,0,5,9),(17,4,16,5,10),(18,6,6,5,12),(19,2,10,5,13),(20,1,5,5,14),(21,1,4,6,7),(22,11,44,6,6),(23,1,5,6,1),(24,1,4,6,8),(25,1,4,6,10),(26,1,2,6,11),(27,1,3,6,12),(28,1,5,6,14),(29,1,5,6,13),(40,3,12,7,7),(41,1,4,7,6),(42,9,45,7,1),(43,1,5,8,1),(44,2,8,8,6),(46,1,4,10,6),(47,1,4,10,7),(48,1,4,10,10),(49,2,8,11,7),(50,1,4,11,8),(56,1,5,11,1),(57,1,3,11,12),(58,1,1,11,3),(59,1,5,22,1),(61,2,2,22,3),(62,1,5,23,1),(63,2,8,23,6),(64,1,2,23,2),(65,1,4,24,10),(66,1,3,21,9);
/*!40000 ALTER TABLE `order_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `prod_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `product_type` varchar(31) NOT NULL,
  `lts` float DEFAULT NULL,
  `amount_people_eat` int(11) DEFAULT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'8 slice pizza','Neapolitan pizza',5,'food',NULL,3,7),(2,'Plastic bottle','Coca-Cola',2,'drink',1.5,NULL,19),(3,'Plastic bottle','Water',1,'drink',0.5,NULL,12),(4,'Grapefuit juice','Levite',1.5,'drink',1,NULL,20),(5,'Plastic bottle','Sprite',2,'drink',1.5,NULL,15),(6,'Half a dozen','Meat empanada',4,'food',NULL,3,23),(7,'Half a dozen','Cheese and Ham empanada',4,'food',NULL,3,19),(8,'Half a dozen','Chicken empanada',4,'food',NULL,3,19),(9,'Tomato, lettuce, cheese, meat','Hamburguer',3,'food',NULL,1,15),(10,'Tomato, lettuce, cheddar, onion, 2 x meat','Special hamburguer',4,'food',NULL,1,18),(11,'Normal chips','Chips',2,'food',NULL,2,30),(12,'Chips with cheddar','Special Chips',3,'food',NULL,2,24),(13,'8 slice pizza','Fugazzeta pizza',5,'food',NULL,2,15),(14,'8 slice pizza','Mozzarella pizza',5,'food',NULL,2,20),(18,'chicken milanese with chips','Chicken Milanese',3,'food',NULL,1,15);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `dni` varchar(255) NOT NULL,
  `access_lvl` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_pass` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('','client','','','','$2a$10$TxZXoG9ozXc8SfptoBuThutlspoj.WfoUjJxMM7fC1D2TPZ6fcDT.','USER'),('1','client','lucho@gmail.com','armas','lucho','$2a$10$I5buR/Jkk5ObHz8EafTBuuZoKSnKPIHz7Hb9odsoWYiEM1S6Tmfku','ADMIN'),('126','client','juli@gmail.com','armas','julian','$2a$10$VOcg0MO1vz4O3aN8s3J1/OkNSHzNQnLDNNQ9DWTBocARgXoELWrAC','USER'),('2','client','l@hotmail.com','client','luciano','$2a$10$6Hjyc0CiYh0PKZuAPH/C0.hJ0UbJ2qWA0TJjblpJw9kdLSTQ7ge5u','USER'),('234','client','qcy@gmail.com','guns','qcy','$2a$10$SbtyjAvGK//TXX0ruT73BOFxbs0Iyl1mTwpTvi6O/JwrUEpAUxeX6','USER'),('3','admin','cele@gmail.com','admin','cele','$2a$10$tHZP.6l8X8rTLgv12kq6EOdLaFjmfgLjGEcFKunMd2kJaeIKnyUt6','ADMIN'),('78','client','78kl@gmail.com','lk','kl','$2a$10$G66SYea26KAtxTfNcAOCBOVuQcOlge93Qyr4FkhYh8HB8dPeQ9/bO','USER'),('789','client','la@gmai.com','Armas','Luciano','$2a$10$4YJDIZxaidtwNePGgZdz1.1Ksh73Gwe4v9/0lwMgyh8LRxEuZ1Q3i','USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50112 SET @disable_bulk_load = IF (@is_rocksdb_supported, 'SET SESSION rocksdb_bulk_load = @old_rocksdb_bulk_load', 'SET @dummy_rocksdb_bulk_load = 0') */;
/*!50112 PREPARE s FROM @disable_bulk_load */;
/*!50112 EXECUTE s */;
/*!50112 DEALLOCATE PREPARE s */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-11  6:07:51
