INSERT INTO Dish (name, description, photo_directory) VALUES
  ('Spaghetti Bolognese', 'Classic spaghetti with meat sauce', 'D/photo1'),
  ('Caesar Salad', 'Crisp romaine lettuce, Parmesan cheese, and croutons', 'D/photo2'),
  ('Grilled Salmon', 'Freshly grilled salmon with lemon and herbs', 'D/photo3');
  
INSERT INTO MenuType (name) VALUES
	('Breakfast'),
	('Lunch'),
    ('Dinner');
 
INSERT INTO MenuItem (menu_date, dish_ID, menuType_ID) VALUES
	('2023-05-10', 1, 1),
	('2023-05-10', 2, 1),
    ('2023-05-10', 3, 1),
    ('2023-05-10', 1, 2),
	('2023-05-10', 2, 2),
    ('2023-05-10', 3, 2),
    ('2023-05-10', 1, 3),
	('2023-05-10', 2, 3),
    ('2023-05-10', 3, 3);
    
INSERT INTO Weekend (place, description, date_from, date_to) VALUES
	('Courtyard', 'Bike ride', '2023-05-10 12:30:00', '2023-03-10 13:30:00'),
	('Courtyard', 'Kayaking', '2023-05-10 14:00:00', '2023-03-10 15:00:00'),
    ('Restaurant', 'Cooking Classes', '2023-05-10 17:30:00', '2023-03-10 19:30:00');
    
INSERT INTO EntertainmentType (name, price) VALUES
	('Tennis', 10),
	('Kayak', 10),
    ('Bicycle', 5);    
    
INSERT INTO Entertainment (description, entertainmentType_ID) VALUES
	('Tennis court 1', 1),
    ('Tennis court 2', 1),
    ('Tennis court 3', 1),
    ('Tennis court 4', 1),
	('Kayak 1', 2),
    ('Kayak 2', 2),
    ('Kayak 3', 2),
    ('Kayak 4', 2),
    ('Bicycle 1, Lock code 712-421', 3),
    ('Bicycle 2, Lock code 642-522', 3),
    ('Bicycle 3, Lock code 162-653', 3),
    ('Bicycle 4, Lock code 461-135', 3),
    ('Bicycle 5, Lock code 743-153', 3),
    ('Bicycle 6, Lock code 635-221', 3),
    ('Bicycle 7, Lock code 533-531', 3);
    
INSERT INTO RoomType (room_occupancy, price, room_area) VALUES
	(4, 20, 30),
	(2, 15, 20),
    (6, 30, 40);   
    
INSERT INTO Room (number, access_code, floor, roomType_ID, cleaning_request) VALUES
	(1, 05-31, 0, 1, '2023-05-10 12:30:00'),
	(2, 42-31, 0, 1, '2023-05-10 12:30:00'),
    (3, 64-31, 0, 1, NULL),
    (4, 07-11, 0, 1, NULL),
    (5, 72-63, 0, 1, NULL),
    (6, 15-71, 0, 1, NULL),
    (7, 75-32, 0, 1, NULL),
    (8, 78-32, 0, 1, NULL),
    (11, 34-66, 1, 1, '2023-05-10 12:30:00'),
    (12, 25-64, 1, 1, NULL),
    (13, 60-31, 1, 1, NULL),
    (14, 82-42, 1, 1, NULL),
    (15, 02-44, 1, 1, '2023-05-10 12:30:00'),
    (16, 82-42, 1, 1, NULL),
    (17, 31-65, 1, 1, NULL),
    (18, 65-11, 1, 1, NULL),
    (21, 54-54, 2, 2, NULL),
    (22, 24-45, 2, 2, NULL),
    (23, 88-75, 2, 2, NULL),
    (24, 75-35, 2, 2, NULL),
    (25, 65-55, 2, 2, '2023-05-10 12:30:00'),
    (26, 24-34, 2, 2, NULL),
    (27, 42-23, 2, 2, NULL),
    (28, 12-64, 2, 2, '2023-05-10 12:30:00'),
    (31, 31-44, 3, 2, NULL),
    (32, 86-97, 3, 2, NULL),
    (33, 34-07, 3, 2, '2023-05-10 12:30:00'),
    (34, 11-43, 3, 2, NULL),
    (35, 05-76, 3, 2, '2023-05-10 12:30:00'),
    (36, 24-14, 3, 2, '2023-05-10 12:30:00'),
    (37, 05-37, 3, 2, NULL),
    (38, 22-75, 3, 2, NULL),
    (41, 92-00, 4, 3, NULL),
    (42, 26-77, 4, 3, NULL),
    (43, 85-25, 4, 3, NULL),
    (44, 54-75, 4, 3, '2023-05-10 12:30:00'),
    (45, 23-44, 4, 3, '2023-05-10 12:30:00'),
    (46, 75-21, 4, 3, NULL),
    (47, 25-11, 4, 3, NULL),
    (48, 88-35, 4, 3, '2023-05-10 12:30:00');
    
INSERT INTO Role (name) VALUES
	('Client'),
	('Cleaner'),
    ('Restaurant'),
    ('Administrator');
    
INSERT INTO User (first_name, last_name, email, phone, discount, hash, salt, refresh_token) VALUES
	('John', 'Doe', 'johndoe@example.com', '+48859371835', 0, 'abf67e3fa8b1d3c5', 'f3a9c7e6', 'd6a1e5c8'),
	('Robert', 'Johnson', 'robertjohnson@example.com', '+48768547568', 0, 'f7e3a1d5c8b2', 'd1b7e9c2', 'g4h8j9k7'),
	('Amanda', 'Brown', 'amandabrown@example.com', '+48756321458', 0, 'c8b2a1e5f6d9', 'e4f8g2h6', 'j6k9l7m4'),
	('William', 'Davis', 'williamdavis@example.com', '+48536945856', 20, 'e9d7c1b8a2f6', 'h2g5j7k9', 'l4m7n9p6'),
	('Laura', 'Garcia', 'lauragarcia@example.com', '+48578632154', 0, 'b8a1c5d9e3f6', 'j5k8l7m4', 'n9p6q7r5');

INSERT INTO UserRoles(user_ID, role_ID) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1);
    
INSERT INTO Payment (date, amount) VALUES
	('2023-05-10 12:30:00', 60),
	('2023-05-10 12:30:00', 10),
    ('2023-05-10 12:30:00', 5),
    ('2023-05-10 12:30:00', 40); 
    
INSERT INTO EntertainmentReservations (date, time, user_ID, entertainment_ID, payment_ID) VALUES
	('2023-05-10', '12:00:00', 1, 1, 2),
    ('2023-05-10', '15:00:00', 1, 3, 3);
    
INSERT INTO RoomReservations (from_date, to_date, room_number, user_ID, payment_ID) VALUES
	('2023-05-10', '2023-05-12', 11, 1, 1),
    ('2023-05-10', '2023-05-12', 25, 3, 4),
    ('2023-05-10', '2023-05-12', 31, 2, NULL);    
	

  
  

