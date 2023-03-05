-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-03-05 12:32:04.618

-- tables
-- Table: Dish
CREATE TABLE Dish (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    description varchar(500) NOT NULL,
    photoDirectory varchar(100) NOT NULL,
    CONSTRAINT Dish_pk PRIMARY KEY (ID)
);

-- Table: Entertainment
CREATE TABLE Entertainment (
    ID int NOT NULL AUTO_INCREMENT,
    description varchar(100) NOT NULL,
    entertainmentTypeID int NOT NULL,
    CONSTRAINT Entertainment_pk PRIMARY KEY (ID)
);

-- Table: EntertainmentReservation
CREATE TABLE EntertainmentReservation (
    ID int NOT NULL AUTO_INCREMENT,
    date date NOT NULL,
    time time NOT NULL,
    userID int NOT NULL,
    entertainmentID int NOT NULL,
    paymentID int NOT NULL,
    CONSTRAINT EntertainmentReservation_pk PRIMARY KEY (ID)
);

-- Table: EntertainmentType
CREATE TABLE EntertainmentType (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    price int NOT NULL,
    CONSTRAINT EntertainmentType_pk PRIMARY KEY (ID)
);

-- Table: MenuItem
CREATE TABLE MenuItem (
    ID int NOT NULL AUTO_INCREMENT,
    menuDate date NOT NULL,
    dishID int NOT NULL,
    menuTypeID int NOT NULL,
    CONSTRAINT MenuItem_pk PRIMARY KEY (ID)
);

-- Table: MenuType
CREATE TABLE MenuType (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    CONSTRAINT MenuType_pk PRIMARY KEY (ID)
);

-- Table: Payment
CREATE TABLE Payment (
    ID int NOT NULL AUTO_INCREMENT,
    date timestamp NOT NULL,
    amount int NOT NULL,
    CONSTRAINT Payment_pk PRIMARY KEY (ID)
);

-- Table: Role
CREATE TABLE Role (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(25) NOT NULL,
    CONSTRAINT Role_pk PRIMARY KEY (ID)
);

-- Table: Room
CREATE TABLE Room (
    number int NOT NULL,
    accessCode int NOT NULL,
    floor int NOT NULL,
    roomTypeID int NOT NULL,
    cleaningRequest timestamp NULL,
    CONSTRAINT Room_pk PRIMARY KEY (number)
);

-- Table: RoomReservation
CREATE TABLE RoomReservation (
    ID int NOT NULL AUTO_INCREMENT,
    fromDate date NOT NULL,
    toDate date NOT NULL,
    roomNumber int NOT NULL,
    userID int NOT NULL,
    paymentID int NULL,
    CONSTRAINT RoomReservation_pk PRIMARY KEY (ID)
);

-- Table: RoomType
CREATE TABLE RoomType (
    ID int NOT NULL AUTO_INCREMENT,
    roomOccupancy int NOT NULL,
    price int NOT NULL,
    roomArea int NOT NULL,
    CONSTRAINT RoomType_pk PRIMARY KEY (ID)
);

-- Table: User
CREATE TABLE User (
    ID int NOT NULL AUTO_INCREMENT,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    email varchar(50) NOT NULL,
    phone varchar(50) NOT NULL,
    discount int NOT NULL,
    hash varchar(500) NOT NULL,
    salt varchar(500) NOT NULL,
    refreshToken varchar(500) NOT NULL,
    roleID int NOT NULL,
    CONSTRAINT User_pk PRIMARY KEY (ID)
);

-- Table: Weekend
CREATE TABLE Weekend (
    ID int NOT NULL AUTO_INCREMENT,
    place varchar(50) NOT NULL,
    description varchar(500) NOT NULL,
    dateFrom timestamp NOT NULL,
    dateTo timestamp NOT NULL,
    CONSTRAINT Weekend_pk PRIMARY KEY (ID)
);

-- foreign keys
-- Reference: EntertainmentReservation_Entertainment (table: EntertainmentReservation)
ALTER TABLE EntertainmentReservation ADD CONSTRAINT EntertainmentReservation_Entertainment FOREIGN KEY EntertainmentReservation_Entertainment (entertainmentID)
    REFERENCES Entertainment (ID);

-- Reference: EntertainmentReservation_Payment (table: EntertainmentReservation)
ALTER TABLE EntertainmentReservation ADD CONSTRAINT EntertainmentReservation_Payment FOREIGN KEY EntertainmentReservation_Payment (paymentID)
    REFERENCES Payment (ID);

-- Reference: EntertainmentReservation_User (table: EntertainmentReservation)
ALTER TABLE EntertainmentReservation ADD CONSTRAINT EntertainmentReservation_User FOREIGN KEY EntertainmentReservation_User (userID)
    REFERENCES User (ID);

-- Reference: Entertainment_EntertainmentType (table: Entertainment)
ALTER TABLE Entertainment ADD CONSTRAINT Entertainment_EntertainmentType FOREIGN KEY Entertainment_EntertainmentType (entertainmentTypeID)
    REFERENCES EntertainmentType (ID);

-- Reference: MenuItem_Dish (table: MenuItem)
ALTER TABLE MenuItem ADD CONSTRAINT MenuItem_Dish FOREIGN KEY MenuItem_Dish (dishID)
    REFERENCES Dish (ID);

-- Reference: MenuItem_MenuType (table: MenuItem)
ALTER TABLE MenuItem ADD CONSTRAINT MenuItem_MenuType FOREIGN KEY MenuItem_MenuType (menuTypeID)
    REFERENCES MenuType (ID);

-- Reference: RoomReservation_Payment (table: RoomReservation)
ALTER TABLE RoomReservation ADD CONSTRAINT RoomReservation_Payment FOREIGN KEY RoomReservation_Payment (paymentID)
    REFERENCES Payment (ID);

-- Reference: RoomReservation_Room (table: RoomReservation)
ALTER TABLE RoomReservation ADD CONSTRAINT RoomReservation_Room FOREIGN KEY RoomReservation_Room (roomNumber)
    REFERENCES Room (number);

-- Reference: RoomReservation_User (table: RoomReservation)
ALTER TABLE RoomReservation ADD CONSTRAINT RoomReservation_User FOREIGN KEY RoomReservation_User (userID)
    REFERENCES User (ID);

-- Reference: Room_RoomType (table: Room)
ALTER TABLE Room ADD CONSTRAINT Room_RoomType FOREIGN KEY Room_RoomType (roomTypeID)
    REFERENCES RoomType (ID);

-- Reference: User_Role (table: User)
ALTER TABLE User ADD CONSTRAINT User_Role FOREIGN KEY User_Role (roleID)
    REFERENCES Role (ID);

-- End of file.

