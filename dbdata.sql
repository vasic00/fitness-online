CREATE DATABASE  IF NOT EXISTS `fitness2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fitness2`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: fitness2
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin','admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advisor`
--

DROP TABLE IF EXISTS `advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advisor` (
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisor`
--

LOCK TABLES `advisor` WRITE;
/*!40000 ALTER TABLE `advisor` DISABLE KEYS */;
INSERT INTO `advisor` VALUES ('advisor','advisor','advisor','advisor123');
/*!40000 ALTER TABLE `advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute` (
  `value` varchar(255) DEFAULT NULL,
  `program_id` bigint NOT NULL,
  `specific_attribute_id` bigint NOT NULL,
  PRIMARY KEY (`program_id`,`specific_attribute_id`),
  KEY `FK1m7slccnlksvkwu5r7qvm1jmr` (`specific_attribute_id`),
  CONSTRAINT `FK1m7slccnlksvkwu5r7qvm1jmr` FOREIGN KEY (`specific_attribute_id`) REFERENCES `specific_attribute` (`id`),
  CONSTRAINT `FKbin9teic6oirbusmvw6c7v3tv` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES ('somevaluebbb',1,4),('somevalueaaa',1,5),('somevaluexxxx',2,6),('somevalue111',3,2),('somevalue222',3,3),('attribute1x',4,2),('attribute2x',4,3),('attribute5x',5,6),('attr5val',7,6);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'CategoryA'),(2,'CategoryB'),(3,'CategoryC');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `creator` bigint NOT NULL,
  `program` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfqymi934nige0v1gba0k0cx3g` (`creator`),
  KEY `FKme5q7vidsedsya7yxgpvqw4df` (`program`),
  CONSTRAINT `FKfqymi934nige0v1gba0k0cx3g` FOREIGN KEY (`creator`) REFERENCES `user` (`id`),
  CONSTRAINT `FKme5q7vidsedsya7yxgpvqw4df` FOREIGN KEY (`program`) REFERENCES `program` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Some comment','25.03.2024 10:06:32',1,1),(2,'Cool program','25.03.2024 10:34:57',2,1),(3,'Ovo je Markov komentar','03.04.2024 12:47:40',4,4),(4,'Another comment','03.04.2024 15:48:44',4,5),(5,'Cool program','04.04.2024 09:48:27',1,3);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `duration` int NOT NULL,
  `intensity` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `result` double NOT NULL,
  `user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK998jrtsgdk0f1bw4ic5oo0903` (`user`),
  CONSTRAINT `FK998jrtsgdk0f1bw4ic5oo0903` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES (1,'2024-03-25',30,120,'Exercise1',90,1),(2,'2024-01-15',20,110,'Exercise11',99,1),(3,'2024-01-16',25,115,'Exercise12',99,1),(4,'2024-01-17',25,116,'Exercise13',99,1),(5,'2024-01-18',40,125,'Exercise14',98,1),(6,'2024-01-20',40,130,'Exercise15',98,1),(7,'2024-01-22',35,125,'Exercise16',98,1),(8,'2024-01-23',30,115,'Exercise17',98,1),(9,'2024-01-30',25,120,'Exercise18',97,1),(10,'2024-02-05',30,112,'Exercise192',97,1),(11,'2024-02-06',40,122,'Exercise19',97,1),(12,'2024-02-07',25,130,'Exercise20',96,1),(13,'2024-02-10',15,115,'Exercise21',96,1),(14,'2024-02-12',45,125,'Exercise22',95,1),(15,'2024-02-18',40,120,'Exercise23',95,1),(16,'2024-02-20',20,110,'Exercise24',94,1),(17,'2024-02-25',30,128,'Exercise25',94,1),(18,'2024-02-27',50,125,'Exercise26',93,1),(19,'2024-03-10',40,130,'Exercise27',92,1),(20,'2024-03-11',20,140,'Exercise28',92,1),(21,'2024-03-12',30,110,'Exercise29',92,1),(22,'2024-03-14',35,120,'Exercise30',91,1),(23,'2024-03-20',40,130,'Exercise31',91,1),(24,'2024-03-21',15,110,'Exercise32',91,1),(25,'2024-03-22',20,120,'Exercise33',91,1),(26,'2024-04-04',40,130,'SomeExercise',88,1),(28,'2024-04-04',50,140,'SomeExercise2',86,1),(29,'2024-04-04',44,110,'SomeExercise3',81,1);
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time_of_sending` datetime(6) DEFAULT NULL,
  `unread` bit(1) NOT NULL,
  `receiver` bigint DEFAULT NULL,
  `sender` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1wn5617q01o90dwqjua8yhvux` (`receiver`),
  KEY `FKob83vkf2oo4r68pn9d69kgwf8` (`sender`),
  CONSTRAINT `FK1wn5617q01o90dwqjua8yhvux` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`),
  CONSTRAINT `FKob83vkf2oo4r68pn9d69kgwf8` FOREIGN KEY (`sender`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'What is going on','2024-03-25 10:36:11.495423',_binary '\0',NULL,2),(2,'jkljjlkjlhjkh','2024-03-25 10:37:46.107702',_binary '',1,2),(3,'This is a new message','2024-03-25 12:00:11.125026',_binary '\0',NULL,1);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participation` (
  `user_id` bigint NOT NULL,
  `program_id` bigint NOT NULL,
  KEY `FKk6ny0h4leu6k5matmpap60733` (`program_id`),
  KEY `FKfputwcduinudasn7es02c12ra` (`user_id`),
  CONSTRAINT `FKfputwcduinudasn7es02c12ra` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKk6ny0h4leu6k5matmpap60733` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES (2,1),(4,5),(1,3);
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `difficulty_level` int NOT NULL,
  `ending` datetime(6) DEFAULT NULL,
  `instructor_info` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `users_notified` bit(1) NOT NULL,
  `category` bigint NOT NULL,
  `creator` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2wep6dkcpotc85hj9vcubsrll` (`category`),
  KEY `FKbfujyd97ra9l8vr73sus943c2` (`creator`),
  CONSTRAINT `FK2wep6dkcpotc85hj9vcubsrll` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  CONSTRAINT `FKbfujyd97ra9l8vr73sus943c2` FOREIGN KEY (`creator`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (1,'066615999','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu faucibus nisl, sit amet facilisis sapien. Integer augue nunc, faucibus id vulputate ac, placerat auctor nisl.',3,'2024-03-29 04:04:00.000000','Andrija Vasic','Banja Luka','Program1',55.99,_binary '',2,1),(2,'066992348','Cras quis nisl faucibus, faucibus justo consectetur, pharetra ipsum. Aenean sodales, magna sed interdum ornare, erat ex pharetra justo, a placerat tortor urna sit amet diam. Aliquam erat volutpat.',2,'2024-03-29 11:11:00.000000','Stojko Stojkovic','Prijedor','Program2',100,_binary '',3,1),(3,'066123123','Nulla sed efficitur quam. Curabitur sollicitudin imperdiet leo vel malesuada. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.',2,'2024-12-26 04:44:00.000000','Some instructor info','Novi Grad','Program3',120.99,_binary '',1,2),(4,'066123123','some description',3,'2025-03-07 04:44:00.000000','some info some info','Sarajevo','Program4',199.99,_binary '',1,3),(5,'066555555','some description',1,'2025-02-11 08:08:00.000000','Some instructor info','Prijedor','Program5',29.99,_binary '',3,3),(7,'aaaaaaaa','aaaaaaaaaaaaaa',4,'2024-04-26 05:55:00.000000','aaaaaaaa','aaaaa','Program6',66.89,_binary '',3,4);
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_image`
--

DROP TABLE IF EXISTS `program_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `program` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdk03qyl7o91fmgjqkjrj20bk8` (`program`),
  CONSTRAINT `FKdk03qyl7o91fmgjqkjrj20bk8` FOREIGN KEY (`program`) REFERENCES `program` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_image`
--

LOCK TABLES `program_image` WRITE;
/*!40000 ALTER TABLE `program_image` DISABLE KEYS */;
INSERT INTO `program_image` VALUES (1,'1-1.jpg',1),(2,'1-2.jpg',1),(3,'2-1.jpg',2),(4,'3-1.jpg',3),(5,'4-1.jpg',4),(6,'5-1.jpg',5),(7,'5-2.jpg',5),(8,'5-3.jpg',5),(9,'5-4.jpg',5),(11,'7-1.jpg',7),(12,'7-2.jpg',7);
/*!40000 ALTER TABLE `program_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_type` varchar(255) DEFAULT NULL,
  `program_category` varchar(255) DEFAULT NULL,
  `program_name` varchar(255) DEFAULT NULL,
  `program_price` double NOT NULL,
  `time` varchar(255) DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKggof91v7jv6w6jldiekpy1n9c` (`user`),
  CONSTRAINT `FKggof91v7jv6w6jldiekpy1n9c` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,'card 8899878909877','CategoryB','Program1',55.99,'25.03.2024 10:35:13',2),(2,'paypal 86568986','CategoryC','Program5',29.99,'03.04.2024 13:17:18',4),(3,'card 12345667789098','CategoryA','Program3',120.99,'04.04.2024 09:48:20',1);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specific_attribute`
--

DROP TABLE IF EXISTS `specific_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specific_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `category` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl69ahlxbx32l1lnrje7ychnxv` (`category`),
  CONSTRAINT `FKl69ahlxbx32l1lnrje7ychnxv` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specific_attribute`
--

LOCK TABLES `specific_attribute` WRITE;
/*!40000 ALTER TABLE `specific_attribute` DISABLE KEYS */;
INSERT INTO `specific_attribute` VALUES (2,'attribute1',1),(3,'attribute2',1),(4,'attribute3',2),(5,'attribute4',2),(6,'attribute5',3),(7,'attrnew',1);
/*!40000 ALTER TABLE `specific_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `user_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  KEY `FKnj55m79pro2qanuayd9ckn3st` (`category_id`),
  KEY `FK8l1goo02px4ye49xd7wgogxg6` (`user_id`),
  CONSTRAINT `FK8l1goo02px4ye49xd7wgogxg6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnj55m79pro2qanuayd9ckn3st` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,1),(2,3),(2,2),(2,1);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVATED','NOT_ACTIVATED','BLOCKED') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'http://localhost:8082/FitnessOnline/avatars/a4.png','Banja Luka','mihailo.vasic1306@gmail.com','Mihailo','Vasic','$2a$10$Iv/0BUcyslWSaB538i6JouPZ8by/Pp0Mxdtqu6v5EztyeJ.yVJbQm','ACTIVATED','mihailo'),(2,'http://localhost:8082/FitnessOnline/avatars/a4.png','Prijedor','mihailo.vasic2000@gmail.com','Andrija','Vasic','$2a$10$ORaBLlQ6ZyanaVTLayxASuYDCOBKDYX2msPtQbvqbbp.RmkYdZ69i','ACTIVATED','andrija'),(3,'http://localhost:8082/FitnessOnline/avatars/a5.png','Some City','mihailo.vasic1306@gmail.com','someguy','someguy','$2a$10$0nalobqaoElaJ3VxQFoHNeb9cwJBVYiwz371SwUxRwA3fleHKxiMS','ACTIVATED','someguy'),(4,'http://localhost:8082/FitnessOnline/avatars/a5.png','Banja Luka','mihailo.vasic1306@gmail.com','Marko','Markovic','$2a$10$Lp1Gwxyy.OBRZ3uD5J0SMe4Fu9mTlwPkbmLL9gN1miO8DnEA9AVl6','ACTIVATED','marko');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-18 15:57:04
