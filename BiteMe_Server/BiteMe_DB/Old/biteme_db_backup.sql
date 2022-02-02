-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biteme_db
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `biteme_table`
--

DROP TABLE IF EXISTS `biteme_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `biteme_table` (
  `Restaurant` varchar(30) DEFAULT NULL,
  `OrderNumber` varchar(45) NOT NULL,
  `OrderTime` varchar(45) DEFAULT NULL,
  `PhoneNumber` varchar(45) DEFAULT NULL,
  `TypeOfOrder` varchar(45) DEFAULT NULL,
  `OrderAddress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biteme_table`
--

LOCK TABLES `biteme_table` WRITE;
/*!40000 ALTER TABLE `biteme_table` DISABLE KEYS */;
INSERT INTO `biteme_table` VALUES ('Pizza Hut','103','14:24','05462585','Take-Away','karmiel'),('Japanika','225','18:54','05248539','Delivery','Ashdod');
/*!40000 ALTER TABLE `biteme_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branchmantoceo`
--

DROP TABLE IF EXISTS `branchmantoceo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branchmantoceo` (
  `branch` varchar(45) NOT NULL,
  `quarter` int NOT NULL,
  `year` int NOT NULL,
  `pdfFile` blob,
  PRIMARY KEY (`branch`,`quarter`,`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branchmantoceo`
--

LOCK TABLES `branchmantoceo` WRITE;
/*!40000 ALTER TABLE `branchmantoceo` DISABLE KEYS */;
INSERT INTO `branchmantoceo` VALUES ('E_NORTH',1,2022,_binary '%PDF-1.4\n%\‚\„\œ\”\n2 0 obj\n<</Length 73/Filter/FlateDecode>>stream\nxú+\‰r\n\·26S∞00SI\·2P\–5¥\01Ù\›çB“∏44C≤Ä(Ú\ZÅ•â%©E9ï\nA©˘E%\≈Hä\\C∏π\0{\Ô<\nendstream\nendobj\n4 0 obj\n<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F1 1 0 R>>>>/Contents 2 0 R/Parent 3 0 R>>\nendobj\n1 0 obj\n<</Type/Font/Subtype/Type1/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Type/Pages/Count 1/Kids[4 0 R]>>\nendobj\n5 0 obj\n<</Type/Catalog/Pages 3 0 R>>\nendobj\n6 0 obj\n<</Producer(iTextÆ 5.5.13.2 ©2000-2020 iText Group NV \\(AGPL-version\\))/CreationDate(D:20220104132026+02\'00\')/ModDate(D:20220104132026+02\'00\')>>\nendobj\nxref\n0 7\n0000000000 65535 f \n0000000266 00000 n \n0000000015 00000 n \n0000000354 00000 n \n0000000154 00000 n \n0000000405 00000 n \n0000000450 00000 n \ntrailer\n<</Size 7/Root 5 0 R/Info 6 0 R/ID [<5fdd788069ccc9cba35aeb96cbb4a0cc><5fdd788069ccc9cba35aeb96cbb4a0cc>]>>\n%iText-5.5.13.2\nstartxref\n610\n%%EOF\n');
/*!40000 ALTER TABLE `branchmantoceo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `businessW4C` int NOT NULL,
  `companyName` varchar(45) NOT NULL,
  `employerName` varchar(45) NOT NULL,
  `branch` varchar(45) NOT NULL,
  `isApproved` varchar(45) DEFAULT NULL,
  `monthlyLimit` int DEFAULT NULL,
  PRIMARY KEY (`companyName`,`employerName`,`branch`,`businessW4C`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES (5002,'Asaf & Sons','Asaf','E_NORTH','YES',2000),(5001,'Bob and Sons','Bob','E_NORTH','YES',150),(5003,'Eli & Brothers','Eli','E_NORTH','YES',300),(1554,'Eli Robots','Eli','E_CENTER','NO',100),(1687,'Eli Robots','Eli','E_NORTH','YES',150),(1653,'TalTech','Tal Alright','E_NORTH','NO',200);
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `restID` int NOT NULL,
  `dishName` varchar(45) NOT NULL,
  `restName` varchar(45) NOT NULL,
  `price` varchar(45) NOT NULL,
  `dishSizes` varchar(100) NOT NULL,
  `category` varchar(45) NOT NULL,
  `allergyCategories` varchar(250) NOT NULL,
  `ingredients` varchar(250) NOT NULL,
  `removableOptions` varchar(250) NOT NULL,
  `grillDoneness` varchar(45) NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY (`dishName`,`restID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (120,'Chicken Sandwitch','Tals Sandwitches','10.90','E_SMALL','E_MAIN','E_DAIRY,E_EGGS,E_WHEAT,E_NUTS,E_SHELLFISH,E_SOY,E_FISH','Chicken,  Bread,  Mayo','Chicken','E_YES','Delicous chicken sandwitch'),(120,'Chicken Soup','Bobs Fishes','12.99','E_LARGE','E_SOUP','E_EGGS','Chicken,      Water,      Tomatoes','Water,      Tomatoes','E_NO','Yummy Chicken Soup!'),(120,'Chocolate Cake','Tals Sandwitches','20.55','E_LARGE','E_DESSERT','E_NUTS','Chocolate, Flour, Nuts','Nuts','E_NO','Thick chocolate Cake with nuts on top'),(120,'Fish Sticks','Tals Sandwitches','14.65','E_MED','E_APPETIZER','E_WHEAT,E_FISH','Fish,Wheat,Corn','Wheat,Corn','E_NO','Large Fish Sticks'),(120,'Fuze Tea','Tals Sandwitches','5.62,7.52','E_SMALL,E_MED','E_DRINKS','E_NA','Fuze Tea','NA','E_NO','Refreshing drink'),(120,'Greek Salad W/ Chicken','Tals Sandwitches','10.95','E_SMALL','E_SALAD','E_EGGS,E_NUTS','Leaves, Chicken, Sauce','Chicken','E_YES','Yummy greek salad in the style of israel'),(144,'Green Tea Shake','Shakes Shak','12.95,15.20,20.51','E_SMALL,E_MED,E_LARGE','E_DRINKS','E_NUTS','Kale, Nuts, Lemon','Lemon, Nuts','E_NO','Healthy tall glass of energy'),(144,'Leaves of the Field','Shakes Shak','12.95,15.20','E_SMALL,E_MED','E_SALAD','E_EGGS,E_NUTS','Salad, Nuts, Eggs','Eggs','E_NO','Healthy Salad with a large serving of eggs'),(144,'Lemon Bars','Shakes Shak','15.45','E_MED','E_DESSERT','E_EGGS,E_WHEAT','Lemon, Sugar, Wheat','NA','E_NO','A delicous lemon cake'),(144,'Nut Bowl','Shakes Shak','10.95','E_SMALL','E_APPETIZER','E_NUTS','Nuts','NA','E_NO','Healthy Nut bowl'),(120,'Shrimp','Bobs Fishes','11.99,12.99','E_SMALL,E_LARGE','E_APPETIZER','E_NA','Fish, Salt, Pepper, Fries, corn','Salt, Pepper, corn, tacos','E_NO','Large fillet of fish with crusty badder and a large side of crunchy double fired french fries.'),(155,'Soda','Bobs Fishes','11.99,13.99,12.99','E_SMALL,E_MED,E_LARGE','E_APPETIZER','E_NA','Fish, Salt, Pepper, Fries','Salt, Pepper, corn, tacos','E_NO','Large fillet of fish with crusty badder and a large side of crunchy double fired french fries.');
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_users`
--

DROP TABLE IF EXISTS `external_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `external_users` (
  `ID` int NOT NULL,
  `privateName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `cardNumber` varchar(45) DEFAULT NULL,
  `restName` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_users`
--

LOCK TABLES `external_users` WRITE;
/*!40000 ALTER TABLE `external_users` DISABLE KEYS */;
INSERT INTO `external_users` VALUES (1,'asaf','fas','asaf','a','a','151','HR for intel','1001-1542-1522-4587','NA','NA','NA'),(145,'tal','talush','tt','tt','tt','147','Looser','1452-5478-8521-7845','Tals Sandwitches','NA','NA'),(424,'eli','a','eli','e','e','152','user','1452-4785-9856-9852','NA','NA','NA');
/*!40000 ALTER TABLE `external_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlyreports`
--

DROP TABLE IF EXISTS `monthlyreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlyreports` (
  `restID` int NOT NULL,
  `date` date NOT NULL,
  `branch` varchar(45) NOT NULL,
  `appAmnt` int DEFAULT NULL,
  `saladAmnt` int DEFAULT NULL,
  `soupAmnt` int DEFAULT NULL,
  `mainAmnt` int DEFAULT NULL,
  `dessertAmnt` int DEFAULT NULL,
  `drinksAmnt` int DEFAULT NULL,
  `totalOrders` int DEFAULT NULL,
  `totalSum` double DEFAULT NULL,
  `ordersOnTime` int DEFAULT NULL,
  `ordersLate` int DEFAULT NULL,
  `performance` float DEFAULT NULL,
  `feeSum` double DEFAULT '0',
  `fee` float DEFAULT '0',
  `restName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`restID`,`branch`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlyreports`
--

LOCK TABLES `monthlyreports` WRITE;
/*!40000 ALTER TABLE `monthlyreports` DISABLE KEYS */;
INSERT INTO `monthlyreports` VALUES (120,'2021-10-30','E_NORTH',2,2,2,2,2,2,12,160,12,0,1,14.4,0.09,'Bobs Fishes'),(120,'2021-11-30','E_NORTH',5,0,0,5,5,5,20,4500,10,10,0.5,450,0.1,'Bobs Fishes'),(120,'2021-12-30','E_NORTH',164,0,0,0,0,1,165,16899.980000000007,6,4,0.6,1926.6975551203445,0.114006,'Bobs Fishes'),(120,'2022-01-02','E_NORTH',165,0,2,0,0,1,168,16937.950000000008,6,4,0.6,1223.0392548122097,0.072207,'null'),(155,'2021-12-30','E_SOUTH',124,15,24,154,222,120,659,150000,600,59,0.9,18000,0.12,'Sallis Pies');
/*!40000 ALTER TABLE `monthlyreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderNumber` varchar(45) NOT NULL,
  `restID` int NOT NULL,
  `userID` int NOT NULL,
  `restaurantName` varchar(45) NOT NULL,
  `timeDesired` varchar(45) NOT NULL,
  `dateDesired` date NOT NULL,
  `timeApproved` varchar(45) NOT NULL DEFAULT 'NA',
  `timeRecieved` varchar(45) NOT NULL DEFAULT 'NA',
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `orderStatus` varchar(45) NOT NULL,
  `delType` varchar(45) NOT NULL,
  `delOnTime` varchar(45) DEFAULT 'E_NA',
  `total` double NOT NULL,
  `businessOrderNum` varchar(45) DEFAULT NULL,
  `branch` varchar(45) NOT NULL,
  `isEarlyOrder` int DEFAULT '0',
  `deliveryBusinessName` varchar(45) DEFAULT 'NA',
  `deliveryReceiverName` varchar(45) DEFAULT 'NA',
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('1-120-100',120,123,'Tals Sandwitches','09:12','2022-01-26','22:45','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-101',120,123,'Tals Sandwitches','09:22','2022-01-27','22:45','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-102',120,123,'Tals Sandwitches','09:12','2022-01-26','22:46','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-103',120,123,'Tals Sandwitches','09:34','2022-01-26','22:46','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-104',120,123,'Tals Sandwitches','05:23','2022-01-27','22:46','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-105',120,123,'Tals Sandwitches','09:12','2022-01-27','22:48','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-106',120,123,'Tals Sandwitches','09:12','2022-02-02','22:48','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-107',120,123,'Tals Sandwitches','09:12','2022-02-02','21:56','21:57','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-108',120,123,'Tals Sandwitches','09:12','2022-02-02','21:56','21:57','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',46.476,'NA','E_NORTH',1,'NA','NA'),('1-120-109',120,123,'Tals Sandwitches','09:12','2022-01-26','21:59','21:59','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-110',120,123,'Tals Sandwitches','09:12','2022-02-01','22:03','22:04','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-111',120,123,'Tals Sandwitches','09:12','2022-01-26','22:40','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-112',120,123,'Tals Sandwitches','09:12','2022-02-03','22:39','22:46','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-114',120,145,'Tals Sandwitches','09:12','2022-02-03','NA','NA','Carmiel road','d','05421987454','E_WAITING_REST_APPROVE','E_DELIVERY','E_NA',35.685,'NA','E_NORTH',1,'NA','Tal'),('1-120-115',120,145,'Tals Sandwitches','09:12','2022-02-02','NA','NA','NA','NA','NA','E_WAITING_REST_APPROVE','E_PICKUP','E_NA',141.16500000000002,'NA','E_NORTH',1,'NA','NA'),('1-120-116',120,145,'Tals Sandwitches','09:12','2022-02-02','NA','NA','NA','NA','NA','E_WAITING_REST_APPROVE','E_PICKUP','E_NA',141.16500000000002,'NA','E_NORTH',1,'NA','NA'),('1-120-117',120,123,'Tals Sandwitches','09:12','2022-02-02','12:29','12:30','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-25',120,123,'Bobs Fishes','10:31','2021-11-29','20:10','20:24','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',44.082,'NA','E_NORTH',1,'NA','NA'),('1-120-26',120,123,'Bobs Fishes','15:21','2021-12-29','20:11','20:24','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-27',120,123,'Bobs Fishes','09:12','2021-12-30','20:13','20:24','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-28',120,123,'Bobs Fishes','09:12','2021-12-29','20:16','20:16','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-29',120,123,'Bobs Fishes','15:12','2021-12-29','20:19','20:19','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-30',120,123,'Bobs Fishes','15:12','2021-12-31','20:21','20:21','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-31',120,123,'Bobs Fishes','09:12','2021-12-29','20:23','20:23','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-32',120,123,'Bobs Fishes','15:12','2021-12-29','20:27','20:27','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-33',120,123,'Bobs Fishes','09:12','2021-12-29','20:32','20:32','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-34',120,123,'Bobs Fishes','09:12','2021-12-29','20:35','20:35','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-35',120,123,'Bobs Fishes','09:12','2021-12-29','20:50','20:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-37',120,123,'Bobs Fishes','09:12','2021-12-29','20:55','20:55','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',44.082,'NA','E_NORTH',1,'NA','NA'),('1-120-39',120,123,'Bobs Fishes','05:12','2021-12-30','21:11','21:11','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',54.873,'NA','E_NORTH',1,'NA','NA'),('1-120-42',120,123,'Bobs Fishes','09:15','2021-12-30','22:56','22:58','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',102.94,'NA','E_NORTH',0,'NA','NA'),('1-120-43',120,123,'Bobs Fishes','09:12','2021-12-30','23:58','23:58','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',36.99,'NA','E_NORTH',0,'NA','NA'),('1-120-44',120,123,'Tals Sandwitches','09:15','2022-01-04','15:17','15:18','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',56.673,'NA','E_NORTH',1,'NA','NA'),('1-120-48',120,123,'Tals Sandwitches','09:12','2022-02-01','22:48','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-49',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-50',120,123,'Tals Sandwitches','09:12','2022-02-01','22:50','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-51',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-52',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-53',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-54',120,123,'Tals Sandwitches','09:12','2022-02-01','22:50','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-55',120,123,'Tals Sandwitches','09:12','2022-02-01','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-56',120,123,'Tals Sandwitches','09:12','2022-02-01','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-57',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-58',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-59',120,123,'Tals Sandwitches','09:12','2022-01-26','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',21.582,'NA','E_NORTH',1,'NA','NA'),('1-120-60',120,123,'Tals Sandwitches','09:12','2022-01-26','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',21.582,'NA','E_NORTH',1,'NA','NA'),('1-120-61',120,123,'Tals Sandwitches','09:12','2022-01-26','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',21.582,'NA','E_NORTH',1,'NA','NA'),('1-120-62',120,123,'Tals Sandwitches','09:12','2022-01-26','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',21.582,'NA','E_NORTH',1,'NA','NA'),('1-120-63',120,123,'Tals Sandwitches','09:12','2022-01-25','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',10.791,'NA','E_NORTH',1,'NA','NA'),('1-120-64',120,145,'Tals Sandwitches','09:12','2022-02-01','23:48','23:48','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',28.791,'NA','E_NORTH',1,'NA','NA'),('1-120-65',120,123,'Tals Sandwitches','09:12','2022-01-26','22:50','22:51','123','dd d','123','E_DELIVERD','E_DELIVERY','E_ON_TIME',65.592,'NA','E_NORTH',1,'NA','NA'),('1-120-66',120,123,'Tals Sandwitches','05:12','2022-01-25','15:32','15:45','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-67',120,123,'Tals Sandwitches','08:12','2022-02-01','15:46','15:49','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-68',120,123,'Tals Sandwitches','09:12','2022-02-01','15:53','15:53','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-69',120,123,'Tals Sandwitches','09:12','2022-01-31','15:54','15:54','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-70',120,123,'Tals Sandwitches','09:12','2022-01-31','15:57','15:58','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-71',120,123,'Tals Sandwitches','09:12','2022-01-31','15:59','16:00','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',48.87,'NA','E_NORTH',1,'NA','NA'),('1-120-72',120,123,'Tals Sandwitches','09:12','2022-02-01','16:01','16:03','NA','NA','NA','E_DELIVERD','E_PICKUP','E_NA',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-73',120,123,'Tals Sandwitches','09:12','2022-02-01','16:07','16:10','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-74',120,123,'Tals Sandwitches','09:12','2022-01-26','16:09','16:10','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-75',120,123,'Tals Sandwitches','09:12','2022-01-26','16:10','18:38','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-76',120,123,'Tals Sandwitches','09:12','2022-02-01','19:35','19:59','NA','NA','NA','E_DELIVERD','E_PICKUP','E_LATE',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-77',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-78',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-79',120,123,'Tals Sandwitches','09:12','2022-02-02','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-80',120,123,'Tals Sandwitches','09:12','2022-01-27','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-81',120,123,'Tals Sandwitches','09:12','2022-01-26','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-82',120,123,'Tals Sandwitches','09:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-83',120,123,'Tals Sandwitches','09:12','2022-02-02','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-84',120,123,'Tals Sandwitches','09:12','2022-02-02','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-85',120,123,'Tals Sandwitches','09:12','2022-01-26','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-86',120,123,'Tals Sandwitches','08:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-87',120,123,'Tals Sandwitches','09:12','2022-02-01','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-88',120,123,'Tals Sandwitches','09:12','2022-02-02','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-89',120,123,'Tals Sandwitches','10:12','2022-02-02','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-90',120,123,'Tals Sandwitches','09:12','2022-02-03','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-91',120,123,'Tals Sandwitches','09:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-92',120,123,'Tals Sandwitches','09:12','2022-01-25','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-93',120,123,'Tals Sandwitches','09:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-94',120,123,'Tals Sandwitches','09:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',33.291000000000004,'NA','E_NORTH',1,'NA','NA'),('1-120-95',120,123,'Tals Sandwitches','09:12','2022-02-02','22:47','22:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-96',120,123,'Tals Sandwitches','09:12','2022-01-31','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-97',120,123,'Tals Sandwitches','09:12','2022-02-03','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-98',120,123,'Tals Sandwitches','09:12','2022-01-26','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-120-99',120,123,'Tals Sandwitches','09:12','2022-01-26','22:50','22:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',35.685,'NA','E_NORTH',1,'NA','NA'),('1-144-113',144,123,'Shakes Shak','09:12','2022-01-25','NA','NA','NA','NA','NA','E_WAITING_REST_APPROVE','E_PICKUP','E_NA',32.355000000000004,'NA','E_NORTH',1,'NA','NA'),('3-155-45',155,123,'Salli','09:12','2022-01-25','19:50','19:50','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',44.082,'NA','E_SOUTH',1,'NA','NA'),('3-155-46',155,123,'Salli','09:12','2022-01-25','19:51','19:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',54.873,'NA','E_SOUTH',1,'NA','NA'),('3-155-47',155,123,'Salli','09:12','2022-01-25','19:51','19:51','NA','NA','NA','E_DELIVERD','E_PICKUP','E_ON_TIME',65.664,'NA','E_SOUTH',1,'NA','NA');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarterlyreports`
--

DROP TABLE IF EXISTS `quarterlyreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarterlyreports` (
  `quarter` int NOT NULL,
  `year` int NOT NULL,
  `branch` varchar(45) NOT NULL,
  `pdfReport` blob,
  PRIMARY KEY (`quarter`,`branch`,`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarterlyreports`
--

LOCK TABLES `quarterlyreports` WRITE;
/*!40000 ALTER TABLE `quarterlyreports` DISABLE KEYS */;
INSERT INTO `quarterlyreports` VALUES (1,2022,'E_NORTH',_binary '%PDF-1.4\n%\‚\„\œ\”\n4 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 1 1]/N 1>>\nendobj\n6 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n8 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 0.47451 0.47451]/N 1>>\nendobj\n10 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.47451 0.47451]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n12 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 0.33333 1]/C1[1 1 1]/N 1>>\nendobj\n14 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 0.33333 1]/N 1>>\nendobj\n16 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 0.33333 1]/C1[0.47451 0.47451 1]/N 1>>\nendobj\n18 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 0.47451 1]/C1[0.33333 0.33333 1]/N 1>>\nendobj\n20 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n22 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n24 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[0.47451 1 0.47451]/N 1>>\nendobj\n26 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 1 0.47451]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n28 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n30 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 1 0.33333]/N 1>>\nendobj\n32 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.33333]/C1[1 1 0.47451]/N 1>>\nendobj\n34 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.47451]/C1[1 1 0.33333]/N 1>>\nendobj\n36 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n38 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n40 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[0.47451 1 0.47451]/N 1>>\nendobj\n42 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 1 0.47451]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n46 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 1 1]/N 1>>\nendobj\n48 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n50 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 0.47451 0.47451]/N 1>>\nendobj\n52 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.47451 0.47451]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n54 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 0.33333 1]/C1[1 1 1]/N 1>>\nendobj\n56 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 0.33333 1]/N 1>>\nendobj\n58 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 0.33333 1]/C1[0.47451 0.47451 1]/N 1>>\nendobj\n60 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 0.47451 1]/C1[0.33333 0.33333 1]/N 1>>\nendobj\n62 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n64 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n66 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[0.47451 1 0.47451]/N 1>>\nendobj\n68 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 1 0.47451]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n70 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n72 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 1 0.33333]/N 1>>\nendobj\n74 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.33333]/C1[1 1 0.47451]/N 1>>\nendobj\n76 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 0.47451]/C1[1 1 0.33333]/N 1>>\nendobj\n78 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[1 1 1]/N 1>>\nendobj\n80 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n82 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.33333 1 0.33333]/C1[0.47451 1 0.47451]/N 1>>\nendobj\n84 0 obj\n<</FunctionType 2/Domain[0 1]/C0[0.47451 1 0.47451]/C1[0.33333 1 0.33333]/N 1>>\nendobj\n87 0 obj\n<</Length 121/Filter/FlateDecode>>stream\nxú+\‰*T0T0\0BCc33CÖ\‰\\˝à4Có|Ö@TYCC3®¨D(\œ\Â\¬î≤00SI\·2P\–5¥\01Ù›åçB“∏4KKRãr*ÇRÚãJä5C≤∏@F\"+\◊P@56∞\ƒeú	˙]CÄN\0à´1¡\nendstream\nendobj\n89 0 obj\n<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F2 3 0 R>>/XObject<</Xf1 1 0 R/Xf2 45 0 R>>>>/Contents 87 0 R/Parent 88 0 R>>\nendobj\n90 0 obj\n<</Length 162/Filter/FlateDecode>>stream\nxúÖŒ±Ç0–Ω_ÒF¿WhI$q0Q\Ï†‘Ä©≠\“ÚˇÇâ&uÄ7\›\·û\‹˜\"Ö I3\rA)õ¬∫åÅ\∆ n$®§uı\–\◊\⁄¡^_\ÕCB%ü¶wv%\Ó#@\œ£]ªäaa.\ Œ∂\“\Õ0\¬\Ÿ>ı˙õó@¸7¿8è8\√\Ô\Â≥8‘ÉR#\‚IÒÙá\ÿ\¬\‚πV™≥p\Ï¶\≈Ûû\‹	r\"o¥_UB\nendstream\nendobj\n91 0 obj\n<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F2 3 0 R>>>>/Contents 90 0 R/Parent 88 0 R>>\nendobj\n2 0 obj\n<</Type/Font/Subtype/Type1/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Type/Font/Subtype/Type1/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n45 0 obj\n<</Type/XObject/Subtype/Form/Resources<</Font<</F1 2 0 R/F2 3 0 R>>/Pattern<</P21 47 0 R/P22 49 0 R/P23 51 0 R/P24 53 0 R/P25 55 0 R/P26 57 0 R/P27 59 0 R/P28 61 0 R/P29 63 0 R/P30 65 0 R/P31 67 0 R/P32 69 0 R/P33 71 0 R/P34 73 0 R/P35 75 0 R/P36 77 0 R/P37 79 0 R/P38 81 0 R/P39 83 0 R/P40 85 0 R>>/ExtGState<</GS2 86 0 R>>>>/BBox[0 0 500 300]/FormType 1/Matrix [1 0 0 1 0 0]/Length 1191/Filter/FlateDecode>>stream\nxú•ò\ﬂo\‹6\«\ﬂ˝W\Ë±VG¢dK\⁄[\n\\këó\rC±á4Mõ\Ó\Õ]∂§(…¥\Î∏8$\ƒ_J\‘«¥D—∫˚\ﬁheµV_Ò™\’\–tzºR˚\–\‹55ª∆®ˇ\ZPø£◊ó\∆hı∂yˇèVõ\Ôu¯8† \ ˇ)Ü>üèˆ)µh\ZÙj€úº6\n¥\⁄~Bµ\Z\Â]\€)°çQmø6/ukBAmoöõ\À\Ì\È\’\≈Èª≠:ø:\›n.˛¯[]l˛‹ºª\⁄¸≤˝Ç`t\⁄l«®å\—≥≠õ\r±ZC∂*ˆ\ZUÒcΩxÉ](¥1É≤ö\À\‘cp∑ìö∂é#©Ω¯¸@Q∫µÙWØ)~ö”∑\‡Ñ\ﬁ\≈\Z≤≤ë\'FÒKO\—BßÒFÛkYP\‰\Z\Ëë\‡i\Zp	t´˚4˝oø\Ìwø-\Áz5\œ{\Ëd\‹\— ∏ìUBMé\„YqGø∑Yä\€\»˘ÜÆqC/Áë≠*;Nå\Á\ƒ\r}Xâ\Êπ-\"∂\‡E\ƒ\÷\ d+…é\„9[ß\ÁöCûŒ¥\Ô :5ø\"öv@O{¡Öê‘êîß-Å}\Â\ =fáÖˇk{5ø\‚6•Q.Ω\„ù\’\—4§\÷*ó\€!µC•Ω\ \Âvˆw`J{ï\À\Ì\Ï¸f§\ƒ\√kAt∫\„yı\Ì\√^Ωæ\ﬂ\ﬂ\›\Ó\Âå/ÁòèCI\Êd˜8?m£üÖ`y¡/Øá\·~Ø\Œ\ÓB¿\ÿ\›$Ñ_Xµ\–9¿\ÂJu˝v∏~|∏\ﬁ$#t9;PxÆíã±\·ºzK©å\‘©8\—`Kc~:\÷i{™¡¸X\⁄ÛSÅ˛U\ÎIùCû\—SóôYØq\r`¶\€\ÿ-Ç°k]\Ã`÷´`\ÁZ\Ág\‡~\Ï\"≥^còÅ\√\"\ÿ\ÁW!Å}}â=Q*∞.õ6÷àÉç^$\„r\‡¨1ôı\ZL\◊z3\'/.\‡z∏s÷´d¨pÆüì\◊pApﬁòÃ∫í\À \ÍK\¬\„˙\“\—¿QEH\€¿Q\rMõ\‡\ﬂ\€\›\„\ÌÑ}\\}§ô\∆C\‡°\Ó=(P\ÈL«ß°\\C©ç¸t)YM\Õ\"≠©´\‰e\Í\Zì∫Jf•Æ1µ®´\‰F\Í\ZìÉ∫\ ‚¶Æqu\”I(ØNz\‘qy®´Lo\Í\Z\Á˜\‰\Ï˙p∏}ÿ©õΩ:9£ˆ7x\"\’xr§=ﬂöò_¨á¨I©Ÿá^23.0∑Ç\n\nT\È\‚ñ8õq0\‚@\‡†\‚&\‚IúcHæÃº fsBáUh\«Pãu∆©Ä\Á5Z3c˚¨á§\ƒ>RünüπΩÄ\r‘èb\Á3.å8\'pÆ\‚ÑX¡\∆9Å\Î\÷¡ë\»&≥*›≠B#Cª\ÿFzÉÿ∂\'nYI3à}§.>?r≠fn\‘\nhU∫∏\Â.oû^\ƒ\Ë\ŒWú+∏ºgº\ƒ\·!π\ƒd2´\“˝*îwé5°uTØ-~<ÛÇ.∆êåÑ\ ^RWß4\Ô\"Å\".Hna\’\»\€.ªLîëZI¥ï(\ƒ\Zë7èÄy_wöl\∆\’\ÿu.\Ô\"áe3éæÀòtb\rYI\'Rˆë∫¯,pC\Êç\Z(åb3.é8+p∂\‚Ñx\Z\Áxˇå\ƒ9†î¨D6ôU\Èv\n=øò¨ˇ\‡\¬\ﬂ\\æù\ræùO\ﬁ\\Ç˙º^8ÚMyƒù\œÒÛ?<=\ T\nendstream\nendobj\n1 0 obj\n<</Type/XObject/Subtype/Form/Resources<</Font<</F1 2 0 R/F2 3 0 R>>/Pattern<</P1 5 0 R/P2 7 0 R/P3 9 0 R/P4 11 0 R/P5 13 0 R/P6 15 0 R/P7 17 0 R/P8 19 0 R/P9 21 0 R/P10 23 0 R/P11 25 0 R/P12 27 0 R/P13 29 0 R/P14 31 0 R/P15 33 0 R/P16 35 0 R/P17 37 0 R/P18 39 0 R/P19 41 0 R/P20 43 0 R>>/ExtGState<</GS1 44 0 R>>>>/BBox[0 0 500 300]/FormType 1/Matrix [1 0 0 1 0 0]/Length 1158/Filter/FlateDecode>>stream\nxú•òKo\‹6\«\Ô˙<¶á\–\‰!±7uMc\ÔnPAÆ\„\ƒî\r\‚u–Ø\ﬂ)éˆ°¿∞∞˛\√˛4\Z>\ƒ\›\ÔùQ\ŒıØFç]0\ÌJ\Ìcw\◊˝\’m;´˛\Î@˝éQ_:k\‘\€\Ó\√?F}\ÏæO\›[áä®ˇ\Á˙<|~B∑Oπ\≈PßWõ\Ó\ÏµU`\‘\Ê¢®’™>È††tJjÛµ{i¥\ÌaPõõ\Ó\≈\ÍbΩ9ø:ˇs£Æﬁüo.V¸≠ﬁ≠~ªX≠\Ÿ|A,\∆\\lZN\÷\ZB9\Ì\Ã\Ã\ri≤\∆bYT\’TçhôÆﬁ†\”ù\n®\Ë±[gè\≈n\‹Nj\ﬁ\⁄zR{ç9†(£˝M◊úøßÃ¢O åÆ\÷X¨°9pf‘∏¸\ZÇ¡\Ì_\ÎÄ≤ G¿BDBOe¿0\⁄\ƒ\\˝∑ﬂ∂èwøZ#kΩü5\◊}2\Ô\‰d\ﬁŸ™©\Ê¿ôÒ¨ºSøê∑=ñ∑ïıÜE\ﬁe\Ÿ*©r\‡\ÃxN\ﬁáÖºanãåÙ\"c\Ád\Ÿ*Ir\‡\ÃxN\∆Œ•y\∆	d\ ÛJ˜íW˚WDGèèNk¡CVcV=-	Ù\’+{8ÕÄ\€˛76Eµ\≈eJΩ|ˆûWñÛ:ˆ\‹\‹Ù)dÙëõã8l\„8o{ãr\ÀIüÚpü\'Oj\„(≥\⁄~\ËM\‡Ú\Í€ø;ı˙~wwªì%?1\…Ñ\Êòœà\Ìèq¸i_ó@n/\«Cææ\«˚ù∫º?íf\Ôg98öxw.\ÀY∏\r\Ê≠˝v˜x˝\„\·z˚()—îÇ¢\ÁùÚhÇá\Œ\—tFÍãºAaÁêß\“O˚\“.`pb}\€$~\Ÿ`£Li\÷mã^§\‚2É¥GÖC*\÷√îg,zë\Zºvvè\Í©!iSûæ\ËE\Í`µÛ{TH¢∂ö\ÂÃ†\›~U\√¨”∂Tµ\ËE*nknø™Òê\Í\—[™ZÙD≠”íà/	\rA\”˚∫\Óf\r/éwof\”˙â[ëû≤\0OY@Å á,>†î\Ìj®\€6M”åö\≈<#Wù+\Ÿ\’&π\ÍÄgWqrïQÀûi\ÿ(\ÕZ˙¸4≠ˆ\‰™ıÀÆV¿≥\À\Î\«\«€á≠∫Ÿ©≥K´v7\€_‹†p\Âó\–\–9í\ÃOï˝MU7\Ì\Ës0Æo∞\‘`© ˙\Èz\Z\‰î&Pj†TA\Ìz\Z\‰3(5ê5∏∂[3É*8-CZ¥5x‘¥˘å\Ëã\Œ\«7∆î©k\Ã!6¨\Ã(ò±r|ßi}°\≈FAÉâ&\ƒi\⁄¿4ê¥§ìfìQ \ƒifb&C\Œi:\'\‚z[ç1L\‚(©ß†C2~\ \ËÜ\"Æì\\WX\”\r\‰mèm!:A^CïÜvãD^(B\ƒ^õ4€åB,qy\›8\”k™VüNïX\÷c\÷\ƒ!B÷à#P^C\rCH+ê•äç-nx\n\Œ6@\√Tämb\«+ß!4(\Z1õÃö\Ë\‚ñG†ºÄ<V\ZáÄæ \‰\Ì™≥Œ†#uç9\¬\nWB£Ä\∆\nÚM,\‡R¡≈Üs\Á&úßq¿+ß!óto1õÃö\ËnΩ?B,ˇÜ¡_,æ_-æ_\œﬁ¨≠˙ºk\ﬂŸü¯Æ^?\·\ŒW¯˘]ı¶˙\nendstream\nendobj\n49 0 obj\n<</PatternType 2/Shading 92 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n33 0 obj\n<</PatternType 2/Shading 93 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n63 0 obj\n<</PatternType 2/Shading 94 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n9 0 obj\n<</PatternType 2/Shading 95 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n75 0 obj\n<</PatternType 2/Shading 96 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n17 0 obj\n<</PatternType 2/Shading 97 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n69 0 obj\n<</PatternType 2/Shading 98 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n7 0 obj\n<</PatternType 2/Shading 99 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n31 0 obj\n<</PatternType 2/Shading 100 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n23 0 obj\n<</PatternType 2/Shading 101 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n25 0 obj\n<</PatternType 2/Shading 102 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n67 0 obj\n<</PatternType 2/Shading 103 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n19 0 obj\n<</PatternType 2/Shading 104 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n53 0 obj\n<</PatternType 2/Shading 105 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n27 0 obj\n<</PatternType 2/Shading 106 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n57 0 obj\n<</PatternType 2/Shading 107 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n35 0 obj\n<</PatternType 2/Shading 108 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n41 0 obj\n<</PatternType 2/Shading 109 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n79 0 obj\n<</PatternType 2/Shading 110 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n39 0 obj\n<</PatternType 2/Shading 111 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n83 0 obj\n<</PatternType 2/Shading 112 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n29 0 obj\n<</PatternType 2/Shading 113 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n5 0 obj\n<</PatternType 2/Shading 114 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n73 0 obj\n<</PatternType 2/Shading 115 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n71 0 obj\n<</PatternType 2/Shading 116 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n51 0 obj\n<</PatternType 2/Shading 117 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n59 0 obj\n<</PatternType 2/Shading 118 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n81 0 obj\n<</PatternType 2/Shading 119 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n65 0 obj\n<</PatternType 2/Shading 120 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n13 0 obj\n<</PatternType 2/Shading 121 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n55 0 obj\n<</PatternType 2/Shading 122 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n85 0 obj\n<</PatternType 2/Shading 123 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n43 0 obj\n<</PatternType 2/Shading 124 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n11 0 obj\n<</PatternType 2/Shading 125 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n47 0 obj\n<</PatternType 2/Shading 126 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n21 0 obj\n<</PatternType 2/Shading 127 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n61 0 obj\n<</PatternType 2/Shading 128 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n37 0 obj\n<</PatternType 2/Shading 129 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n15 0 obj\n<</PatternType 2/Shading 130 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n77 0 obj\n<</PatternType 2/Shading 131 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n129 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[422.04 300 424 300]/Function 36 0 R/Extend[true true]>>\nendobj\n94 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[159.93 300 162 300]/Function 62 0 R/Extend[true true]>>\nendobj\n123 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[439 300 442.56 300]/Function 84 0 R/Extend[true true]>>\nendobj\n93 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[311 300 322 300]/Function 32 0 R/Extend[true true]>>\nendobj\n95 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[89 300 99 300]/Function 8 0 R/Extend[true true]>>\nendobj\n119 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[428 300 429 300]/Function 80 0 R/Extend[true true]>>\nendobj\n120 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[162 300 163 300]/Function 64 0 R/Extend[true true]>>\nendobj\n113 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[307.8 300 310 300]/Function 28 0 R/Extend[true true]>>\nendobj\n112 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[429 300 439 300]/Function 82 0 R/Extend[true true]>>\nendobj\n116 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[318.42 300 320 300]/Function 70 0 R/Extend[true true]>>\nendobj\n114 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[85.2 300 87 300]/Function 4 0 R/Extend[true true]>>\nendobj\n122 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[134.44 300 136 300]/Function 54 0 R/Extend[true true]>>\nendobj\n96 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[322 300 332 300]/Function 74 0 R/Extend[true true]>>\nendobj\n106 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[154 300 157.04 300]/Function 26 0 R/Extend[true true]>>\nendobj\n131 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[332 300 335.05 300]/Function 76 0 R/Extend[true true]>>\nendobj\n130 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[114 300 116 300]/Function 14 0 R/Extend[true true]>>\nendobj\n101 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[141 300 143 300]/Function 22 0 R/Extend[true true]>>\nendobj\n111 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[424 300 426 300]/Function 38 0 R/Extend[true true]>>\nendobj\n107 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[136 300 138 300]/Function 56 0 R/Extend[true true]>>\nendobj\n121 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[112.29 300 114 300]/Function 12 0 R/Extend[true true]>>\nendobj\n100 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[310 300 311 300]/Function 30 0 R/Extend[true true]>>\nendobj\n118 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[138 300 148 300]/Function 58 0 R/Extend[true true]>>\nendobj\n98 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[173 300 176.56 300]/Function 68 0 R/Extend[true true]>>\nendobj\n92 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[111 300 112 300]/Function 48 0 R/Extend[true true]>>\nendobj\n128 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[148 300 151.07 300]/Function 60 0 R/Extend[true true]>>\nendobj\n102 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[143 300 154 300]/Function 24 0 R/Extend[true true]>>\nendobj\n104 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[126 300 129.96 300]/Function 18 0 R/Extend[true true]>>\nendobj\n127 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[139.38 300 141 300]/Function 20 0 R/Extend[true true]>>\nendobj\n124 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[436 300 439.71 300]/Function 42 0 R/Extend[true true]>>\nendobj\n125 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[99 300 102.87 300]/Function 10 0 R/Extend[true true]>>\nendobj\n97 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[116 300 126 300]/Function 16 0 R/Extend[true true]>>\nendobj\n115 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[320 300 322 300]/Function 72 0 R/Extend[true true]>>\nendobj\n99 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[87 300 89 300]/Function 6 0 R/Extend[true true]>>\nendobj\n109 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[426 300 436 300]/Function 40 0 R/Extend[true true]>>\nendobj\n110 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[425.93 300 428 300]/Function 78 0 R/Extend[true true]>>\nendobj\n108 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[322 300 325.47 300]/Function 34 0 R/Extend[true true]>>\nendobj\n126 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[108.95 300 111 300]/Function 46 0 R/Extend[true true]>>\nendobj\n117 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[112 300 122 300]/Function 50 0 R/Extend[true true]>>\nendobj\n105 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[122 300 125.57 300]/Function 52 0 R/Extend[true true]>>\nendobj\n103 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[163 300 173 300]/Function 66 0 R/Extend[true true]>>\nendobj\n44 0 obj\n<</CA 1>>\nendobj\n86 0 obj\n<</CA 1>>\nendobj\n88 0 obj\n<</Type/Pages/Count 2/Kids[89 0 R 91 0 R]>>\nendobj\n132 0 obj\n<</Type/Catalog/Pages 88 0 R>>\nendobj\n133 0 obj\n<</Producer(iTextÆ 5.5.13.2 ©2000-2020 iText Group NV \\(AGPL-version\\))/CreationDate(D:20220102013030+02\'00\')/ModDate(D:20220102013030+02\'00\')>>\nendobj\nxref\n0 134\n0000000000 65535 f \n0000006037 00000 n \n0000004222 00000 n \n0000004315 00000 n \n0000000015 00000 n \n0000009177 00000 n \n0000000098 00000 n \n0000008114 00000 n \n0000000181 00000 n \n0000007835 00000 n \n0000000276 00000 n \n0000009957 00000 n \n0000000372 00000 n \n0000009673 00000 n \n0000000456 00000 n \n0000010312 00000 n \n0000000540 00000 n \n0000007974 00000 n \n0000000636 00000 n \n0000008467 00000 n \n0000000732 00000 n \n0000010099 00000 n \n0000000816 00000 n \n0000008254 00000 n \n0000000900 00000 n \n0000008325 00000 n \n0000000996 00000 n \n0000008609 00000 n \n0000001092 00000 n \n0000009106 00000 n \n0000001170 00000 n \n0000008183 00000 n \n0000001248 00000 n \n0000007695 00000 n \n0000001332 00000 n \n0000008751 00000 n \n0000001416 00000 n \n0000010241 00000 n \n0000001500 00000 n \n0000008964 00000 n \n0000001584 00000 n \n0000008822 00000 n \n0000001680 00000 n \n0000009886 00000 n \n0000015094 00000 n \n0000004403 00000 n \n0000001776 00000 n \n0000010028 00000 n \n0000001860 00000 n \n0000007625 00000 n \n0000001944 00000 n \n0000009389 00000 n \n0000002040 00000 n \n0000008538 00000 n \n0000002136 00000 n \n0000009744 00000 n \n0000002220 00000 n \n0000008680 00000 n \n0000002304 00000 n \n0000009460 00000 n \n0000002400 00000 n \n0000010170 00000 n \n0000002496 00000 n \n0000007765 00000 n \n0000002580 00000 n \n0000009602 00000 n \n0000002664 00000 n \n0000008396 00000 n \n0000002760 00000 n \n0000008044 00000 n \n0000002856 00000 n \n0000009318 00000 n \n0000002934 00000 n \n0000009247 00000 n \n0000003012 00000 n \n0000007904 00000 n \n0000003096 00000 n \n0000010383 00000 n \n0000003180 00000 n \n0000008893 00000 n \n0000003264 00000 n \n0000009531 00000 n \n0000003348 00000 n \n0000009035 00000 n \n0000003444 00000 n \n0000009815 00000 n \n0000015120 00000 n \n0000003540 00000 n \n0000015146 00000 n \n0000003729 00000 n \n0000003877 00000 n \n0000004107 00000 n \n0000013119 00000 n \n0000010807 00000 n \n0000010572 00000 n \n0000010921 00000 n \n0000011844 00000 n \n0000013937 00000 n \n0000013002 00000 n \n0000014166 00000 n \n0000012772 00000 n \n0000012309 00000 n \n0000013351 00000 n \n0000014979 00000 n \n0000013466 00000 n \n0000014861 00000 n \n0000011958 00000 n \n0000012539 00000 n \n0000014510 00000 n \n0000014277 00000 n \n0000014392 00000 n \n0000012424 00000 n \n0000011379 00000 n \n0000011262 00000 n \n0000011612 00000 n \n0000014051 00000 n \n0000011494 00000 n \n0000014746 00000 n \n0000012887 00000 n \n0000011032 00000 n \n0000011147 00000 n \n0000012654 00000 n \n0000011726 00000 n \n0000010689 00000 n \n0000013702 00000 n \n0000013820 00000 n \n0000014628 00000 n \n0000013584 00000 n \n0000013233 00000 n \n0000010454 00000 n \n0000012194 00000 n \n0000012076 00000 n \n0000015206 00000 n \n0000015254 00000 n \ntrailer\n<</Size 134/Root 132 0 R/Info 133 0 R/ID [<c676f7d12fd2a63e3035fe447d89279c><c676f7d12fd2a63e3035fe447d89279c>]>>\n%iText-5.5.13.2\nstartxref\n15416\n%%EOF\n'),(1,2022,'E_SOUTH',_binary '%PDF-1.4\n%\‚\„\œ\”\n4 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 1 1]/N 1>>\nendobj\n6 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n8 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 0.47451 0.47451]/N 1>>\nendobj\n10 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.47451 0.47451]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n14 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 1 1]/N 1>>\nendobj\n16 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 1 1]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n18 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.33333 0.33333]/C1[1 0.47451 0.47451]/N 1>>\nendobj\n20 0 obj\n<</FunctionType 2/Domain[0 1]/C0[1 0.47451 0.47451]/C1[1 0.33333 0.33333]/N 1>>\nendobj\n23 0 obj\n<</Length 121/Filter/FlateDecode>>stream\nxú+\‰*T0T0\0BCc33CÖ\‰\\˝à4Có|Ö@TYCC3®¨D(\œ\Â\¬î≤00SI\·2P\–5¥\01Ù›åçB“∏4KKRãr*ÇRÚãJä5C≤∏@F\"+\◊P@56∞\ƒeú	˙]CÄN\0à´1¡\nendstream\nendobj\n25 0 obj\n<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F2 3 0 R>>/XObject<</Xf1 1 0 R/Xf2 13 0 R>>>>/Contents 23 0 R/Parent 24 0 R>>\nendobj\n26 0 obj\n<</Length 109/Filter/FlateDecode>>stream\nxú+\‰r\n\·26S∞00SI\·2P\–5¥\01Ù›åçB“∏4ÇRãKKãÛJ<ÛíÛsSÇRÚãJä5C≤Ä\ZPÙi¯\Ê\ÁïdXı\Í\'\Ê\‰d+d¶+ö\Z\0Åûí\◊Æ@.\0I\€!\nendstream\nendobj\n27 0 obj\n<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F2 3 0 R>>>>/Contents 26 0 R/Parent 24 0 R>>\nendobj\n2 0 obj\n<</Type/Font/Subtype/Type1/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Type/Font/Subtype/Type1/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n13 0 obj\n<</Type/XObject/Subtype/Form/Resources<</Font<</F1 2 0 R/F2 3 0 R>>/Pattern<</P5 15 0 R/P6 17 0 R/P7 19 0 R/P8 21 0 R>>/ExtGState<</GS2 22 0 R>>>>/BBox[0 0 500 300]/FormType 1/Matrix [1 0 0 1 0 0]/Length 789/Filter/FlateDecode>>stream\nxúïñKo\€0Ä\Ô˙<v¿™H¥d…ªu@V`Xã6q7\≈Eóæ\‡fhìn§ñ\„%äü)íí¸\"TJ¡3µ\n:aUiyº\‚õX\r\¬göı$¥Ç3q˝C¡OÒ“õÉå\»ˇª~^\Ô0ª#äç>∂bˆI*h\Ô≈£\Zúë\–y\Ÿ4\–>ãc%µØÑˆV-\Ê\Àˆ\‰jqr\ﬁ\¬\Â\’I;_|˘ã˘\◊˘˘\’¸]˚D`ö4oãW®+ÜU\“xÚ\r}\›˜∫\‘\”,ÖYE\ 3äØãSR@îM%πÀ†\—d\«Y\Zçˆña<\ÕSh˘≤\‚_\ﬂˇ{VK4lÄ∂\Ôu©\ÁS\'L\‹\È\‰ya≠¢ç€úç\√ 6DpJÅí™\·?˚µ\ﬁ>|\–8åµí\Œbc`\‹\⁄7Ä5Ø\‘x§.HéL∫\‹FMt\”RY“ø\“M\r„ñí¿V&ùIÒÙ\ÏjàOß\«1åä⁄Äµ•ç™b-.o∫\Óqè´\Õ0©¶)\ÁCà•˙Giuß5H+	Ω\⁄lo\ﬁ^o\÷\€!\√\€8\\,èI\ﬂhIÆ\‚,ıH1Åç+∂˘Ø≠Q\–\‘\\|qY\ \≈U°zØî\⁄q¶≠4e52ìºè´ëä†\ZÅ\Õ$òj\ÿ4	\ÂΩ`c§q#p=	6M\‹3éÚ^pM[G`?	v\È`∞\Îw\Ô$òˆ∞n¥lzè}k5I¶tP\‘\"9\ ˚»®≠tzLûLR>LÚ9\…{\…U#M=&O\Ê)!∑HérO\Œ{Ä©«åß¸j\‰\”/l\√\«K\ÿøW\Î∑\’˚∞£ÉØ\"E∑\“mvçÄ.≥x\r§c\√\Ác#.ûÜ˙™\Ê\·AY≥*\◊ePï\¬dUÆ¨†*•≈™\\AUäÉU9πAU≤\Àk\»\Ÿ	K-\ÈaUoPï¯\Œ.n∂\€\’\Î\Zn70ª∞∞π•õX—çIY∞µ\‘d°Mñª áeß9C9\œ\·\„w[G¨0Ω.Lí#ßáõ}4i=\·YTt§\Á˘,GHèº\Î_ö¥B†\—V≈Å\‘\r®>|#3/ü;˚øñ\‚≈§©\¬4U\ÿ\Ïtâpø)\ﬂV\ÎÚÄ7_\“Û)Æ\0Q\nendstream\nendobj\n1 0 obj\n<</Type/XObject/Subtype/Form/Resources<</Font<</F1 2 0 R/F2 3 0 R>>/Pattern<</P1 5 0 R/P2 7 0 R/P3 9 0 R/P4 11 0 R>>/ExtGState<</GS1 12 0 R>>>>/BBox[0 0 500 300]/FormType 1/Matrix [1 0 0 1 0 0]/Length 742/Filter/FlateDecode>>stream\nxúïñKo\€0Ä\Ô˙:vá*ı∞Ω[áeÜum\≈P\Ï¥\ÈnÜ&ˆ˜GR≤\ÂÿÉá$@Dë\‚gä§\‰º-≠\÷Ú\rG-\·uIﬂàgq+6\¬\»?\‰W\\ı*åñ\‚Óßñ\‚Ωs\œ-¢˝=\ƒ\–w˚tÑ\€#k49}™\≈Ïãë†e˝à(\“\ZYT\ K(JUU≤~ßZôJYﬂãì\≈|Yü\›,Œæ\◊Ú˙Ê¨û/æ˝êóã\œÛ\≈ÚC˝äX\\3ØsL`,°¨r%Fe\ËfMöíxUñ\⁄9\“\≈9öúPU%πK∂tãzí\⁄ŒìıiÕêÇõWñ>\›\»ÒYP\‡\»|7k“¨L^x0i\◊Ò.xç\Zém@\Z\ËW\0ÄJPP\Z∞\0Z\È¿Ÿø¯µ\Ÿ?4\–œµVÖá\ \…\·à\ËÄ˚¥SWñ,5,¥a¥µc¥\ƒ0=6%˛jS9±\‰\Â∞p1üEà∫$åu¿∫£S\‡©J\ﬁ\Á&t\⁄ßaπjöóùºzY\Ô˙ôHΩå\’\Óc,\’¬ÇÚ&¶\“;(´\ÿ\À\Î\›~ı{ª\⁄\Ï˚î†S\“P(bk¸3>W\»\¬RÖëz¢âÄŒû≥˚__l.£1°=^U<]®ÏáÇ4£Ω\“6ì<IÖBA5†¬òä˘\–ièIû§zß¨P\Ìò\Í+•\”\Óì<I-ç≤n@uc*^&AYúbÇ.ïf’èòtÕòî\’$OR≠Wvò\’0¶:¥¶¨&π£∂mI\ƒSB\„\ÂaHô8\Ì{π}Xo\⁄˙\»SL\ÔçØ¿\◊\ Hê¸Vâ7r:¡e{ÇcÅP’µ©{}F¶∂WÿîõÖLm¡Ÿî+N¶T5∂te£0\€\‘Ûnr\Ó\…\‘\ÊèM9Å≥´\’~ø\ﬁn\‰˝NŒÆå\‹\›oD\È\Ó\Õ«á∞InX\Ê}Òä,µv∫\ÊÅ¿¿\Ã@^\»\ÎIfJ\Ó?mL≥ërt∂\“\›zí#§√Ü©\ÿ\”2sÇ*;è8cPá\Ó?èà◊Ωø\”ˇH\‚ıo∞y6\œ\Ï|i\‰\”.øÅèl\ƒ\ÂOæ\∆\Ô_£E\‡$\nendstream\nendobj\n9 0 obj\n<</PatternType 2/Shading 28 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n5 0 obj\n<</PatternType 2/Shading 29 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n11 0 obj\n<</PatternType 2/Shading 30 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n7 0 obj\n<</PatternType 2/Shading 31 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n17 0 obj\n<</PatternType 2/Shading 32 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n21 0 obj\n<</PatternType 2/Shading 33 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n15 0 obj\n<</PatternType 2/Shading 34 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n19 0 obj\n<</PatternType 2/Shading 35 0 R/Matrix[1 0 0 1 0 0]>>\nendobj\n28 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[162 300 390 300]/Function 8 0 R/Extend[true true]>>\nendobj\n33 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[396 300 468.05 300]/Function 20 0 R/Extend[true true]>>\nendobj\n30 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[390 300 466.8 300]/Function 10 0 R/Extend[true true]>>\nendobj\n31 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[123 300 162 300]/Function 6 0 R/Extend[true true]>>\nendobj\n29 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[85.2 300 123 300]/Function 4 0 R/Extend[true true]>>\nendobj\n35 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[181 300 396 300]/Function 18 0 R/Extend[true true]>>\nendobj\n32 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[145 300 181 300]/Function 16 0 R/Extend[true true]>>\nendobj\n34 0 obj\n<</ShadingType 2/ColorSpace/DeviceRGB/Coords[108.95 300 145 300]/Function 14 0 R/Extend[true true]>>\nendobj\n12 0 obj\n<</CA 1>>\nendobj\n22 0 obj\n<</CA 1>>\nendobj\n24 0 obj\n<</Type/Pages/Count 2/Kids[25 0 R 27 0 R]>>\nendobj\n36 0 obj\n<</Type/Catalog/Pages 24 0 R>>\nendobj\n37 0 obj\n<</Producer(iTextÆ 5.5.13.2 ©2000-2020 iText Group NV \\(AGPL-version\\))/CreationDate(D:20220102200024+02\'00\')/ModDate(D:20220102200024+02\'00\')>>\nendobj\nxref\n0 38\n0000000000 65535 f \n0000002593 00000 n \n0000001361 00000 n \n0000001454 00000 n \n0000000015 00000 n \n0000003662 00000 n \n0000000098 00000 n \n0000003801 00000 n \n0000000181 00000 n \n0000003593 00000 n \n0000000276 00000 n \n0000003731 00000 n \n0000005068 00000 n \n0000001542 00000 n \n0000000372 00000 n \n0000004010 00000 n \n0000000456 00000 n \n0000003870 00000 n \n0000000540 00000 n \n0000004080 00000 n \n0000000636 00000 n \n0000003940 00000 n \n0000005094 00000 n \n0000000732 00000 n \n0000005120 00000 n \n0000000921 00000 n \n0000001069 00000 n \n0000001246 00000 n \n0000004150 00000 n \n0000004609 00000 n \n0000004380 00000 n \n0000004496 00000 n \n0000004837 00000 n \n0000004263 00000 n \n0000004951 00000 n \n0000004723 00000 n \n0000005180 00000 n \n0000005227 00000 n \ntrailer\n<</Size 38/Root 36 0 R/Info 37 0 R/ID [<9c0a778f392e85c19da887e374682911><9c0a778f392e85c19da887e374682911>]>>\n%iText-5.5.13.2\nstartxref\n5388\n%%EOF\n');
/*!40000 ALTER TABLE `quarterlyreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `restaurantID` int NOT NULL,
  `restName` varchar(45) NOT NULL,
  `bmBranch` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`restaurantID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (120,'Tals Sandwitches','E_NORTH','Large juicy sandwitches','Carmiel','058-142-4564'),(144,'Shakes Shak','E_NORTH','Shakes healthy!','Haifa','054-555-2144'),(155,'Salli Pies','E_NORTH','Sallies Pies are the best!','Ber Sheva','054-475-5623'),(156,'Zazzi\'s Pizza','E_NORTH','Large pizzas and fries','Afula','058-256-3522'),(170,'BBQ & Hot Dogs','E_NORTH','All the best BBQ','Afula','051-231-4587');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restreports`
--

DROP TABLE IF EXISTS `restreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restreports` (
  `restID` int NOT NULL,
  `month` int NOT NULL,
  `year` int NOT NULL,
  `branch` varchar(45) NOT NULL,
  `dishType` varchar(45) NOT NULL,
  `amount` varchar(45) NOT NULL,
  `total` varchar(45) NOT NULL,
  PRIMARY KEY (`restID`,`month`,`year`,`branch`,`dishType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restreports`
--

LOCK TABLES `restreports` WRITE;
/*!40000 ALTER TABLE `restreports` DISABLE KEYS */;
INSERT INTO `restreports` VALUES (120,1,2022,'E_NORTH','E_APPETIZER','155','2030.6900000000037'),(120,1,2022,'E_NORTH','E_SALAD','2','21.9'),(120,9,2021,'E_NORTH','E_DESSERT','64','1000'),(120,10,2021,'E_NORTH','E_SOUP','4','500'),(120,11,2021,'E_NORTH','E_DESSERT','51','265'),(120,11,2021,'E_NORTH','E_MAIN','23','150'),(120,12,2021,'E_NORTH','E_APPETIZER','165','16906.97000000001'),(120,12,2021,'E_NORTH','E_DRINKS','1','5.0'),(120,12,2021,'E_NORTH','E_SOUP','2','25.98'),(144,1,2022,'E_NORTH','E_APPETIZER','1','10.95'),(155,1,2022,'E_SOUTH','E_APPETIZER','9','107.91');
/*!40000 ALTER TABLE `restreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userID` int NOT NULL,
  `privateName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `defaultBranch` varchar(45) NOT NULL,
  `role` varchar(45) DEFAULT NULL,
  `userType` varchar(45) NOT NULL,
  `manageRestID` int DEFAULT '0',
  `isLoggedIn` int NOT NULL,
  `accountStatus` varchar(45) NOT NULL,
  `W4CNumber` int NOT NULL,
  `businessName` varchar(45) NOT NULL,
  `businessW4CNumber` int NOT NULL,
  `businessAmountUsed` double NOT NULL DEFAULT '0',
  `businessMonthlyLimit` double NOT NULL DEFAULT '0',
  `credits` varchar(100) DEFAULT NULL,
  `cardNumber` varchar(45) DEFAULT NULL,
  `restName` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `isGroupOrder` int DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'asaf','fas','asaf','a','a','151','E_NORTH','HR for intel','E_UNAPPROVED_BUSINESS_USER',0,0,'E_ACTIVE',1499,'Eli Robots',1687,0,150,'0','1001-1542-1522-4587','NA','NA','NA',1),(123,'asaf','dovid','a','a','asaf@dovid','0541241245','E_NORTH','user','E_REG_USER',0,0,'E_ACTIVE',1234,'NA',0,0,0,'150-29.53, 120-3903.8050000000003','0554-1254-1244-3541','NA','NA','NA',0),(124,'tal','levinzon','t','t','tal@levi','0441218745','E_NORTH','Restaurant Manager','E_RESTAURANT_USER',120,0,'E_ACTIVE',1451,'NA',0,0,0,'150-5.00','4785-5682-1245-3325','Tals Sandwitches','NA','NA',0),(125,'sender','hodik','s','s','sender@hodik','0412325887','E_NORTH','Software Engineer Intel','E_UNAPPROVED_BUSINESS_USER',0,0,'E_ACTIVE',1500,'Eli & Brothers',5003,0,300,'120-50.00','4111-5241-5784-6598','NA','NA','NA',0),(126,'eli','arnson','e','e','eli@arnson','0584478546','E_NORTH','Branch manager','E_BRANCH_MAN_USER',0,0,'E_ACTIVE',1478,'PEPSI',989,0,200,'120-50.00','4152-3562-4569-8794','NA','NA','NA',0),(127,'almog','loyoda','ab','a','almog@loyad','0541232654','E_NORTH','HR intel','E_HR',0,0,'E_ACTIVE',1498,'PEPSI',989,0,150,'120-30.00','4152-3562-4569-9874','NA','NA','NA',0),(145,'tal','talush','tt','tt','tt','147','E_NORTH','Looser','E_BUSINESS_USER',0,0,'E_ACTIVE',0,'NA',0,120,120,'0','1452-5478-8521-7845','Tals Sandwitches','NA','NA',0),(424,'eli','a','eli','e','e','152','E_NORTH','user','E_CEO_USER',0,0,'E_ACTIVE',0,'NA',0,0,0,'0','1452-4785-9856-9852','NA','NA','NA',0);
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

-- Dump completed on 2022-01-04 15:49:11
