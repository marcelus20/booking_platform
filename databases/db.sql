
CREATE TABLE users (
                u_id INT AUTO_INCREMENT NOT NULL,
                email VARCHAR(60) NOT NULL,
                u_l_name VARCHAR(25),
                u_pass VARCHAR(500),
                u_type VARCHAR(1) NOT NULL,
                d_acc_created DATE NOT NULL,
                u_phone VARCHAR(30),
                u_f_name VARCHAR(25),
                PRIMARY KEY (u_id)
);


CREATE TABLE location (
                u_id INT NOT NULL,
                l_id INT AUTO_INCREMENT NOT NULL,
                l_add_line_2 VARCHAR(70),
                city VARCHAR(50) DEFAULT 'Dublin' NOT NULL,
                l_add_line_1 VARCHAR(70) NOT NULL,
                PRIMARY KEY (l_id)
);


CREATE TABLE bookings (
                b_id INT AUTO_INCREMENT NOT NULL,
                u_id INT NOT NULL,
                l_id INT NOT NULL,
                b_status VARCHAR(10) NOT NULL,
                b_time TIME NOT NULL,
                PRIMARY KEY (b_id, u_id, l_id)
);


ALTER TABLE location ADD CONSTRAINT users_location_fk
FOREIGN KEY (u_id)
REFERENCES users (u_id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE bookings ADD CONSTRAINT location_bookings_fk
FOREIGN KEY (l_id, u_id)
REFERENCES location (l_id, u_id)
ON DELETE CASCADE
ON UPDATE CASCADE;
/*ADDING ADMIN TO THE TABLE USERS*/
INSERT INTO users(email, u_type, d_acc_created, u_pass)
 VALUES ('admin@admin.com', 'a', '2018-10-12', 'admin');

/*ADDING SOME RANDOM USERS TO THE USERS TABLE*/
INSERT INTO users(email, u_l_name, u_type, d_acc_created, u_phone, u_pass, u_f_name)
 VALUES ('sara_toledo87@gmail.com', 'de Toledo','c', '2018-10-12', '0895458732','sarath145', 'Sara');
INSERT INTO users(email, u_l_name, u_type, d_acc_created, u_phone, u_pass, u_f_name)
 VALUES ('paullusKina@live.com', 'Kenny','c', '2018-10-17', '0854476325','gthjr32', 'Paul');
INSERT INTO users(email, u_l_name, u_type, d_acc_created, u_phone, u_pass, u_f_name)
 VALUES ('n_megan@ig.com', 'Nolan','c', '2018-10-7', '0867412585','mirindaKe45rath', 'Megan');
INSERT INTO users(email, u_l_name, u_type, d_acc_created, u_phone, u_pass, u_f_name)
 VALUES ('jhow_nba@gmail.com', 'Jonathan','s', '2018-10-20', '08142557','phth47', 'Mark');
