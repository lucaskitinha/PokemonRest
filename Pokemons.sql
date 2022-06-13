-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: pokemon
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `next_evolution`
--

DROP TABLE IF EXISTS `next_evolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `next_evolution` (
  `id_next_evol` int NOT NULL AUTO_INCREMENT,
  `id` int NOT NULL,
  `num` varchar(4) NOT NULL,
  `next1_name` varchar(20) DEFAULT NULL,
  `next1_num` varchar(4) DEFAULT NULL,
  `next2_name` varchar(20) DEFAULT NULL,
  `next2_num` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id_next_evol`),
  KEY `id` (`id`),
  CONSTRAINT `next_evolution_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pokemons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `next_evolution`
--

LOCK TABLES `next_evolution` WRITE;
/*!40000 ALTER TABLE `next_evolution` DISABLE KEYS */;
INSERT INTO `next_evolution` VALUES (2,2,'001','Ivysauro','002','Venusauro','003'),(3,3,'006','Charizard','007',NULL,NULL),(4,4,'008','Wartotle','009','Blastoise','010'),(5,5,'011',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `next_evolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pokemons`
--

DROP TABLE IF EXISTS `pokemons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num` varchar(4) NOT NULL,
  `nome` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemons`
--

LOCK TABLES `pokemons` WRITE;
/*!40000 ALTER TABLE `pokemons` DISABLE KEYS */;
INSERT INTO `pokemons` VALUES (2,'001','Bulbassauro'),(3,'006','Charmeleon'),(4,'008','Squirtle'),(5,'011','Arbok');
/*!40000 ALTER TABLE `pokemons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prev_evolution`
--

DROP TABLE IF EXISTS `prev_evolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prev_evolution` (
  `id_prev_evol` int NOT NULL AUTO_INCREMENT,
  `id` int NOT NULL,
  `num` varchar(4) NOT NULL,
  `prev1_name` varchar(20) DEFAULT NULL,
  `prev1_num` varchar(4) DEFAULT NULL,
  `prev2_name` varchar(20) DEFAULT NULL,
  `prev2_num` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id_prev_evol`),
  KEY `id` (`id`),
  CONSTRAINT `prev_evolution_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pokemons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prev_evolution`
--

LOCK TABLES `prev_evolution` WRITE;
/*!40000 ALTER TABLE `prev_evolution` DISABLE KEYS */;
INSERT INTO `prev_evolution` VALUES (2,2,'001',NULL,NULL,NULL,NULL),(3,3,'006','Charmander','005',NULL,NULL),(4,4,'008',NULL,NULL,NULL,NULL),(5,5,'011',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `prev_evolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo`
--

DROP TABLE IF EXISTS `tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo` (
  `id_tipo` int NOT NULL AUTO_INCREMENT,
  `id` int NOT NULL,
  `num` varchar(4) NOT NULL,
  `tipo1` varchar(20) DEFAULT NULL,
  `tipo2` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_tipo`),
  KEY `id` (`id`),
  CONSTRAINT `tipo_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pokemons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo`
--

LOCK TABLES `tipo` WRITE;
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
INSERT INTO `tipo` VALUES (2,2,'001','Grass','Poison'),(3,3,'006','Fire','Poison'),(4,4,'008','Water',NULL),(5,5,'011','Poison',NULL);
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'pokemon'
--

--
-- Dumping routines for database 'pokemon'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-13 17:19:05
