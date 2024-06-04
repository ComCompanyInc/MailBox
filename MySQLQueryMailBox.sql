CREATE DATABASE mbox;

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
    DateOfRegistration DATE
);

CREATE TABLE Mail
(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Id INT NOT NULL,
    IdDirectoryAddressOut INT NOT NULL,
    IdDirectoryAddressIn INT NOT NULL,
    DescriptionMail NVARCHAR(5000),
    DateOfSend DATE
)