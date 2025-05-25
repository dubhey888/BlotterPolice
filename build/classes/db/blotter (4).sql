-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2025 at 01:37 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blotter`
--

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `report_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `incident_type` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `date_of_incident` date DEFAULT NULL,
  `time_of_incident` time DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  `suspect_description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`report_id`, `u_id`, `full_name`, `incident_type`, `description`, `location`, `date_of_incident`, `time_of_incident`, `status`, `suspect_description`) VALUES
(1, 0, 'Ross Sabio', 'Theft', 'TInakbu ang Cellphone ko', 'Ward 2', '2025-05-12', '01:17:00', 'Resolved', NULL),
(2, 3, 'Jay Reyes', 'Assault', 'Stab in the Stomach', 'Ward 1', '2025-05-03', '14:06:00', 'Pending', NULL),
(3, 3, 'Kayeshe Monggol', 'Theft', 'Tinakbo ang phone ko nag lalakad lang ako', 'Tungkop', '2025-05-08', '03:11:00', 'Resolved', NULL),
(4, 5, 'Mark Pacaldo', 'Vandalism', 'The guy on the black hoodie is vandal my car', 'Tungkop', '2025-05-16', '23:15:00', 'Pending', NULL),
(5, 3, 'Hero Sabino', 'Theft', 'When i walking my phone grabbed in the walkways', 'Naga', '2025-02-04', '03:12:00', 'Resolved', 'Tall Man, Red Hoodie');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_log`
--

CREATE TABLE `tbl_log` (
  `log_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `u_username` varchar(50) NOT NULL,
  `login_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `u_type` varchar(50) NOT NULL,
  `log_status` enum('Pending','Active','Inactive','') NOT NULL,
  `logout_time` timestamp NULL DEFAULT NULL,
  `log_description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_log`
--

INSERT INTO `tbl_log` (`log_id`, `u_id`, `u_username`, `login_time`, `u_type`, `log_status`, `logout_time`, `log_description`) VALUES
(1, 3, 'rose123', '2025-05-18 10:13:29', 'Success - User Action', 'Inactive', '2025-05-18 10:13:29', 'New user registered: rose123'),
(2, 3, 'rose123', '2025-05-18 10:13:29', 'Failed - Inactive Account', 'Inactive', '2025-05-18 10:13:29', NULL),
(3, 3, 'rose123', '2025-05-18 10:13:29', 'Success - Admin Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(4, 3, 'rose123', '2025-05-18 07:49:14', 'Success - Admin Login', 'Inactive', '2025-05-18 07:49:14', NULL),
(5, 3, 'rose123', '2025-05-18 07:50:02', 'Success - Admin Login', 'Inactive', '2025-05-18 07:50:02', NULL),
(6, 3, 'rose123', '2025-05-18 09:41:32', 'Success - Admin Login', 'Inactive', '2025-05-18 09:41:32', NULL),
(7, 3, 'rose123', '2025-05-18 10:13:29', 'Success - Staff Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(8, 3, 'rose123', '2025-05-18 09:47:34', 'Success - Admin Login', 'Inactive', '2025-05-18 09:47:34', NULL),
(9, 3, 'rose123', '2025-05-18 09:50:36', 'Success - Admin Login', 'Inactive', '2025-05-18 09:50:36', NULL),
(10, 3, 'rose123', '2025-05-18 09:51:37', 'Success - Admin Login', 'Inactive', '2025-05-18 09:51:37', NULL),
(11, 4, 'tupac123', '2025-05-18 12:10:24', 'Success - User Action', 'Inactive', '2025-05-18 12:10:24', 'New user registered: tupac123'),
(12, 4, 'tupac123', '2025-05-18 10:07:19', 'Failed - Inactive Account', 'Inactive', '2025-05-18 10:07:19', NULL),
(13, 4, 'tupac123', '2025-05-18 10:02:30', 'Success - Police Login', 'Inactive', '2025-05-18 10:02:30', NULL),
(14, 4, 'tupac123', '2025-05-18 10:07:16', 'Success - Police Login', 'Inactive', '2025-05-18 10:07:16', NULL),
(15, 3, 'rose123', '2025-05-18 10:13:29', 'Success - User Login', 'Inactive', '2025-05-18 10:13:29', NULL),
(16, 1, 'ross123', '2025-05-18 10:14:14', 'Success - Admin Login', 'Inactive', '2025-05-18 10:14:14', NULL),
(17, 3, 'rose123', '2025-05-18 10:27:20', 'Success - User Login', 'Inactive', '2025-05-18 10:27:20', NULL),
(18, 3, 'rose123', '2025-05-18 10:27:20', 'Success - User Login', 'Inactive', '2025-05-18 10:27:20', NULL),
(19, 3, 'rose123', '2025-05-18 10:29:14', 'Success - User Login', 'Inactive', '2025-05-18 10:29:14', NULL),
(20, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(22, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(23, 3, 'rose123', '2025-05-18 10:37:07', 'Submitted a new blotter report', 'Inactive', '2025-05-18 10:37:07', NULL),
(24, 3, 'rose123', '2025-05-18 10:37:07', 'Success - User Login', 'Inactive', '2025-05-18 10:37:07', NULL),
(25, 3, 'rose123', '2025-05-18 11:58:46', 'Success - User Login', 'Inactive', '2025-05-18 11:58:46', NULL),
(26, 3, 'rose123', '2025-05-18 11:58:46', 'Success - User Login', 'Inactive', '2025-05-18 11:58:46', NULL),
(27, 3, 'rose123', '2025-05-18 11:58:46', 'Success - User Login', 'Inactive', '2025-05-18 11:58:46', NULL),
(28, 3, 'rose123', '2025-05-18 11:58:46', 'Success - User Login', 'Inactive', '2025-05-18 11:58:46', NULL),
(30, 3, 'rose123', '2025-05-18 11:58:46', 'Success - User Login', 'Inactive', '2025-05-18 11:58:46', NULL),
(31, 3, 'rose123', '2025-05-18 12:00:31', 'Success - User Login', 'Active', NULL, NULL),
(32, 3, 'rose123', '2025-05-18 12:01:42', 'Success - User Login', 'Active', NULL, NULL),
(33, 3, 'rose123', '2025-05-18 12:02:23', 'Submitted a new blotter report', 'Active', NULL, NULL),
(34, 4, 'tupac123', '2025-05-18 12:10:24', 'Success - Police Login', 'Inactive', '2025-05-18 12:10:24', NULL),
(35, 4, 'tupac123', '2025-05-18 12:10:24', 'Success - Police Login', 'Inactive', '2025-05-18 12:10:24', NULL),
(36, 4, 'tupac123', '2025-05-18 12:10:24', 'Success - Police Login', 'Inactive', '2025-05-18 12:10:24', NULL),
(37, 4, 'tupac123', '2025-05-18 12:10:24', 'Success - Police Login', 'Inactive', '2025-05-18 12:10:24', NULL),
(38, 4, 'tupac123', '2025-05-18 12:12:05', 'Success - Police Login', 'Inactive', '2025-05-18 12:12:05', NULL),
(39, 4, 'tupac123', '2025-05-18 12:13:00', 'Success - Police Login', 'Inactive', '2025-05-18 12:13:00', NULL),
(40, 4, 'tupac123', '2025-05-18 12:14:42', 'Success - Police Login', 'Inactive', '2025-05-18 12:14:42', NULL),
(41, 4, 'tupac123', '2025-05-18 12:14:42', 'Resolved a blotter report (ID: 3)', 'Inactive', '2025-05-18 12:14:42', NULL),
(42, 5, 'kaye123', '2025-05-18 12:17:24', 'Success - User Action', 'Inactive', '2025-05-18 12:17:24', 'New user registered: kaye123'),
(43, 5, 'kaye123', '2025-05-18 12:17:24', 'Failed - Inactive Account', 'Inactive', '2025-05-18 12:17:24', NULL),
(44, 5, 'kaye123', '2025-05-18 12:17:24', 'Success - User Login', 'Inactive', '2025-05-18 12:17:24', NULL),
(45, 5, 'kaye123', '2025-05-18 12:17:24', 'Submitted a new blotter report', 'Inactive', '2025-05-18 12:17:24', NULL),
(46, 5, 'kaye123', '2025-05-18 12:17:24', 'Success - User Login', 'Inactive', '2025-05-18 12:17:24', NULL),
(47, 5, 'kaye123', '2025-05-23 06:48:53', 'Success - User Action', 'Inactive', '2025-05-23 06:48:53', 'User Reset Their Password'),
(48, 5, 'kaye123', '2025-05-23 06:48:53', 'Success - User Login', 'Inactive', '2025-05-23 06:48:53', NULL),
(52, 5, 'kaye123', '2025-05-23 06:48:53', 'Success - User Login', 'Inactive', '2025-05-23 06:48:53', NULL),
(53, 5, 'kaye123', '2025-05-23 06:48:53', 'Success - User Action', 'Inactive', '2025-05-23 06:48:53', 'User Changed Their Password'),
(54, 5, 'kaye123', '2025-05-23 06:50:37', 'Success - User Login', 'Inactive', '2025-05-23 06:50:37', NULL),
(55, 5, 'kaye123', '2025-05-23 06:50:37', 'Success - Admin Login', 'Inactive', '2025-05-23 06:50:37', NULL),
(56, 5, 'kaye123', '2025-05-23 06:53:46', 'Success - Admin Login', 'Inactive', '2025-05-23 06:53:46', NULL),
(57, 5, 'kaye123', '2025-05-23 07:09:16', 'Success - Admin Login', 'Inactive', '2025-05-23 07:09:16', NULL),
(59, 5, 'kaye123', '2025-05-23 07:12:05', 'Success - Admin Login', 'Inactive', '2025-05-23 07:12:05', NULL),
(60, 5, 'kaye123', '2025-05-23 07:12:05', 'Success - Admin Login', 'Inactive', '2025-05-23 07:12:05', NULL),
(61, 1, 'ross123', '2025-05-23 07:12:52', 'Success - Admin Login', 'Inactive', '2025-05-23 07:12:52', NULL),
(62, 5, 'kaye123', '2025-05-23 07:16:00', 'Success - Admin Login', 'Inactive', '2025-05-23 07:16:00', NULL),
(63, 5, 'kaye123', '2025-05-25 11:33:47', 'Success - Admin Login', 'Inactive', '2025-05-25 11:33:47', NULL),
(64, 4, 'tupac123', '2025-05-23 07:18:55', 'Success - Police Login', 'Inactive', '2025-05-23 07:18:55', NULL),
(65, 5, 'kaye123', '2025-05-25 11:33:47', 'Success - Admin Login', 'Inactive', '2025-05-25 11:33:47', NULL),
(66, 5, 'kaye123', '2025-05-25 11:33:47', 'Success - Admin Login', 'Inactive', '2025-05-25 11:33:47', NULL),
(67, 3, 'rose123', '2025-05-25 02:57:46', 'Success - User Login', 'Active', NULL, NULL),
(68, 3, 'rose123', '2025-05-25 02:58:56', 'Submitted a new blotter report', 'Active', NULL, NULL),
(69, 4, 'tupac123', '2025-05-25 03:00:47', 'Success - Police Login', 'Inactive', '2025-05-25 03:00:47', NULL),
(71, 4, 'tupac123', '2025-05-25 11:33:09', 'Success - Police Login', 'Inactive', '2025-05-25 11:33:09', NULL),
(72, 4, 'tupac123', '2025-05-25 11:33:09', 'Success - Police Login', 'Inactive', '2025-05-25 11:33:09', NULL),
(73, 4, 'tupac123', '2025-05-25 11:33:09', 'Resolved a blotter report (ID: 5)', 'Inactive', '2025-05-25 11:33:09', NULL),
(74, 4, 'tupac123', '2025-05-25 11:33:24', 'Success - Police Login', 'Inactive', '2025-05-25 11:33:24', NULL),
(75, 5, 'kaye123', '2025-05-25 11:33:47', 'Success - Admin Login', 'Inactive', '2025-05-25 11:33:47', NULL),
(76, 3, 'rose123', '2025-05-25 11:33:56', 'Success - User Login', 'Active', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `u_id` int(11) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `u_lname` varchar(255) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_status` varchar(255) NOT NULL,
  `u_type` varchar(255) NOT NULL,
  `u_password` varchar(255) NOT NULL,
  `u_image` varchar(255) NOT NULL,
  `security_question` varchar(100) NOT NULL,
  `security_answer` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`u_id`, `u_fname`, `u_lname`, `u_username`, `u_email`, `u_status`, `u_type`, `u_password`, `u_image`, `security_question`, `security_answer`) VALUES
(1, 'ross', 'sabio', 'ross123', 'rosssabio@gmail.com', 'Active', 'Admin', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', '', '', ''),
(2, 'mark', 'pacaldo', 'mark123', 'mark@gmail.com', 'Active', 'User', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', '', '', ''),
(3, 'rose', 'reyes', 'rose123', 'rose@gmail.com', 'Active', 'User', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', 'Null', 'What\'s your favorite food?', 'qV/QG0FakhHmXWW/9kfkNE5/GUje75Wbca8h9ippdXE='),
(4, 'tupac', 'pactu', 'tupac123', 'tupac123@gmail.com', 'Active', 'Police', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', 'Null', 'What\'s the name of your first pet?', 'Fkd2iMDgBpnGz6RJejYS1+g8UyBitkslD+2JCBKO1Ug='),
(5, 'shea', 'kaye', 'kaye123', 'kayeshea@gmail.com', 'Active', 'Admin', 'ky88G1YlfOhTmsJp16q0JVDaz4gY0HXwvfGZBWKq4+8=', 'Null', 'What\'s your favorite food?', '3kM+1VChYkp0J5qjDDa1LyhEJmEMAUtwAi5MiJ68qHU=');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `tbl_log`
--
ALTER TABLE `tbl_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `u_id` (`u_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_log`
--
ALTER TABLE `tbl_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_log`
--
ALTER TABLE `tbl_log`
  ADD CONSTRAINT `tbl_log_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
