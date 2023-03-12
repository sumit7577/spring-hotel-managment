-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-03-12 13:01:11.737

-- tables
-- Table: Dish
CREATE TABLE Dish (
                      ID int NOT NULL AUTO_INCREMENT,
                      name varchar(50) NOT NULL,
                      description varchar(500) NOT NULL,
                      photo_directory varchar(100) NOT NULL,
                      CONSTRAINT Dish_pk PRIMARY KEY (ID)
);

-- Table: Entertainment
CREATE TABLE Entertainment (
                               ID int NOT NULL AUTO_INCREMENT,
                               description varchar(100) NOT NULL,
                               entertainmentType_ID int NOT NULL,
                               CONSTRAINT Entertainment_pk PRIMARY KEY (ID)
);

-- Table: EntertainmentReservations
CREATE TABLE EntertainmentReservations (
                                           ID int NOT NULL AUTO_INCREMENT,
                                           date date NOT NULL,
                                           time time NOT NULL,
                                           user_ID int NOT NULL,
                                           entertainment_ID int NOT NULL,
                                           payment_ID int NOT NULL,
                                           CONSTRAINT EntertainmentReservations_pk PRIMARY KEY (ID)
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
                          menu_date date NOT NULL,
                          dish_ID int NOT NULL,
                          menuType_ID int NOT NULL,
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
                      access_code int NOT NULL,
                      floor int NOT NULL,
                      roomType_ID int NOT NULL,
                      cleaning_request timestamp NULL,
                      CONSTRAINT Room_pk PRIMARY KEY (number)
);

-- Table: RoomReservations
CREATE TABLE RoomReservations (
                                  ID int NOT NULL AUTO_INCREMENT,
                                  from_date date NOT NULL,
                                  to_date date NOT NULL,
                                  room_number int NOT NULL,
                                  user_ID int NOT NULL,
                                  payment_ID int NULL,
                                  CONSTRAINT RoomReservations_pk PRIMARY KEY (ID)
);

-- Table: RoomType
CREATE TABLE RoomType (
                          ID int NOT NULL AUTO_INCREMENT,
                          room_occupancy int NOT NULL,
                          price int NOT NULL,
                          room_area int NOT NULL,
                          CONSTRAINT RoomType_pk PRIMARY KEY (ID)
);

-- Table: User
CREATE TABLE User (
                      ID int NOT NULL AUTO_INCREMENT,
                      first_name varchar(30) NOT NULL,
                      last_name varchar(30) NOT NULL,
                      email varchar(50) NOT NULL,
                      phone varchar(50) NOT NULL,
                      discount int NOT NULL,
                      hash varchar(500) NOT NULL,
                      salt varchar(500) NOT NULL,
                      refresh_token varchar(500) NOT NULL,
                      CONSTRAINT User_pk PRIMARY KEY (ID)
);

-- Table: UserRoles
CREATE TABLE UserRoles (
                           role_ID int NOT NULL,
                           user_ID int NOT NULL,
                           CONSTRAINT UserRoles_pk PRIMARY KEY (role_ID,user_ID)
);

-- Table: Weekend
CREATE TABLE Weekend (
                         ID int NOT NULL AUTO_INCREMENT,
                         place varchar(50) NOT NULL,
                         description varchar(500) NOT NULL,
                         date_from timestamp NOT NULL,
                         date_to timestamp NOT NULL,
                         CONSTRAINT Weekend_pk PRIMARY KEY (ID)
);

-- foreign keys
-- Reference: EntertainmentReservation_Entertainment (table: EntertainmentReservations)
ALTER TABLE EntertainmentReservations ADD CONSTRAINT EntertainmentReservation_Entertainment FOREIGN KEY EntertainmentReservation_Entertainment (entertainment_ID)
    REFERENCES Entertainment (ID);

-- Reference: EntertainmentReservation_Payment (table: EntertainmentReservations)
ALTER TABLE EntertainmentReservations ADD CONSTRAINT EntertainmentReservation_Payment FOREIGN KEY EntertainmentReservation_Payment (payment_ID)
    REFERENCES Payment (ID);

-- Reference: EntertainmentReservation_User (table: EntertainmentReservations)
ALTER TABLE EntertainmentReservations ADD CONSTRAINT EntertainmentReservation_User FOREIGN KEY EntertainmentReservation_User (user_ID)
    REFERENCES User (ID);

-- Reference: Entertainment_EntertainmentType (table: Entertainment)
ALTER TABLE Entertainment ADD CONSTRAINT Entertainment_EntertainmentType FOREIGN KEY Entertainment_EntertainmentType (entertainmentType_ID)
    REFERENCES EntertainmentType (ID);

-- Reference: MenuItem_Dish (table: MenuItem)
ALTER TABLE MenuItem ADD CONSTRAINT MenuItem_Dish FOREIGN KEY MenuItem_Dish (dish_ID)
    REFERENCES Dish (ID);

-- Reference: MenuItem_MenuType (table: MenuItem)
ALTER TABLE MenuItem ADD CONSTRAINT MenuItem_MenuType FOREIGN KEY MenuItem_MenuType (menuType_ID)
    REFERENCES MenuType (ID);

-- Reference: RoomReservation_Payment (table: RoomReservations)
ALTER TABLE RoomReservations ADD CONSTRAINT RoomReservation_Payment FOREIGN KEY RoomReservation_Payment (payment_ID)
    REFERENCES Payment (ID);

-- Reference: RoomReservation_Room (table: RoomReservations)
ALTER TABLE RoomReservations ADD CONSTRAINT RoomReservation_Room FOREIGN KEY RoomReservation_Room (room_number)
    REFERENCES Room (number);

-- Reference: RoomReservation_User (table: RoomReservations)
ALTER TABLE RoomReservations ADD CONSTRAINT RoomReservation_User FOREIGN KEY RoomReservation_User (user_ID)
    REFERENCES User (ID);

-- Reference: Room_RoomType (table: Room)
ALTER TABLE Room ADD CONSTRAINT Room_RoomType FOREIGN KEY Room_RoomType (roomType_ID)
    REFERENCES RoomType (ID);

-- Reference: UserRoles_Role (table: UserRoles)
ALTER TABLE UserRoles ADD CONSTRAINT UserRoles_Role FOREIGN KEY UserRoles_Role (role_ID)
    REFERENCES Role (ID);

-- Reference: UserRoles_User (table: UserRoles)
ALTER TABLE UserRoles ADD CONSTRAINT UserRoles_User FOREIGN KEY UserRoles_User (user_ID)
    REFERENCES User (ID);

-- End of file.

