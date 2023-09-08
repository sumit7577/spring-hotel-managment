CREATE INDEX idx_user_email ON User(email);

CREATE INDEX idx_user_id_on_room_reservation ON Room_Reservation(user_id);
CREATE INDEX idx_payment_id_on_room_reservation ON Room_Reservation(payment_id);

CREATE INDEX idx_room_type_id ON Room(room_Type_id);
CREATE INDEX idx_room_date_from_to ON Room_Reservation(from_Date, to_Date);

CREATE INDEX idx_entertainment_type_id ON Entertainment(entertainment_Type_id);
CREATE INDEX idx_entertainment_date_from_to ON Entertainment_Reservation(from_date, to_date);

CREATE INDEX idx_menu_date ON Menu_Item(menu_Date);
CREATE INDEX idx_menu_type_id ON Menu_Item(menu_Type_id);



DROP PROCEDURE IF EXISTS InsertMenuItemsForNext5Years
DELIMITER //
CREATE PROCEDURE InsertMenuItemsForNext5Years()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE total_days INT DEFAULT 5 * 365; -- for 5 years, assuming no leap years

    WHILE i < total_days DO
            INSERT INTO Menu_Item (menu_date, dish_ID, menu_type_ID) VALUES
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 1, 1),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 2, 1),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 3, 1),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 4, 1),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 5, 1),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 6, 2),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 7, 2),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 8, 2),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 9, 2),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 10, 3),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 11, 3),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 12, 3),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 13, 3),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 14, 3),
                                                                         (DATE_ADD(CURDATE(), INTERVAL i DAY), 15, 3);

            SET i = i + 1;
        END WHILE;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS AddFutureEntertainmentReservations
DELIMITER //
CREATE PROCEDURE AddFutureEntertainmentReservations(
    IN from_time TIME,
    IN to_time TIME
)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE j INT DEFAULT 0;
    DECLARE random_user_id INT;
    DECLARE random_entertainment_id INT;

    WHILE i < 5 DO
            SET j = 0;
            WHILE j < 365 DO
                    SET random_user_id = FLOOR(1 + (RAND() * 40));
                    SET random_entertainment_id = FLOOR(1 + (RAND() * 15));
                    SET @from_date_time = TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL (i * 365 + j) DAY), from_time);
                    SET @to_date_time = TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL (i * 365 + j) DAY), to_time);

                    INSERT INTO Entertainment_Reservation (from_date, to_date, booked_at, user_id, entertainment_id, payment_id)
                    VALUES (
                               @from_date_time,
                               @to_date_time,
                               NOW(),
                               random_user_id,
                               random_entertainment_id,
                               NULL
                           );
                    SET j = j + 1;
                END WHILE;
            SET i = i + 1;
        END WHILE;
END;
//
DELIMITER ;


DROP PROCEDURE IF EXISTS AddFutureRoomReservations
DELIMITER //
CREATE PROCEDURE AddFutureRoomReservations(
    IN payment_amount INT
)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE j INT DEFAULT 0;
    DECLARE k INT DEFAULT 0;
    DECLARE random_user_id INT;
    DECLARE random_room_id INT;
    DECLARE new_payment_id INT;
    DECLARE cur_date DATE;

    WHILE i < 5 DO
            SET j = 0;
            WHILE j < 365 DO

                    SET random_user_id = FLOOR(1 + (RAND() * 40));
                    SET random_room_id = FLOOR(1 + (RAND() * 48));
                    SET cur_date = DATE_ADD(CURDATE(), INTERVAL (i * 365 + j) DAY);

                    -- Create Payment Record
                    INSERT INTO Payment (date, amount) VALUES (NOW(), payment_amount);
                    SET new_payment_id = LAST_INSERT_ID();

                    -- Create Room Reservation
                    INSERT INTO Room_Reservation (from_date, to_date, booked_at, user_id, room_id, payment_id)
                    VALUES (
                               cur_date,
                               DATE_ADD(cur_date, INTERVAL 1 DAY),
                               NOW(),
                               random_user_id,
                               random_room_id,
                               new_payment_id
                           );



                    SET j = j + 1;
                END WHILE;
            SET i = i + 1;
        END WHILE;
END;
//
DELIMITER ;

CALL InsertMenuItemsForNext5Years();
CALL AddFutureEntertainmentReservations('12:30:00', '13:30:00');
CALL AddFutureRoomReservations(100);
