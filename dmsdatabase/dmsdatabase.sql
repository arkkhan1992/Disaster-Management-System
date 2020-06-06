-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May. 10, 2020 at 06:58 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dmsdatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth`
--

CREATE TABLE `auth` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `passward` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `auth`
--

INSERT INTO `auth` (`id`, `username`, `passward`, `role`) VALUES
(1, 'admin', 'admin123', '1'),
(2, 'ark', 'ark123', '2'),
(3, 'arslan', '124bd1296bec0d9d93c7b52a71ad8d5b', '2'),
(4, 'nasir21', '202cb962ac59075b964b07152d234b70', '2'),
(5, 'noman', '6531401f9a6807306651b87e44c05751', '2'),
(7, 'Moiz', '202cb962ac59075b964b07152d234b70\r\n', '2'),
(8, 'fa21', '202cb962ac59075b964b07152d234b70', '2'),
(9, '', 'd41d8cd98f00b204e9800998ecf8427e', '2'),
(10, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(11, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(12, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(13, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(14, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(15, 'asdfsdaf', '912ec803b2ce49e4a541068d495ab570', '2'),
(16, 'asdfasdf', '63d0cea9d550e495fde1b81310951bd7', '2'),
(17, 'Mo21', '25d55ad283aa400af464c76d713c07ad', '2'),
(18, 'ahsan', '25d55ad283aa400af464c76d713c07ad', '2'),
(19, 'ahsan', '25d55ad283aa400af464c76d713c07ad', '2'),
(20, 'Ali', '25d55ad283aa400af464c76d713c07ad', '2'),
(21, 'Ali', '25d55ad283aa400af464c76d713c07ad', '2'),
(22, 'afzaal', '25d55ad283aa400af464c76d713c07ad', '2'),
(23, 'moiz', '202cb962ac59075b964b07152d234b70', '2'),
(24, 'moiz', '202cb962ac59075b964b07152d234b70', '2'),
(25, 'moizz', '202cb962ac59075b964b07152d234b70', '2');

-- --------------------------------------------------------

--
-- Table structure for table `Disaster_Help`
--

CREATE TABLE `Disaster_Help` (
  `id` int(11) NOT NULL,
  `location_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `lat` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `long` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Disaster_Help`
--

INSERT INTO `Disaster_Help` (`id`, `location_name`, `lat`, `long`, `type`) VALUES
(1, 'Karsaz', '24.875969', '67.101281', 'disaster_zone'),
(2, 'Drigh_road', '24.885935', '67.125475', 'Help_zone'),
(3, 'shahfaisal_colony', '24.885488', '67.145898', 'disaster_zone'),
(4, 'shahfaisal_town', '24.886247', '67.169062', 'Help_zone'),
(5, 'Malir', '24.930799', '67.198929', 'disaster_zone'),
(6, 'Gulistan_e_Johar', '24.924017', '67.138214', 'Help_zone'),
(7, 'Qayyumabad', '24.827180', '67.078218', 'Help_zone'),
(8, 'Korangi_Area', '24.820636', '67.135871', 'disaster_zone'),
(9, 'Clifton,Karachi', '24.817520', '67.027428', 'disaster_zone'),
(10, 'DHA,Karachi', '24.796640', '67.053166', 'Help_zone'),
(11, 'Dolman Shopping Mall Tariq Road', '24.876819', '67.062947', 'disaster_zone'),
(12, 'Bahadurabad_chowrangi', '24.882327', '67.067203', 'Help_zone'),
(13, 'Chase Up jail Chowrangi Branch', '24.884275', '67.056880', 'disaster_zone'),
(14, 'New Town police Station ', '24.888723', '67.060452', 'Help_zone'),
(15, 'Hill Park ', '24.874011', '67.074025', 'disaster_zone'),
(16, 'Baloch Colony Bus Stop', '24.867183', '67.082752', 'Help_zone'),
(17, 'karachi', '24.860734299999997', '67.0011364', 'disaster_zone'),
(18, 'Multan', '30.157458', '71.5249154', 'Help_zone'),
(19, 'pakistan', '30.375321000000003', '69.34511599999999', 'disaster_zone'),
(20, 'pakistan', '30.375321000000003', '69.34511599999999', 'disaster_zone'),
(21, 'pakistan', '30.375321000000003', '69.34511599999999', 'disaster_zone'),
(22, 'pakistan', '30.375321000000003', '69.34511599999999', 'disaster_zone'),
(23, 'karachi', '24.860734299999997', '67.0011364', 'Help_zone'),
(24, 'karachi', '24.860734299999997', '67.0011364', 'Help_zone'),
(25, 'karachi', '24.860734299999997', '67.0011364', 'Help_zone'),
(26, 'shah faisal town', '24.877286899999998', '67.1590899', 'Help_zone'),
(27, 'frere hall', '24.8475152', '67.03304179999999', 'Help_zone'),
(28, 'karachi', '24.860734299999997', '67.0011364', 'disaster_zone');

-- --------------------------------------------------------

--
-- Table structure for table `family_detail`
--

CREATE TABLE `family_detail` (
  `id` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `Father_name` varchar(50) DEFAULT NULL,
  `father_num` varchar(15) DEFAULT NULL,
  `Mother_name` varchar(50) DEFAULT NULL,
  `mother_num` varchar(15) DEFAULT NULL,
  `other_name` varchar(50) NOT NULL,
  `other_number` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `family_detail`
--

INSERT INTO `family_detail` (`id`, `Email`, `Password`, `contact_no`, `Father_name`, `father_num`, `Mother_name`, `mother_num`, `other_name`, `other_number`) VALUES
(9, 'ark', '6531401f9a6807306651b87e44c05751', '03455723676', 'Abc', '03150279642', 'xyz', '03333312771', 'jkl', '03032290204'),
(10, 'arslan', '202cb962ac59075b964b07152d234b70', '03448883292', 'Arshad', '03455723676', 'hdudb', '03400373776', 'Czn', '03455723676'),
(11, 'nasir21', '202cb962ac59075b964b07152d234b70', '03008287413', 'aghs', '03455723676', 'vdhdb', '03008287413', 'other', '03013330001'),
(12, 'noman', '6531401f9a6807306651b87e44c05751', '03232655308', 'hshdh', '03455723676', 'hdueh', '03008287413', 'hdusb', '03455723676'),
(13, 'Moiz', '202cb962ac59075b964b07152d234b70', '5641', 'abc', '654', 'asdf', '654', 'asdf', '6454'),
(15, 'fa21', '202cb962ac59075b964b07152d234b70', '03455723676', 'avc', '0345572367', 'geyye', '0345572367', 'gydy', '0345572367'),
(16, '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '', '', '', '', ''),
(17, 'asdfsdaf', '63d0cea9d550e495fde1b81310951bd7', '', '', '', '', '', '', ''),
(23, 'asdfasdf', '63d0cea9d550e495fde1b81310951bd7', '12345678901', '', '', '', '', '', ''),
(24, 'Mo21', '25d55ad283aa400af464c76d713c07ad', '03455723676', 'gdydg', '03455723676', 'gdudg', '03455723676', 'vhdg', '03455723676'),
(25, 'ahsan', '25d55ad283aa400af464c76d713c07ad', '03455723676', 'ahsan', '03133698588', 'abcd', '03133698588', 'friend', '03150279642'),
(27, 'Ali', '25d55ad283aa400af464c76d713c07ad', '03455723676', 'ahsan', '03133698588', 'abcd', '03133698588', 'friend', '03150279642'),
(29, 'afzaal', '25d55ad283aa400af464c76d713c07ad', '03455723676', 'Mulazam Hussain ', '03455723676', 'avc', '03455723676', 'cazn', '03455723676'),
(32, 'moizz', '202cb962ac59075b964b07152d234b70', '03150279642', 'abc', '03150279642', 'abc', '03150279642', 'abc', '03150279642');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Disaster_Help`
--
ALTER TABLE `Disaster_Help`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `family_detail`
--
ALTER TABLE `family_detail`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auth`
--
ALTER TABLE `auth`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `Disaster_Help`
--
ALTER TABLE `Disaster_Help`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `family_detail`
--
ALTER TABLE `family_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
