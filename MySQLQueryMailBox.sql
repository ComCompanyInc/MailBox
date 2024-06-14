DROP DATABASE IF EXISTS mbox;
CREATE DATABASE IF NOT EXISTS mbox;

USE mbox;

CREATE TABLE DirectoryAddress
(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    MailAddress NVARCHAR(64)
);

CREATE TABLE Account
(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    IdDirectoryAddress INT NOT NULL,
    FirstName NVARCHAR(100),
    LastName NVARCHAR(100),
    DateOfRegistration DATE,
    Password NVARCHAR(30)
);

CREATE TABLE Mail
(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    IdDirectoryAddressOut INT NOT NULL,
    IdDirectoryAddressIn INT NOT NULL,
    DescriptionMail NVARCHAR(5000),
    DateOfSend DATE
)