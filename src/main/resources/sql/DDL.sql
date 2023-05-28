-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-05-28 11:20:06.396

-- tables
-- Table: Cleaning_History
CREATE TABLE Cleaning_History (
                                  id int NOT NULL AUTO_INCREMENT,
                                  room_id int NOT NULL,
                                  requested_at timestamp NOT NULL,
                                  done_at timestamp NULL,
                                  CONSTRAINT Cleaning_History_pk PRIMARY KEY (id)
);

-- Table: Dish
CREATE TABLE Dish (
                      id int NOT NULL AUTO_INCREMENT,
                      name varchar(50) NOT NULL,
                      description varchar(500) NOT NULL,
                      photo_directory varchar(100) NOT NULL,
                      CONSTRAINT Dish_pk PRIMARY KEY (id)
);

-- Table: Entertainment
CREATE TABLE Entertainment (
                               id int NOT NULL AUTO_INCREMENT,
                               lock_code int NULL,
                               description varchar(100) NOT NULL,
                               entertainment_type_id int NOT NULL,
                               CONSTRAINT Entertainment_pk PRIMARY KEY (id)
);

-- Table: Entertainment_Reservation
CREATE TABLE Entertainment_Reservation (
                                           id int NOT NULL AUTO_INCREMENT,
                                           date timestamp NOT NULL,
                                           user_id int NOT NULL,
                                           entertainment_id int NOT NULL,
                                           payment_id int NOT NULL,
                                           CONSTRAINT Entertainment_Reservation_pk PRIMARY KEY (id)
);

-- Table: Entertainment_Type
CREATE TABLE Entertainment_Type (
                                    id int NOT NULL AUTO_INCREMENT,
                                    name varchar(50) NOT NULL,
                                    price int NOT NULL,
                                    CONSTRAINT Entertainment_Type_pk PRIMARY KEY (id)
);

-- Table: Menu_Item
CREATE TABLE Menu_Item (
                           id int NOT NULL AUTO_INCREMENT,
                           menu_date date NOT NULL,
                           dish_id int NOT NULL,
                           menu_type_id int NOT NULL,
                           CONSTRAINT Menu_Item_pk PRIMARY KEY (id)
);

-- Table: Menu_Type
CREATE TABLE Menu_Type (
                           id int NOT NULL AUTO_INCREMENT,
                           name varchar(50) NOT NULL,
                           CONSTRAINT Menu_Type_pk PRIMARY KEY (id)
);

-- Table: Payment
CREATE TABLE Payment (
                         id int NOT NULL AUTO_INCREMENT,
                         date timestamp NOT NULL,
                         amount int NOT NULL,
                         CONSTRAINT Payment_pk PRIMARY KEY (id)
);

-- Table: Place
CREATE TABLE Place (
                       id int NOT NULL AUTO_INCREMENT,
                       name varchar(100) NOT NULL,
                       CONSTRAINT Place_pk PRIMARY KEY (id)
);

-- Table: Role
CREATE TABLE Role (
                      id int NOT NULL AUTO_INCREMENT,
                      name varchar(50) NOT NULL,
                      CONSTRAINT Role_pk PRIMARY KEY (id)
);

-- Table: Room
CREATE TABLE Room (
                      id int NOT NULL AUTO_INCREMENT,
                      number int NOT NULL,
                      room_type_id int NOT NULL,
                      access_code int NOT NULL,
                      CONSTRAINT Room_pk PRIMARY KEY (id)
);

-- Table: Room_Reservation
CREATE TABLE Room_Reservation (
                                  id int NOT NULL AUTO_INCREMENT,
                                  from_date date NOT NULL,
                                  to_date date NOT NULL,
                                  user_id int NOT NULL,
                                  room_id int NOT NULL,
                                  payment_id int NULL,
                                  CONSTRAINT Room_Reservation_pk PRIMARY KEY (id)
);

-- Table: Room_Type
CREATE TABLE Room_Type (
                           id int NOT NULL AUTO_INCREMENT,
                           room_occupancy int NOT NULL,
                           price int NOT NULL,
                           room_area int NOT NULL,
                           CONSTRAINT Room_Type_pk PRIMARY KEY (id)
);

-- Table: Token
CREATE TABLE Token (
                       id int NOT NULL AUTO_INCREMENT,
                       token varchar(500) NOT NULL,
                       user_id int NOT NULL,
                       token_type_id int NOT NULL,
                       CONSTRAINT Token_pk PRIMARY KEY (id)
);

-- Table: Token_Type
CREATE TABLE Token_Type (
                            id int NOT NULL AUTO_INCREMENT,
                            type varchar(25) NOT NULL,
                            CONSTRAINT Token_Type_pk PRIMARY KEY (id)
);

-- Table: User
CREATE TABLE User (
                      id int NOT NULL AUTO_INCREMENT,
                      first_name varchar(30) NOT NULL,
                      last_name varchar(30) NOT NULL,
                      email varchar(50) NOT NULL,
                      phone varchar(50) NOT NULL,
                      discount int NOT NULL,
                      password varchar(500) NOT NULL,
                      verified timestamp NULL,
                      CONSTRAINT User_pk PRIMARY KEY (id)
);

-- Table: User_Role
CREATE TABLE User_Role (
                           user_id int NOT NULL AUTO_INCREMENT,
                           role_id int NOT NULL,
                           CONSTRAINT User_Role_pk PRIMARY KEY (user_id,role_id)
);

-- Table: Weekend
CREATE TABLE Weekend (
                         id int NOT NULL AUTO_INCREMENT,
                         description varchar(500) NOT NULL,
                         date_from timestamp NOT NULL,
                         date_to timestamp NOT NULL,
                         place_id int NOT NULL,
                         CONSTRAINT Weekend_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Cleaning_Request_Room (table: Cleaning_History)
ALTER TABLE Cleaning_History ADD CONSTRAINT Cleaning_Request_Room FOREIGN KEY Cleaning_Request_Room (room_id)
    REFERENCES Room (id);

-- Reference: EntertainmentReservation_Entertainment (table: Entertainment_Reservation)
ALTER TABLE Entertainment_Reservation ADD CONSTRAINT EntertainmentReservation_Entertainment FOREIGN KEY EntertainmentReservation_Entertainment (entertainment_id)
    REFERENCES Entertainment (id);

-- Reference: EntertainmentReservation_Payment (table: Entertainment_Reservation)
ALTER TABLE Entertainment_Reservation ADD CONSTRAINT EntertainmentReservation_Payment FOREIGN KEY EntertainmentReservation_Payment (payment_id)
    REFERENCES Payment (id);

-- Reference: EntertainmentReservation_User (table: Entertainment_Reservation)
ALTER TABLE Entertainment_Reservation ADD CONSTRAINT EntertainmentReservation_User FOREIGN KEY EntertainmentReservation_User (user_id)
    REFERENCES User (id);

-- Reference: Entertainment_EntertainmentType (table: Entertainment)
ALTER TABLE Entertainment ADD CONSTRAINT Entertainment_EntertainmentType FOREIGN KEY Entertainment_EntertainmentType (entertainment_type_id)
    REFERENCES Entertainment_Type (id);

-- Reference: MenuItem_Dish (table: Menu_Item)
ALTER TABLE Menu_Item ADD CONSTRAINT MenuItem_Dish FOREIGN KEY MenuItem_Dish (dish_id)
    REFERENCES Dish (id);

-- Reference: MenuItem_MenuType (table: Menu_Item)
ALTER TABLE Menu_Item ADD CONSTRAINT MenuItem_MenuType FOREIGN KEY MenuItem_MenuType (menu_type_id)
    REFERENCES Menu_Type (id);

-- Reference: RoomReservation_Payment (table: Room_Reservation)
ALTER TABLE Room_Reservation ADD CONSTRAINT RoomReservation_Payment FOREIGN KEY RoomReservation_Payment (payment_id)
    REFERENCES Payment (id);

-- Reference: RoomReservation_User (table: Room_Reservation)
ALTER TABLE Room_Reservation ADD CONSTRAINT RoomReservation_User FOREIGN KEY RoomReservation_User (user_id)
    REFERENCES User (id);

-- Reference: Room_Reservations_Room (table: Room_Reservation)
ALTER TABLE Room_Reservation ADD CONSTRAINT Room_Reservations_Room FOREIGN KEY Room_Reservations_Room (room_id)
    REFERENCES Room (id);

-- Reference: Room_RoomType (table: Room)
ALTER TABLE Room ADD CONSTRAINT Room_RoomType FOREIGN KEY Room_RoomType (room_type_id)
    REFERENCES Room_Type (id);

-- Reference: Token_Token_Type (table: Token)
ALTER TABLE Token ADD CONSTRAINT Token_Token_Type FOREIGN KEY Token_Token_Type (token_type_id)
    REFERENCES Token_Type (id);

-- Reference: Token_User (table: Token)
ALTER TABLE Token ADD CONSTRAINT Token_User FOREIGN KEY Token_User (user_id)
    REFERENCES User (id);

-- Reference: UserRole_Role (table: User_Role)
ALTER TABLE User_Role ADD CONSTRAINT UserRole_Role FOREIGN KEY UserRole_Role (role_id)
    REFERENCES Role (id);

-- Reference: UserRole_User (table: User_Role)
ALTER TABLE User_Role ADD CONSTRAINT UserRole_User FOREIGN KEY UserRole_User (user_id)
    REFERENCES User (id);

-- Reference: Weekend_Place (table: Weekend)
ALTER TABLE Weekend ADD CONSTRAINT Weekend_Place FOREIGN KEY Weekend_Place (place_id)
    REFERENCES Place (id);

-- End of file.

