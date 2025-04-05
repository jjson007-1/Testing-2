-- phpMyAdmin SQL Dump
-- version 4.0.4.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 05, 2025 at 10:33 PM
-- Server version: 5.6.13
-- PHP Version: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `rentalproject`
--
CREATE DATABASE IF NOT EXISTS `rentalproject` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `rentalproject`;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `user_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `customer_history`
--

CREATE TABLE IF NOT EXISTS `customer_history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `fullname` varchar(25) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(25) NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE IF NOT EXISTS `equipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `description` varchar(200) NOT NULL,
  `date_added` varchar(20) NOT NULL,
  `avaliability` varchar(25) NOT NULL,
  `condition` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`id`, `name`, `price`, `description`, `date_added`, `avaliability`, `condition`) VALUES
(1, 'Generator', 75.5, 'Portable power generator for various applications.', '08/15/2023', 'Available', 'Excellent'),
(2, 'Water Pump', 45, 'High-efficiency water pump for irrigation and drainage.', '03/20/2024', 'Rented', 'Good'),
(3, 'Jackhammer', 99.99, 'Heavy-duty jackhammer for demolition work.', '11/01/2022', 'Available', 'Fair'),
(4, 'Concrete Mixer', 60.25, 'Robust concrete mixer for small to medium projects.', '05/10/2023', 'Under Maintenance', 'Needs Repair'),
(5, 'Scaffolding Set', 120, 'Complete scaffolding set for construction and maintenance.', '09/28/2024', 'Available', 'Good'),
(6, 'Welding Machine', 85.75, 'Industrial welding machine for metal fabrication.', '01/12/2025', 'Rented', 'Excellent'),
(8, 'Power Washer', 35.99, 'High-pressure power washer for cleaning surfaces.', '04/01/2024', 'Available', 'Excellent'),
(9, 'Tile Cutter', 25.5, 'Precision tile cutter for flooring and wall installations.', '12/18/2022', 'Rented', 'Fair'),
(10, 'Lawn Mower', 40, 'Gas-powered lawn mower for garden maintenance.', '07/22/2023', 'Available', 'Good'),
(11, 'Compressor', 23.4, 'Compress things', '23/12/2019', 'Available', 'Excellent');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `event_name` varchar(20) NOT NULL,
  `event_date` date NOT NULL,
  `event_location` varchar(50) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`event_id`, `customer_id`, `event_name`, `event_date`, `event_location`) VALUES
(1, 1, 'Birthday Party', '2025-03-10', 'Skating Ranger'),
(2, 2, 'Wedding Reception', '2025-04-20', 'Pauls Chapel'),
(3, 3, 'Corporate Event', '2025-05-15', 'LHN Headquaters'),
(4, 4, 'Baby Shower', '2025-06-05', 'May Town Spot');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `inventory_id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_id` int(11) NOT NULL,
  `quantity_available` int(11) NOT NULL,
  `total_quantity` int(11) NOT NULL,
  `last_maintenance_date` date NOT NULL,
  `condition_status` varchar(20) NOT NULL,
  PRIMARY KEY (`inventory_id`),
  KEY `equipment_id` (`equipment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`inventory_id`, `equipment_id`, `quantity_available`, `total_quantity`, `last_maintenance_date`, `condition_status`) VALUES
(1, 1, 10, 12, '2025-02-15', 'Good'),
(2, 2, 5, 7, '2025-01-30', 'Good'),
(3, 3, 20, 25, '2025-02-10', 'Good'),
(4, 4, 50, 60, '2025-02-12', 'Good');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `rental_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `amount_due` double(10,2) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_method` varchar(15) NOT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `rental_id` (`rental_id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoice_id`, `rental_id`, `customer_id`, `amount_due`, `payment_date`, `payment_method`) VALUES
(1, 1, 1, 200.00, '2025-03-07', 'Credit Card'),
(2, 2, 2, 500.00, '2025-04-17', 'PayPal'),
(3, 3, 3, 300.00, '2025-05-12', 'Bank Transfer'),
(4, 4, 4, 150.00, '2025-06-02', 'Cash');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `equipment_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(60) NOT NULL,
  `rental_price` double(10,2) NOT NULL,
  `availability_status` varchar(18) NOT NULL,
  `condition_status` varchar(15) NOT NULL,
  PRIMARY KEY (`equipment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`equipment_id`, `name`, `description`, `rental_price`, `availability_status`, `condition_status`) VALUES
(1, 'Speakers', 'High-quality sound system', 50.00, 'Available', 'Good'),
(2, 'Projector', '4K HD projector', 80.00, 'Available', 'Good'),
(3, 'Tables', 'Round tables for seating', 15.00, 'Available', 'Good'),
(4, 'Chairs', 'Comfortable seating', 5.00, 'Available', 'Good');

-- --------------------------------------------------------

--
-- Table structure for table `maintenance`
--

CREATE TABLE IF NOT EXISTS `maintenance` (
  `maintenance_id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_id` int(11) NOT NULL,
  `performed_by` varchar(25) NOT NULL,
  `date_performed` date NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`maintenance_id`),
  KEY `equipment_id` (`equipment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `maintenance`
--

INSERT INTO `maintenance` (`maintenance_id`, `equipment_id`, `performed_by`, `date_performed`, `status`) VALUES
(1, 1, 'Robert Brown', '2025-02-15', 'Completed'),
(2, 2, 'Emily Davis', '2025-01-30', 'Completed'),
(3, 3, 'Robert Brown', '2025-02-10', 'Completed'),
(4, 4, 'Emily Davis', '2025-02-12', 'Completed');

-- --------------------------------------------------------

--
-- Table structure for table `rentalorder`
--

CREATE TABLE IF NOT EXISTS `rentalorder` (
  `rental_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `rental_date` date NOT NULL,
  `return_date` date NOT NULL,
  `rent_status` varchar(15) NOT NULL,
  `total_price` double(10,2) NOT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `event_id` (`event_id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `rentalorder`
--

INSERT INTO `rentalorder` (`rental_id`, `event_id`, `customer_id`, `rental_date`, `return_date`, `rent_status`, `total_price`) VALUES
(1, 1, 1, '2025-03-08', '2025-03-11', 'Confirmed', 200.00),
(2, 2, 2, '2025-04-18', '2025-04-21', 'Pending', 500.00),
(3, 3, 3, '2025-05-13', '2025-05-16', 'Confirmed', 300.00),
(4, 4, 4, '2025-06-03', '2025-06-06', 'Pending', 150.00);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `staff_role` varchar(50) NOT NULL,
  PRIMARY KEY (`staff_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `user_id`, `staff_role`) VALUES
(1, 3, 'Manager'),
(2, 4, 'Technician'),
(3, 5, 'Logistics Coordinator');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(25) NOT NULL,
  `lastname` varchar(25) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `firstname`, `lastname`, `phone`, `email`, `user_password`, `username`) VALUES
(1, 'John', 'Doe', '123-456-7890', 'johndoe@example.com', 'password123', 'JDoe'),
(2, 'Jane', 'Smith', '234-567-8901', 'janesmith@example.com', 'password123', 'JSmith'),
(3, 'Robert', 'Brown', '345-678-9012', 'robertbrown@example.com', 'password123', 'RBrown'),
(4, 'Emily', 'Davis', '456-789-0123', 'emilydavis@example.com', 'password123', 'EDavis'),
(5, 'Michael', 'Johnson', '567-890-1234', 'michaeljohnson@example.com', 'password123', 'MJohnson');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
