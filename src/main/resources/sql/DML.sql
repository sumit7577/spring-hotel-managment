-- Role
INSERT INTO Role (name) VALUES
                            ('ROLE_USER'),
                            ('ROLE_CLEANER'),
                            ('ROLE_RESTAURANT'),
                            ('ROLE_ADMIN');

-- Token
INSERT INTO Token_Type (type) VALUES
                                  ('ACCESS'),
                                  ('EMAIL_CONFIRMATION'),
                                  ('RESET_PASSWORD');

-- Room types
INSERT INTO Room_Type (room_occupancy, price, room_area) VALUES
                                                             (4, 20, 30),
                                                             (2, 15, 20),
                                                             (6, 30, 40);

-- Room
INSERT INTO Room (number, room_type_id, access_code) VALUES
                                                         (101, 1, 1528),
                                                         (102, 1, 4303),
                                                         (103, 1, 8169),
                                                         (104, 1, 3954),
                                                         (105, 1, 2501),
                                                         (106, 1, 5291),
                                                         (107, 1, 6436),
                                                         (108, 1, 1776),
                                                         (109, 1, 1776),
                                                         (110, 1, 9605),
                                                         (111, 1, 3496),
                                                         (112, 1, 8169),
                                                         (113, 1, 5908),
                                                         (114, 1, 9203),
                                                         (115, 1, 6703),
                                                         (116, 1, 8169),
                                                         (201, 1, 4212),
                                                         (202, 1, 7710),
                                                         (203, 1, 8586),
                                                         (204, 1, 1508),
                                                         (205, 1, 7895),
                                                         (206, 1, 7772),
                                                         (207, 1, 8921),
                                                         (208, 2, 3265),
                                                         (209, 2, 7295),
                                                         (210, 2, 1902),
                                                         (211, 2, 3894),
                                                         (212, 2, 8276),
                                                         (213, 2, 5426),
                                                         (214, 2, 3668),
                                                         (215, 1, 6449),
                                                         (216, 1, 4571),
                                                         (301, 3, 4654),
                                                         (302, 3, 3129),
                                                         (303, 3, 2141),
                                                         (304, 3, 2819),
                                                         (305, 3, 2042),
                                                         (306, 3, 4898),
                                                         (307, 3, 3809),
                                                         (308, 2, 2577),
                                                         (309, 2, 7427),
                                                         (310, 2, 1423),
                                                         (311, 2, 3997),
                                                         (312, 2, 4641),
                                                         (313, 2, 7770),
                                                         (314, 1, 7823),
                                                         (315, 1, 8347),
                                                         (316, 1, 6119);

-- Entertainment types
INSERT INTO Entertainment_Type (name, price) VALUES
                                                 ('Tennis', 10),
                                                 ('Kayak', 10),
                                                 ('Bicycle', 5);

-- Entertainment
INSERT INTO Entertainment (description, lock_code, entertainment_type_ID) VALUES
                                                                              ('Tennis court 1', null, 1),
                                                                              ('Tennis court 2', null, 1),
                                                                              ('Tennis court 3', null, 1),
                                                                              ('Tennis court 4', null, 1),
                                                                              ('Kayak 1', null, 2),
                                                                              ('Kayak 2', null, 2),
                                                                              ('Kayak 3', null, 2),
                                                                              ('Kayak 4', null, 2),
                                                                              ('Bicycle 1', 712421, 3),
                                                                              ('Bicycle 2', 642522, 3),
                                                                              ('Bicycle 3', 162653, 3),
                                                                              ('Bicycle 4', 461135, 3),
                                                                              ('Bicycle 5', 743353, 3),
                                                                              ('Bicycle 6', 635221, 3),
                                                                              ('Bicycle 7', 533531, 3);

-- Dish
INSERT INTO Dish (name, description, photo_directory) VALUES
                                                          ('Spaghetti Bolognese', 'Classic spaghetti with meat sauce', 'D/photo1'),
                                                          ('Caesar Salad', 'Crisp romaine lettuce, Parmesan cheese, and croutons', 'D/photo2'),
                                                          ('Grilled Salmon', 'Freshly grilled salmon with lemon and herbs', 'D/photo3');

-- Menu types
INSERT INTO Menu_Type (name) VALUES
                                 ('Breakfast'),
                                 ('Lunch'),
                                 ('Dinner');

-- Menu items
INSERT INTO Menu_Item (menu_date, dish_ID, menu_type_ID) VALUES
                                                             ('2023-07-10', 1, 1),
                                                             ('2023-07-10', 2, 1),
                                                             ('2023-07-10', 3, 1),
                                                             ('2023-07-10', 1, 2),
                                                             ('2023-07-10', 2, 2),
                                                             ('2023-07-10', 3, 2),
                                                             ('2023-07-10', 1, 3),
                                                             ('2023-07-10', 2, 3),
                                                             ('2023-07-10', 3, 3);

-- Place
INSERT INTO Place (name) VALUES
                              ('Courtyard'),
                              ('Restaurant');

-- Weekend
INSERT INTO Weekend (description, date_from, date_to, place_id) VALUES
                                                                    ('Bike ride', '2023-07-10 12:30:00', '2023-07-10 13:30:00', 1),
                                                                    ('Kayaking', '2023-07-10 14:00:00', '2023-07-10 15:00:00', 1),
                                                                    ('Cooking Classes', '2023-07-10 17:30:00', '2023-07-10 19:30:00', 2);

-- Payment
INSERT INTO Payment (date, amount) VALUES
                                       ('2023-07-10 12:30:00', 60),
                                       ('2023-07-10 12:30:00', 10),
                                       ('2023-07-10 12:30:00', 5),
                                       ('2023-07-10 12:30:00', 40);

-- User
INSERT INTO User (first_name, last_name, email, phone, discount, password, enabled) VALUES
                                                                                        ('John', 'Doe', 'johndoe@example.com', '+48859371835', 0, 'abf67e3fa8b1d3c5', null),
                                                                                        ('Robert', 'Johnson', 'robertjohnson@example.com', '+48768547568', 0, 'gererherh52eewef4y', null),
                                                                                        ('Amanda', 'Brown', 'amandabrown@example.com', '+48756321458', 0, 'c8b2a1e5f6d9', null),
                                                                                        ('William', 'Davis', 'williamdavis@example.com', '+48536945856', 20, 'e9d7c1b8a2f6', null),
                                                                                        ('Laura', 'Garcia', 'lauragarcia@example.com', '+48578632154', 0, 'b8a1c5d9e3f6', null);

-- User roles
INSERT INTO User_Role (user_id, role_id) VALUES
                                             (1,1),
                                             (2,1),
                                             (3,1),
                                             (4,1),
                                             (5,1);

-- Entertainment reservations
INSERT INTO Entertainment_Reservation (date, user_ID, entertainment_ID, payment_ID) VALUES
                                                                                        ('2023-07-10 12:30:00', 1, 1, 2),
                                                                                        ('2023-07-11 12:30:00', 1, 3, 3);

-- Room reservations
INSERT INTO Room_Reservation (from_date, to_date, booked_at, user_ID, room_id, payment_ID) VALUES
                                                                                    ('2023-07-10', '2023-07-10 12:30:00', '2023-07-12', 1, 10, NULL),
                                                                                    ('2023-07-10', '2023-07-10 12:30:00', '2023-07-12', 3, 11, NULL),
                                                                                    ('2023-07-10', '2023-07-10 12:30:00', '2023-07-12', 2, 12, NULL);

-- Cleaning history
INSERT INTO Cleaning_History (room_id, cleaned_at) VALUES
                                                                  (12, '2023-06-10'),
                                                                  (12, '2023-06-11'),
                                                                  (12, '2023-06-12');

