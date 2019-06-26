-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: yoitgeo
-- ------------------------------------------------------
-- Server version	8.0.16

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

--
-- Table structure for table `geotable`
--

DROP TABLE IF EXISTS `geotable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `geotable` (
  `attr_id` int(11) NOT NULL AUTO_INCREMENT,
  `geo_id` char(2) DEFAULT NULL,
  `name` varchar(6) DEFAULT NULL,
  `comm` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`attr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geotable`
--

LOCK TABLES `geotable` WRITE;
/*!40000 ALTER TABLE `geotable` DISABLE KEYS */;
INSERT INTO `geotable` VALUES (1,'lg','이기대','약 8천만 년 전 격렬했던 안산암질 화산활동으로 분출된 용암과 화산재, 화쇄류가 쌓여 만들어진 다양한 화산암 및 퇴적암 지층들이 파도의 침식으로 발달된 해식애, 파식대지, 해식동굴과 함께 천혜의 절경을 이루고 있다. 해안가를 따라 오륙도까지 이어지는 지오트레일 코스를 통해 구리광산, 돌개구멍, 말꼬리구조, 함각섬석 암맥 등의 다양한 지질 및 지형경관을 감상할 수 있다.'),(2,'MI','몰운대','약 8천만 년 전 백악기말에 쌓인 하부다대포층과 그 후 부산지역 지각의 변형과정을 한눈에 볼 수 있는 명소이다. 과거 몰운도라는 섬이었으나 낙동강에서 공급된 모래가 쌓이면서 육지와 연결된 육계도의 독특한 지형을 나타낸다. 다양한 단층과 단층암, 암맥, 광맥, 마그마성 및 쇄설성암맥, 쳐트편, 사층리, 흔적화석, 과거지진기록 등의 다양한 지질특성을 간직한 지질학의 교과서라 불릴만 한 명소이다.'),(3,'Ds','두송반도','공룡의 전성시대였던 백악기말의 부산지역 고환경과 지사를 한눈에 보여주는 으뜸 명소이다. 특히 과거 지진이 기록된 다양한 산출 상태의 쇄설성암맥과 고지진암이 백미이다. 퇴적층에서는 공룡알 둥지와 파편화석이 나타나고, 이회암, 석화목, 환원점, 석회질 고토양 등의 흥미로운 지질특성들은 높은 학술적 가치와 탐방객의 흥미를 자아낸다.'),(4,'Sd','송도반도','해안가를 따라 다대포층의 퇴적암, 화산활동으로 생성된 화쇄류암, 용암이 흘러 만들어진 현무암, 이들을 관입한 유문암 등의 다양한 암석을 아름다운 해안절경과 함께 감상할 수 있는 명소이다. 공룡골격과 공룡알 둥지화석, 석회질 고토양, 암맥과 광맥, 단층, 연흔, 환원점 등의 독특하고 다양한 지질기록을 감상할 수 있는 트레일코스가 송도해수욕장에서부터 암남공원까지 조성되어 있다.'),(5,'Dd','두도','\"송도반도 남쪽의 무인도로 동백나무, 비쭉이, 해송 등의 다양한 자생식물과 바다 산호, 부산의 상징새인 갈매기가 많이 서식하고 있으며, 수려한 한려해상의 경관을 만끽할 수 있다. 해안절벽을 따라서는 백악기말에 퇴적된 하부다대포층과 화산암들이 절경을 이룬다. 공룡알둥지화석, 부정합, 암맥, 단층, 꽃다발 구조 등의 독특하고 다양한 지질기록을 볼 수 있다.\"'),(6,'Tj','태종대','부산을 대표하는 해안경관지로 백악기말에 호수에서 쌓인 퇴적층이 해수면 상승으로 파도에 의해 침식되어 만들어진 파식대지, 해식애, 해안동굴 등의 암벽해안이 백미이다. 구상혼펠스, 슬럼프구조, 암맥, 단층, 꽃다발구조 등의 다양한 지질기록과 신비스러운 천연암벽화, 자갈마당 등의 경관이 어울린 으뜸명소이다. 해안식물 생태코스, 태종대 전망대, 영도해양문화공간으로 이어지는 지오트레일코스가 개발되어있다.'),(7,'Or','오륙도','부산을 대표하는 해안경관지로 백악기말에 호수에서 쌓인 퇴적층이 해수면 상승으로 파도에 의해 침식되어 만들어진 파식대지, 해식애, 해안동굴 등의 암벽해안이 백미이다. 구상혼펠스, 슬럼프구조, 암맥, 단층, 꽃다발구조 등의 다양한 지질기록과 신비스러운 천연암벽화, 자갈마당 등의 경관이 어울린 으뜸명소이다. 해안식물 생태코스, 태종대 전망대, 영도해양문화공간으로 이어지는 지오트레일코스가 개발되어있다.'),(8,'Nd','낙동강하구','낙동강이 남해바다와 만나 만들어진 현생삼각주로 모래들이 쌓여 만들어진 사주, 사구, 석호 등 아름다운 지형의 명소들이 백미이다. 습지와 철새도래지 명소에는 독특하고 다양한 동식물들이 서식하며, 에코센터에서 아미산전망대로 이어지는 지오트레일 코스가 개발되어있다.'),(9,'Js','장산','약 7천만 년 전 격렬했던 유문암질 화산활동으로 분출된 화산재, 용암, 화쇄류로 이루어진 산이다. 구과상유문암, 유문암질 응회암, 반상유문암 등의 다양한 화산암들과 장산폭포, 돌서렁, 인셀베르그 등의 웅장한 지형이 넘쳐난다. 원형의 산체여서 산으로 조금만 들어가도 도시를 벗어난 느낌을 받을 수 있고, 장산 정상에서 내려다보는 해운대, 광안대교 등의 해안도심 경관이 절정이다.'),(10,'Gj','금정산','약 7천만 년 전 지하에서 마그마가 식어 만들어진 화강암이 융기하여 만들어진 부산 땅의 뿌리를 이루는 산이다. 오랜 세월 비바람에 깎고 다듬어져 만들어진 기암절벽, 토르, 나마, 인셀베르그, 블록스트림 등의 우아한 화강암 지형을 감상할 수 있다. 범어사, 금정 산성 등의 부산의 역사유적과 다양한 산악식물을 감상할 수 있으며, 탐방 중 산정상에서 마실 수 있는 시원한 산성막걸리도 일품이다.'),(11,'Og','구상반려암','\"황령산 일대의 구상반려암은 약 6천만 년 전 지하 깊은 곳에서 마그마가 서서히 굳어 만들어진 암석으로 암석의 표면에서는 가운데의 핵을 중심으로 하여 동심원을 그리며 광물들이 배열된 구상조직을 잘 보여준다. 반려암은 우리나라에서 분포가 매우 적은 희귀암석이며, 구상반려암은 지금까지 아시아에서 유일하게 보고되고 있어 연구가치가 매우 높은 부산국가지질공원의 명소이다.\"'),(12,'By','백양산','약 8천만 년 전 격렬했던 화산활동으로 분출된 물질이 쌓여 만들어진 다양한 화산쇄설암, 화산활동이 일시적으로 중단되었을 때 호수에서 퇴적된 퇴적암, 그리고 지하에서 이들을 관입한 화강암까지 부산의 지질 변천사를 전체적으로 보여준다. 퇴적암의 석회질 토양층이 녹아 만들어진 석회암동굴, 폭포, 돌서렁, 토르, 인셀베르그 등의 독특한 지형을 만끽할 수 있는 트레킹형 명소이다.');
/*!40000 ALTER TABLE `geotable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-26 23:08:18
