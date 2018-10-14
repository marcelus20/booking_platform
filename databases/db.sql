
CREATE TABLE users (
                user_id INT AUTO_INCREMENT NOT NULL,
                user_type VARCHAR(1) NOT NULL,
                email VARCHAR(60) NOT NULL,
                user_last_name VARCHAR(25),
                user_password VARCHAR(500),
                date_joined DATE NOT NULL,
                user_phone VARCHAR(30),
                user_first_name VARCHAR(25),
                PRIMARY KEY (user_id, user_type)
);


CREATE TABLE location (
                user_id INT NOT NULL,
                user_type VARCHAR(1) NOT NULL,
                address_line_2 VARCHAR(70),
                city VARCHAR(50) DEFAULT Dublin NOT NULL,
                address_line_1 VARCHAR(70) NOT NULL,
                PRIMARY KEY (user_id, user_type)
);


CREATE TABLE bookings (
                user_id INT NOT NULL,
                user_type VARCHAR(1) NOT NULL,
                status VARCHAR(10) NOT NULL,
                b_time TIME NOT NULL,
                PRIMARY KEY (user_id, user_type)
);


ALTER TABLE location ADD CONSTRAINT users_location_fk
FOREIGN KEY (user_id, user_type)
REFERENCES users (user_id, user_type)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE bookings ADD CONSTRAINT location_bookings_fk
FOREIGN KEY (user_type, user_id)
REFERENCES location (user_type, user_id)
ON DELETE CASCADE
ON UPDATE CASCADE;
