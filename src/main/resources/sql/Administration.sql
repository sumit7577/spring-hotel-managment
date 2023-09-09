CREATE USER 'jorvik-restaurant'@'localhost' IDENTIFIED BY 'jorvik-restaurant';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.Menu_Type TO 'jorvik-restaurant'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.Dish TO 'jorvik-restaurant'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.Menu_Item TO 'jorvik-restaurant'@'localhost';

CREATE USER 'jorvik-housekeeping'@'localhost' IDENTIFIED BY 'jorvik-housekeeping';
GRANT SELECT ON jorvik.Room TO 'jorvik-housekeeping'@'localhost';
GRANT SELECT ON jorvik.Room_Reservation TO 'jorvik-housekeeping'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.Cleaning_History TO 'jorvik-housekeeping'@'localhost';

CREATE USER 'jorvik-admin'@'localhost' IDENTIFIED BY 'jorvik-admin';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.* TO 'jorvik-admin'@'localhost';

CREATE USER 'BackendAPI-Connection'@'localhost' IDENTIFIED BY 'BackendAPI-Connection';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.* TO 'BackendAPI-Connection'@'localhost';