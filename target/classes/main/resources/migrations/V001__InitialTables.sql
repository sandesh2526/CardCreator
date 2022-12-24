CREATE TABLE USER (
	ID varchar(36) primary key,
	EMAIL varchar(50),
	FRIST_NAME varchar(50),
	LAST_NAME varchar(50),
);

CREATE TABLE COLUMN(
	ID varchar (36) primary key,
	NAME NVARCHAR(50),
);

CREATE TABLE CARD(
	ID varchar(36) primary key,
	TITLE nvarchar(50),
	DESCRIPTION clob;
	CREATION_DATE timestamp, 
	USER_ID
);