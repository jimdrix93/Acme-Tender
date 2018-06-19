start transaction;

drop database if exists `acme-tender`;
create database `acme-tender`;

use `acme-tender`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
  on `acme-tender`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
    create temporary tables, lock tables, create view, create routine, 
    alter routine, execute, trigger, show view
  on `acme-tender`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-tender
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (18,22),(18,23),(18,24),(18,25),(18,26);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrative`
--

DROP TABLE IF EXISTS `administrative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrative` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aay5wgdadcfs7mmw5aa4aw4tk` (`userAccount_id`),
  CONSTRAINT `FK_aay5wgdadcfs7mmw5aa4aw4tk` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrative`
--

LOCK TABLES `administrative` WRITE;
/*!40000 ALTER TABLE `administrative` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrativerequest`
--

DROP TABLE IF EXISTS `administrativerequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrativerequest` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `accepted` bit(1) DEFAULT NULL,
  `maxAcceptanceDate` date DEFAULT NULL,
  `maxDeliveryDate` date DEFAULT NULL,
  `rejectedReason` varchar(255) DEFAULT NULL,
  `subSectionDescription` varchar(255) DEFAULT NULL,
  `subSectionTitle` varchar(255) DEFAULT NULL,
  `administrative_id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dnosbdjnbi4ohj0ir3ugl3ueq` (`administrative_id`),
  KEY `FK_qga7alu72m552ktr13wo3qfbw` (`offer_id`),
  CONSTRAINT `FK_qga7alu72m552ktr13wo3qfbw` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_dnosbdjnbi4ohj0ir3ugl3ueq` FOREIGN KEY (`administrative_id`) REFERENCES `administrative` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrativerequest`
--

LOCK TABLES `administrativerequest` WRITE;
/*!40000 ALTER TABLE `administrativerequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrativerequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (18,0,'Acme Tender.','admin@acme.com','Administrador','658357522','Del sistema',17);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `writingDate` datetime DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_75tu81nx3am1ltecff6xi7pov` (`comment_id`),
  CONSTRAINT `FK_75tu81nx3am1ltecff6xi7pov` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `fatherCategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8dbpkngc2chtdg1xbd67fu6s0` (`fatherCategory_id`),
  CONSTRAINT `FK_8dbpkngc2chtdg1xbd67fu6s0` FOREIGN KEY (`fatherCategory_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (19,0,'Categorías no clasificables','Otras categorías',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collaborationrequest`
--

DROP TABLE IF EXISTS `collaborationrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collaborationrequest` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `accepted` bit(1) DEFAULT NULL,
  `benefitsPercentage` double DEFAULT NULL,
  `maxAcceptanceDate` date DEFAULT NULL,
  `maxDeliveryDate` date DEFAULT NULL,
  `numberOfPages` int(11) DEFAULT NULL,
  `rejectedReason` varchar(255) DEFAULT NULL,
  `requirements` varchar(255) DEFAULT NULL,
  `section` varchar(255) DEFAULT NULL,
  `subSectionDescription` varchar(255) DEFAULT NULL,
  `subSectionTitle` varchar(255) DEFAULT NULL,
  `commercial_id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8bms7pkvd6x774mtqnsrhgth1` (`commercial_id`),
  KEY `FK_g9p2dxrwpe7fpcau36n63gmam` (`offer_id`),
  CONSTRAINT `FK_g9p2dxrwpe7fpcau36n63gmam` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_8bms7pkvd6x774mtqnsrhgth1` FOREIGN KEY (`commercial_id`) REFERENCES `commercial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collaborationrequest`
--

LOCK TABLES `collaborationrequest` WRITE;
/*!40000 ALTER TABLE `collaborationrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `collaborationrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `writingDate` datetime DEFAULT NULL,
  `commercial_id` int(11) NOT NULL,
  `tender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lcrsfhi3pntodnfpansnuhfi1` (`commercial_id`),
  KEY `FK_l4bewfe8gd7ux6rjjsow88alt` (`tender_id`),
  CONSTRAINT `FK_l4bewfe8gd7ux6rjjsow88alt` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_lcrsfhi3pntodnfpansnuhfi1` FOREIGN KEY (`commercial_id`) REFERENCES `commercial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commercial`
--

DROP TABLE IF EXISTS `commercial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commercial` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aooceqmji9tkcno8ckwogfku2` (`userAccount_id`),
  CONSTRAINT `FK_aooceqmji9tkcno8ckwogfku2` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commercial`
--

LOCK TABLES `commercial` WRITE;
/*!40000 ALTER TABLE `commercial` DISABLE KEYS */;
/*!40000 ALTER TABLE `commercial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companyresult`
--

DROP TABLE IF EXISTS `companyresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `companyresult` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `economicalOffer` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tenderResult_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f64m8lx5t0l2ki3k2e87j88uw` (`tenderResult_id`),
  CONSTRAINT `FK_f64m8lx5t0l2ki3k2e87j88uw` FOREIGN KEY (`tenderResult_id`) REFERENCES `tenderresult` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companyresult`
--

LOCK TABLES `companyresult` WRITE;
/*!40000 ALTER TABLE `companyresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `companyresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `benefitsPercentage` double NOT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `welcomeMessageEn` varchar(255) DEFAULT NULL,
  `welcomeMessageEs` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (32,0,'images/logo.png',10,'Acme Tender','Your place to win tenders!','Tu sitio para ganar licitaciones');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `identificationNumber` varchar(255) DEFAULT NULL,
  `minSalaryExpectation` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `subSection_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_no2vfxw1s7dkbwgn8lm8p6xx2` (`subSection_id`),
  CONSTRAINT `FK_no2vfxw1s7dkbwgn8lm8p6xx2` FOREIGN KEY (`subSection_id`) REFERENCES `subsection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluationcriteria`
--

DROP TABLE IF EXISTS `evaluationcriteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluationcriteria` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `maxScore` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `evaluationCriteriaType_id` int(11) NOT NULL,
  `tender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oo41lwlc82yived2a15xftb55` (`evaluationCriteriaType_id`),
  KEY `FK_kob99xtqlyh85007gkh0fyret` (`tender_id`),
  CONSTRAINT `FK_kob99xtqlyh85007gkh0fyret` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_oo41lwlc82yived2a15xftb55` FOREIGN KEY (`evaluationCriteriaType_id`) REFERENCES `evaluationcriteriatype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluationcriteria`
--

LOCK TABLES `evaluationcriteria` WRITE;
/*!40000 ALTER TABLE `evaluationcriteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluationcriteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluationcriteriatype`
--

DROP TABLE IF EXISTS `evaluationcriteriatype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluationcriteriatype` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluationcriteriatype`
--

LOCK TABLES `evaluationcriteriatype` WRITE;
/*!40000 ALTER TABLE `evaluationcriteriatype` DISABLE KEYS */;
INSERT INTO `evaluationcriteriatype` VALUES (20,0,'Criterio en el que la nota resultante proviene de una evaluación subjetiva','Juicio de valor'),(21,0,'Criterio en el que la nota resultante proviene de la aplicación de una fórmula','Aplicación de fórmulas');
/*!40000 ALTER TABLE `evaluationcriteriatype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executive`
--

DROP TABLE IF EXISTS `executive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `executive` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c7856w4yys7am10841m7b0rco` (`userAccount_id`),
  CONSTRAINT `FK_c7856w4yys7am10841m7b0rco` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executive`
--

LOCK TABLES `executive` WRITE;
/*!40000 ALTER TABLE `executive` DISABLE KEYS */;
/*!40000 ALTER TABLE `executive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `data` longblob,
  `mimeType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `uploadDate` datetime DEFAULT NULL,
  `curriculum_id` int(11) DEFAULT NULL,
  `subSection_id` int(11) DEFAULT NULL,
  `tender_id` int(11) DEFAULT NULL,
  `tenderResult_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2qglh3sxjdyxec7eh3whkclo9` (`curriculum_id`),
  KEY `FK_cq9sw5sbqyq30p57h2wvimqjb` (`subSection_id`),
  KEY `FK_frnv5588nw0c67lr5x9951hr` (`tender_id`),
  KEY `FK_syk1c3xtus8flb49xjliyc2fc` (`tenderResult_id`),
  CONSTRAINT `FK_syk1c3xtus8flb49xjliyc2fc` FOREIGN KEY (`tenderResult_id`) REFERENCES `tenderresult` (`id`),
  CONSTRAINT `FK_2qglh3sxjdyxec7eh3whkclo9` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_cq9sw5sbqyq30p57h2wvimqjb` FOREIGN KEY (`subSection_id`) REFERENCES `subsection` (`id`),
  CONSTRAINT `FK_frnv5588nw0c67lr5x9951hr` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `systemFolder` bit(1) NOT NULL,
  `parentFolder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l1kp977466ddsv762wign7kdh` (`name`),
  KEY `FK_qshunmxjrgbdahcn1eu357sxt` (`parentFolder_id`),
  CONSTRAINT `FK_qshunmxjrgbdahcn1eu357sxt` FOREIGN KEY (`parentFolder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (22,0,'inbox','',NULL),(23,0,'outbox','',NULL),(24,0,'notificationbox','',NULL),(25,0,'trashbox','',NULL),(26,0,'spambox','',NULL);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_mymessage`
--

DROP TABLE IF EXISTS `folder_mymessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_mymessage` (
  `Folder_id` int(11) NOT NULL,
  `mymessages_id` int(11) NOT NULL,
  KEY `FK_hffeps88yjyqb5cbb62jx9xad` (`mymessages_id`),
  KEY `FK_pwfcqwrpnbb9naaakv66u04c` (`Folder_id`),
  CONSTRAINT `FK_pwfcqwrpnbb9naaakv66u04c` FOREIGN KEY (`Folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_hffeps88yjyqb5cbb62jx9xad` FOREIGN KEY (`mymessages_id`) REFERENCES `mymessage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_mymessage`
--

LOCK TABLES `folder_mymessage` WRITE;
/*!40000 ALTER TABLE `folder_mymessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `folder_mymessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mymessage`
--

DROP TABLE IF EXISTS `mymessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mymessage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `broadcast` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_26ke162jmaaft55uyxoqkfup3` (`subject`,`body`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mymessage`
--

LOCK TABLES `mymessage` WRITE;
/*!40000 ALTER TABLE `mymessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `mymessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `amount` double NOT NULL,
  `denialReason` varchar(255) DEFAULT NULL,
  `inDevelopment` bit(1) NOT NULL,
  `presentationDate` datetime DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `commercial_id` int(11) NOT NULL,
  `tender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mfr7jehsspimjl9gk4sin5q4y` (`tender_id`),
  KEY `UK_je3w11gm0scpn1srwud50q16c` (`state`),
  KEY `FK_b1dg09wkbqtcuqfl08alvnvu6` (`commercial_id`),
  CONSTRAINT `FK_mfr7jehsspimjl9gk4sin5q4y` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_b1dg09wkbqtcuqfl08alvnvu6` FOREIGN KEY (`commercial_id`) REFERENCES `commercial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subsection`
--

DROP TABLE IF EXISTS `subsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subsection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `lastReviewDate` datetime DEFAULT NULL,
  `section` varchar(255) DEFAULT NULL,
  `shortDescription` varchar(255) DEFAULT NULL,
  `subsectionOrder` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `administrative_id` int(11) DEFAULT NULL,
  `commercial_id` int(11) DEFAULT NULL,
  `offer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_6bml6hl8f5l7fty8hlff8x5we` (`title`,`section`,`comments`),
  KEY `FK_pbtgy8om4vlrxudb94h1drpc1` (`administrative_id`),
  KEY `FK_9jwqbrpmohljoy0sa52xxcxdl` (`commercial_id`),
  KEY `FK_a54qirp64w5st9mgxejkpfsul` (`offer_id`),
  CONSTRAINT `FK_a54qirp64w5st9mgxejkpfsul` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_9jwqbrpmohljoy0sa52xxcxdl` FOREIGN KEY (`commercial_id`) REFERENCES `commercial` (`id`),
  CONSTRAINT `FK_pbtgy8om4vlrxudb94h1drpc1` FOREIGN KEY (`administrative_id`) REFERENCES `administrative` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subsection`
--

LOCK TABLES `subsection` WRITE;
/*!40000 ALTER TABLE `subsection` DISABLE KEYS */;
/*!40000 ALTER TABLE `subsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subsectionevaluationcriteria`
--

DROP TABLE IF EXISTS `subsectionevaluationcriteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subsectionevaluationcriteria` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `evaluationCriteria_id` int(11) NOT NULL,
  `subSection_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r0f3d9gn50cfbtnygqi5v6wax` (`evaluationCriteria_id`),
  KEY `FK_aim5ikj5d6ibp7w5tjmptpm4m` (`subSection_id`),
  CONSTRAINT `FK_aim5ikj5d6ibp7w5tjmptpm4m` FOREIGN KEY (`subSection_id`) REFERENCES `subsection` (`id`),
  CONSTRAINT `FK_r0f3d9gn50cfbtnygqi5v6wax` FOREIGN KEY (`evaluationCriteria_id`) REFERENCES `evaluationcriteria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subsectionevaluationcriteria`
--

LOCK TABLES `subsectionevaluationcriteria` WRITE;
/*!40000 ALTER TABLE `subsectionevaluationcriteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `subsectionevaluationcriteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabooword`
--

DROP TABLE IF EXISTS `tabooword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabooword` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_dhh5tlth4rbpl63wbqhk691kb` (`text`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabooword`
--

LOCK TABLES `tabooword` WRITE;
/*!40000 ALTER TABLE `tabooword` DISABLE KEYS */;
INSERT INTO `tabooword` VALUES (27,0,'sex'),(28,0,'sexo'),(29,0,'viagra'),(30,0,'cialis'),(31,0,'jes extender');
/*!40000 ALTER TABLE `tabooword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tender`
--

DROP TABLE IF EXISTS `tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tender` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bulletin` varchar(255) DEFAULT NULL,
  `bulletinDate` datetime DEFAULT NULL,
  `estimatedAmount` double NOT NULL,
  `executionTime` int(11) DEFAULT NULL,
  `expedient` varchar(255) DEFAULT NULL,
  `informationPage` varchar(255) DEFAULT NULL,
  `interest` varchar(255) DEFAULT NULL,
  `interestComment` varchar(255) DEFAULT NULL,
  `maxPresentationDate` datetime DEFAULT NULL,
  `observations` varchar(255) DEFAULT NULL,
  `offertable` bit(1) NOT NULL,
  `openingDate` datetime DEFAULT NULL,
  `organism` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `administrative_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `offer_id` int(11) DEFAULT NULL,
  `tenderResult_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5tesmpqnwbfpcbagsufgnsa2j` (`reference`),
  KEY `UK_cwpyb4reg9usocr131d9l0ayb` (`title`,`expedient`,`interestComment`,`informationPage`),
  KEY `FK_sym56x6qs8qifv1etgxk1vwro` (`administrative_id`),
  KEY `FK_7jg93cfjd0a8bdeo7tcqdpr20` (`category_id`),
  KEY `FK_2mniovbnci8kh4t7d5a9pusoj` (`offer_id`),
  KEY `FK_j83m907p0dcc40id1hcgl04v` (`tenderResult_id`),
  CONSTRAINT `FK_j83m907p0dcc40id1hcgl04v` FOREIGN KEY (`tenderResult_id`) REFERENCES `tenderresult` (`id`),
  CONSTRAINT `FK_2mniovbnci8kh4t7d5a9pusoj` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_7jg93cfjd0a8bdeo7tcqdpr20` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_sym56x6qs8qifv1etgxk1vwro` FOREIGN KEY (`administrative_id`) REFERENCES `administrative` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tender`
--

LOCK TABLES `tender` WRITE;
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenderresult`
--

DROP TABLE IF EXISTS `tenderresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenderresult` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tenderDate` datetime DEFAULT NULL,
  `tender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9ewbodmfhyn200lcf73t0twkd` (`tender_id`),
  CONSTRAINT `FK_9ewbodmfhyn200lcf73t0twkd` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenderresult`
--

LOCK TABLES `tenderresult` WRITE;
/*!40000 ALTER TABLE `tenderresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenderresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (17,0,'','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (17,'ADMIN');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 19:31:49

commit;