CREATE TABLE User(
	ID varchar(300) primary key,
	Location varchar(300),
	Country varchar(300),
	SellRating varchar(300),
	BidRating varchar(300)
) ENGINE = INNODB;

CREATE TABLE Item(
	ID varchar(300) primary key,
	Name varchar(300) not null,
	Currently decimal(8, 2) not null,
	Buy_Price decimal(8, 2),
	First_Bid decimal(8, 2) not null,
	Number_of_Bids int,
	Location varchar(300),
	Latitude varchar(300),
	Longitude varchar(300),
	Country varchar(300),
	Started Timestamp,
	Ends Timestamp,
	Seller varchar(300),
	Description varchar(4000),
	FOREIGN KEY (Seller) REFERENCES User(ID)
) ENGINE = INNODB;

CREATE TABLE Bid(
	Iid varchar(300),
	Uid varchar(300),
	Time Timestamp,
	Amount decimal(8, 2),
	PRIMARY KEY (Iid, Uid, Time),
	FOREIGN KEY (Iid) REFERENCES Item(ID),
	FOREIGN KEY (Uid) REFERENCES User(ID)
) ENGINE = INNODB;

CREATE TABLE Category(
	ID int primary key,
	Type varchar(300)
) ENGINE = INNODB;

CREATE TABLE Belong(
	Iid varchar(300),
	Cid int,
	PRIMARY KEY(Iid, Cid),
	FOREIGN KEY (Iid) REFERENCES Item(ID),
	FOREIGN KEY (Cid) REFERENCES Category(ID)
) ENGINE = INNODB;