-- Adatbazis letrehozasa
DROP DATABASE IF EXISTS RobbanoCicak;

CREATE DATABASE IF NOT EXISTS RobbanoCicak
CHARACTER SET utf8
COLLATE utf8_hungarian_ci;

USE RobbanoCicak;

-- Tabla letrehozasa
DROP TABLE IF EXISTS ScoreTable;

CREATE TABLE ScoreTable (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  score INT NOT NULL
);