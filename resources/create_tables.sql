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

