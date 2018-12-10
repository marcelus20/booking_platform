/*
FELIPE MANTOVANI 2017192 GROUP A. 
*/
DROP DATABASE IF EXISTS booking_platform;
CREATE DATABASE booking_platform;
USE booking_platform;


CREATE TABLE users (
                id INT AUTO_INCREMENT NOT NULL,
                user_type VARCHAR(20) NOT NULL,
                email VARCHAR(60) NOT NULL,
                password VARCHAR(128) NOT NULL,
                date_created DATE NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX users_idx
 ON users
 ( email );

CREATE TABLE phone_list (
                phone_id INT AUTO_INCREMENT NOT NULL,
                id INT NOT NULL,
                phone VARCHAR(25) NOT NULL,
                PRIMARY KEY (phone_id)
);


CREATE TABLE logs (
                log_id INT AUTO_INCREMENT NOT NULL,
                id INT NOT NULL,
                activity_log VARCHAR(120) NOT NULL,
                PRIMARY KEY (log_id)
);

ALTER TABLE logs COMMENT 'This table will record a list of activities that user will do, such as subscribing, booking, deleting and etc. It records enum values';


CREATE TABLE service_provider (
                s_id INT AUTO_INCREMENT NOT NULL,
                company_full_name VARCHAR(40) NOT NULL,
                approved_status VARCHAR(20) NOT NULL,
                PRIMARY KEY (s_id)
);


CREATE TABLE booking_slots (
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                s_id INT NOT NULL,
                availability BOOLEAN DEFAULT True NOT NULL,
                PRIMARY KEY (timestamp, s_id)
);


CREATE TABLE location (
                s_id INT NOT NULL,
                eir_code VARCHAR(10) NOT NULL,
                second_line_address VARCHAR(30) NOT NULL,
                first_line_address VARCHAR(50) NOT NULL,
                city VARCHAR(20) DEFAULT 'Dublin' NOT NULL,
                PRIMARY KEY (s_id)
);

ALTER TABLE location COMMENT 'This table represents a sub class, so the relation is one to one';


CREATE TABLE customers (
                c_id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(25) NOT NULL,
                last_name VARCHAR(25) NOT NULL,
                PRIMARY KEY (c_id)
);


CREATE TABLE complaints (
                complaint_ID INT AUTO_INCREMENT NOT NULL,
                s_id INT NOT NULL,
                c_id INT NOT NULL,
                complaint_status VARCHAR(20) DEFAULT 'PENDENT' NOT NULL, 
		complaint VARCHAR(500) NOT NULL,
                PRIMARY KEY (complaint_ID)
);


CREATE TABLE booking (
                time_stamp DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                s_id INT NOT NULL,
                c_id INT NOT NULL,
                booking_status VARCHAR(45) NOT NULL,
                review VARCHAR(20),
                PRIMARY KEY (time_stamp, s_id, c_id)
);


ALTER TABLE customers ADD CONSTRAINT users_customers_fk
FOREIGN KEY (c_id)
REFERENCES users (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE service_provider ADD CONSTRAINT users_service_provider_fk
FOREIGN KEY (s_id)
REFERENCES users (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE logs ADD CONSTRAINT users_logs_fk
FOREIGN KEY (id)
REFERENCES users (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE phone_list ADD CONSTRAINT users_phone_list_fk
FOREIGN KEY (id)
REFERENCES users (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE location ADD CONSTRAINT service_provider_location_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE booking_slots ADD CONSTRAINT service_provider_booking_slots_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE complaints ADD CONSTRAINT service_provider_complaints_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE booking ADD CONSTRAINT booking_slots_booking_fk
FOREIGN KEY (time_stamp, s_id)
REFERENCES booking_slots (timestamp, s_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE booking ADD CONSTRAINT customers_booking_fk
FOREIGN KEY (c_id)
REFERENCES customers (c_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE complaints ADD CONSTRAINT customers_complaints_fk
FOREIGN KEY (c_id)
REFERENCES customers (c_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;





INSERT INTO users (user_type, email, password, date_created) VALUES 
('ADMIN', 'admin@admin.admin', '21232F297A57A5A743894A0E4A801FC3', '2018-11-15'), 
('CUSTOMER', 'marcelus20felipe@gmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'johnatan_heartike@hotmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'rian_pridget@yahoo.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'maire_donnegal@cct.ie', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'giu_marcella@gmail.com', 'F95C9FC67FFEF8657E411E5A5216A8C9', '2018-11-18'),
('CUSTOMER', 'leon_scott_kenedy@rpd.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'redifield_c@outlook.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('CUSTOMER', 'Birkin_sherry@rpd.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'hartes@gmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'woxx@outlook.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'him_b@rte.ie', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'pat_b_shop@yahoo.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'winters_t@gmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'g_barber20@rte.ie', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'Aidan17@gmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18'),
('SERVICE_PROVIDER', 'tark_barbers@gmail.com', '646E613EFCFC1317061B1DF9340E3726', '2018-11-18');


INSERT INTO service_provider VALUES
('10', 'Hartes Barbers', 'APPROVED'),
('11', 'Woxx Barbers', 'APPROVED'),
('12', 'HIM Barbershop', 'APPROVED'),
('13', 'Pats Barber Shop', 'APPROVED'),
('14', 'Tom Winters Barbers', 'APPROVED'),
('15', 'THE GRAFTON BARBER', 'APPROVED'),
('16', 'Aidan Barry Barbers','APPROVED'),
('17', 'Tarkans Traditional Turkish Barbers', 'APPROVED');

INSERT INTO location VALUES
('10', 'd3', 'Ap 23','westmore avenue', 'Dublin'),
('11', 'd9', '', 'whitehall shanowen road', 'Dublin'),
('12', 'C8', '', '38, st church ballingout','Cork'),
('13', 'N03', '', 'Estern Judgment', 'Navan'),
('14', 'D22', 'Ap 15 2nd floor', '10, sacrament road','Dublin'),
('15', 'D14', '', '22 Haricut street','Dublin'),
('16', 'Dr1','', '78 Michigan Liver','Droghda'),
('17', 'S78','', '71 Sorcerers Avenue', 'Sligo');

INSERT INTO customers VALUES
('2', 'Felipe', 'Mantovani'),
('3', 'Johnathan', 'Hartike'),
('4', 'Rian', 'Pridget'),
('5', 'Maire', 'Donnegal'),
('6', 'Giulia', 'Marcella'),
('7', 'Leon', 'Keneddy'),
('8', 'Claire', 'Redfield'),
('9', 'Sherry', 'Birkin');



INSERT INTO booking_slots 
VALUES 
('2018-11-19 08:30:00', '10', '0'),
('2018-11-19 11:30:00', '10', '1'),
('2018-11-19 12:00:00', '16', '1'),
('2018-11-19 16:00:00', '16', '1'),
('2018-11-19 17:00:00', '16', '0'),
('2018-11-20 13:30:00', '12', '0'),
('2018-11-20 15:30:00', '12', '0'), 
('2018-11-21 12:00:00', '13', '0'),
('2018-11-21 14:30:00', '13', '0'),
('2018-11-21 17:00:00', '13', '0'); 

INSERT INTO booking VALUES
('2018-11-19 08:30:00', '10', '2', 'COMPLETE', 'NO_REVIEW_ADDED'),
('2018-11-19 17:00:00', '16', '4', 'CONFIRMED', 'NO_REVIEW_ADDED'),
('2018-11-20 13:30:00', '12', '5', 'COMPLETE', 'NO_REVIEW_ADDED'),
('2018-11-20 15:30:00', '12', '3', 'COMPLETE', 'NO_REVIEW_ADDED'),
('2018-11-21 12:00:00', '13', '5', 'COMPLETE', 'NO_REVIEW_ADDED'),
('2018-11-21 14:30:00', '13', '6', 'PENDENT', 'NO_REVIEW_ADDED'),
('2018-11-21 17:00:00', '13', '7', 'CONFIRMED','NO_REVIEW_ADDED');

INSERT INTO phone_list (id, phone) VALUES
('2', '0854712654'),
('3', '2453767856'),
('4', '2345767676'),
('5', '0253647532'),
('6', '0856567688'),
('7', '0853712655'),
('8', '0853367537'),
('9', '0854754345'),
('10', '0825771267'),
('11', '0854719985'),
('12', '0854712742'),
('13', '0854712626'),
('14', '0854712237'),
('15', '0854678444'),
('16', '0854712264'),
('17', '0734712654');

INSERT INTO logs (id, activity_log) VALUES 
('2', 'Felipe Mantovani subscribed'),
('3', 'Johnathan Hartike subscribed'),
('4', 'Rian Pridget subscribed'),
('5', 'Maire Donnegal subscribed'),
('6', 'Giulia Marcella subscribed'),
('7', 'Leon Keneddy subscribed'),
('8', 'Claire Redfield subsribed'),
('9',  'Sherry Birkin subscribed'),
('10', 'Hartes Barbers subscribed'),
('11', 'Woxx Barbers subscribed '),
('12', 'HIM Barbershop subscribed'),
('13', 'Pats Barber Shop subscribed'),
('14', 'Tom Winters Barbers subscribed'),
('15', 'THE GRAFTON BARBER subscribed'),
('16', 'Aidan Barry Barbers'),
('17', 'Tarkans Traditional Turkish Barbers');

INSERT INTO complaints (s_id, c_id, complaint)
VALUES 
('10', '3', 'did not like the service'),
('10', '5', 'very rude the barber, wont ever come bak there'),
('16', '4', 'I arrived on booked time the lad took care of me 2 hours later, do not recomend.'),
('12', '2', 'The slot was available in the system, but I got there and the place was closed'),
('13', '7', 'very expensive, such a robbery.');

