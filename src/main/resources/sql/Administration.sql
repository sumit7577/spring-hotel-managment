CREATE USER 'jorvik-restaurant'@'localhost' IDENTIFIED BY 'jorvik-restaurant';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.menu_type TO 'jorvik-restaurant'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.dish TO 'jorvik-restaurant'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.menu_item TO 'jorvik-restaurant'@'localhost';

CREATE USER 'jorvik-housekeeping'@'localhost' IDENTIFIED BY 'jorvik-housekeeping';
GRANT SELECT ON jorvik.room TO 'jorvik-housekeeping'@'localhost';
GRANT SELECT ON jorvik.room_reservation TO 'jorvik-housekeeping'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.cleaning_history TO 'jorvik-housekeeping'@'localhost';

CREATE USER 'jorvik-admin'@'localhost' IDENTIFIED BY 'jorvik-admin';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.* TO 'jorvik-admin'@'localhost';

CREATE USER 'BackendAPI-Connection'@'localhost' IDENTIFIED BY 'BackendAPI-Connection';
GRANT SELECT, INSERT, UPDATE, DELETE ON jorvik.* TO 'BackendAPI-Connection'@'localhost';

