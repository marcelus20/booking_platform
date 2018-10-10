
CREATE TABLE admin (
                a_id INT AUTO_INCREMENT NOT NULL,
                a_email VARCHAR(60) NOT NULL,
                a_pass VARCHAR(500) NOT NULL,
                PRIMARY KEY (a_id, a_email)
);


CREATE TABLE service_provider (
                s_id INT AUTO_INCREMENT NOT NULL,
                s_email VARCHAR(60) NOT NULL,
                s_f_name VARCHAR(25) NOT NULL,
                s_phone_n VARCHAR(20) NOT NULL,
                s_l_name VARCHAR(50) NOT NULL,
                s_pass VARCHAR(500) NOT NULL,
                s_date_joined DATE NOT NULL,
                s_location VARCHAR(300) NOT NULL,
                PRIMARY KEY (s_id, s_email)
);


CREATE TABLE customer (
                c_id INT AUTO_INCREMENT NOT NULL,
                s_id INT NOT NULL,
                s_email VARCHAR(60) NOT NULL,
                c_email VARCHAR(60) NOT NULL,
                c_f_name VARCHAR(25) NOT NULL,
                c_phone_n VARCHAR(20) NOT NULL,
                c_date_joined DATE NOT NULL,
                c_pass VARCHAR(500) NOT NULL,
                c_l_name VARCHAR(50) NOT NULL,
                PRIMARY KEY (c_id, s_id, s_email, c_email)
);


ALTER TABLE customer ADD CONSTRAINT service_provider_customer_fk
FOREIGN KEY (s_email, s_id)
REFERENCES service_provider (s_email, s_id)
ON DELETE CASCADE
ON UPDATE CASCADE;

/*creating the root admin hardcoded*/
INSERT INTO admin (a_email, a_pass) VALUES ('admin@admin.com', 'admin');

