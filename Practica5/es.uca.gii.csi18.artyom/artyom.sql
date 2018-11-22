-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 17-11-2018 a las 17:57:36
-- Versión del servidor: 5.7.17-log
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `artyom`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `soldado`
--

CREATE TABLE `soldado` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `HorasGuardia` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `soldado`
--

INSERT INTO `soldado` (`Id`, `Nombre`, `HorasGuardia`) VALUES
(1, 'Pepe el Cojo2', 88),
(3, 'Perico', 17),
(4, 'Junter', 10),
(5, 'Pepe el Cojo2', 88),
(7, 'Perico', 17),
(8, 'Junter', 10),
(9, 'Pepe el Cojo2', 88),
(11, 'Perico', 17),
(12, 'Junter', 10),
(13, 'Pepe el Cojo2', 88),
(15, 'Perico', 17),
(16, 'Junter', 10),
(17, 'Pepe el Cojo2', 88),
(19, 'Perico', 17),
(20, 'Junter', 10),
(21, 'Paco', 54),
(22, 'Adios', 666);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `soldado`
--
ALTER TABLE `soldado`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `soldado`
--
ALTER TABLE `soldado`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
