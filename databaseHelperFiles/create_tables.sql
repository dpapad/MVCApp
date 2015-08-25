CREATE TABLE Contact (
    Id INT primary key auto_increment,
    FullName VARCHAR(45),
    Nickname VARCHAR(45),
    Notes VARCHAR(45)
);

CREATE TABLE ContactEmails (
    Id int primary key AUTO_INCREMENT,
    Address VARCHAR(150) unique,
    Category tinyint,
	fContactId int,
    FOREIGN KEY (fContactId) REFERENCES Contact (Id) ON UPDATE CASCADE
);


CREATE TABLE `Contact` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(45) DEFAULT NULL,
  `Nickname` varchar(45) DEFAULT NULL,
  `Notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

CREATE TABLE `Emails` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Address` varchar(50) DEFAULT NULL,
  `Category` tinyint(4) DEFAULT NULL,
  `fContactId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Address` (`Address`),
  KEY `fContactId` (`fContactId`),
  CONSTRAINT `Emails_ibfk_1` FOREIGN KEY (`fContactId`) REFERENCES `Contact` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

