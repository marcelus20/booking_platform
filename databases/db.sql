DROP DATABASE IF EXISTS booking_platform;
CREATE DATABASE booking_platform;
USE booking_platform;



CREATE TABLE admin (
                id INT AUTO_INCREMENT NOT NULL,
                password VARCHAR(128) NOT NULL,
                email VARCHAR(60) NOT NULL,
                PRIMARY KEY (id)
);


CREATE TABLE service_provider (
                s_id INT AUTO_INCREMENT NOT NULL,
                password VARCHAR(128) NOT NULL,
                email VARCHAR(60) NOT NULL,
                date_of_account_creation DATE NOT NULL,
                phone VARCHAR(30) NOT NULL,
                company_full_name VARCHAR(40) NOT NULL,
                approved_status VARCHAR(20) NOT NULL,
                PRIMARY KEY (s_id)
);


CREATE UNIQUE INDEX service_provider_idx
 ON service_provider
 ( email );

CREATE TABLE booking_slots (
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                s_id INT NOT NULL,
                availability BOOLEAN DEFAULT True NOT NULL,
                PRIMARY KEY (timestamp, s_id)
);


CREATE TABLE location (
                s_id INT NOT NULL,
                first_line_address VARCHAR(50) NOT NULL,
                eir_code VARCHAR(10) NOT NULL,
                city VARCHAR(20) DEFAULT 'Dublin' NOT NULL,
                second_line_address VARCHAR(30),
                PRIMARY KEY (s_id)
);


CREATE TABLE customers (
                customer_id INT AUTO_INCREMENT NOT NULL,
                password VARCHAR(128) NOT NULL,
                email VARCHAR(60) NOT NULL,
                phone VARCHAR(30) NOT NULL,
                first_name VARCHAR(25) NOT NULL,
                last_name VARCHAR(25) NOT NULL,
                date_of_account_creation DATE NOT NULL,
                PRIMARY KEY (customer_id)
);


CREATE UNIQUE INDEX customers_idx
 ON customers
 ( email, phone );

CREATE TABLE booking (
                time_stamp DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                customer_id INT NOT NULL,
                s_id INT NOT NULL,
                booking_status VARCHAR(15) NOT NULL,
                complaint VARCHAR(500),
                PRIMARY KEY (time_stamp, customer_id, s_id)
);


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

ALTER TABLE booking ADD CONSTRAINT booking_slots_booking_fk
FOREIGN KEY (time_stamp, s_id)
REFERENCES booking_slots (timestamp, s_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE booking ADD CONSTRAINT customers_booking_fk
FOREIGN KEY (customer_id)
REFERENCES customers (customer_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

/*INSERTING THE DEFAULT ADMIN SUPERUSER HARDCODED*/
INSERT INTO admin (password, email) VALUES('21232F297A57A5A743894A0E4A801FC3', 'admin@admin.admin'); /*PASSWORD IS: 'admin' */

