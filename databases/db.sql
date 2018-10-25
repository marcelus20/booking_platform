
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

CREATE TABLE complaints (
                customer_id INT NOT NULL,
                s_id INT NOT NULL,
                complaint VARCHAR(500) NOT NULL,
                PRIMARY KEY (customer_id, s_id)
);


CREATE TABLE booking (
                customer_id INT NOT NULL,
                s_id INT NOT NULL,
                time TIME NOT NULL,
                PRIMARY KEY (customer_id, s_id)
);


CREATE TABLE service_type (
                customer_id INT NOT NULL,
                s_id INT NOT NULL,
                type VARCHAR(20) NOT NULL,
                PRIMARY KEY (customer_id, s_id)
);


ALTER TABLE location ADD CONSTRAINT service_provider_location_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE booking ADD CONSTRAINT service_provider_booking_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE complaints ADD CONSTRAINT service_provider_complaints_fk
FOREIGN KEY (s_id)
REFERENCES service_provider (s_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE booking ADD CONSTRAINT customers_booking_fk
FOREIGN KEY (customer_id)
REFERENCES customers (customer_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE complaints ADD CONSTRAINT customers_complaints_fk
FOREIGN KEY (customer_id)
REFERENCES customers (customer_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE service_type ADD CONSTRAINT booking_service_type_fk
FOREIGN KEY (customer_id, s_id)
REFERENCES booking (customer_id, s_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;


/*INSERTING THE DEFAULT ADMIN SUPERUSER HARDCODED*/
INSERT INTO admin (password, email) VALUES('admin', 'admin@admin.com');

