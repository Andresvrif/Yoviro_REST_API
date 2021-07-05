/* TRIGGERS */
drop trigger if exists before_insert_contratante;
create trigger before_insert_contratante before insert on contratantes for each row begin IF (NEW.numero_de_contratante IS NULL) THEN SELECT MAX(numero_de_contratante) INTO @max_label FROM contratantes;IF (@max_label IS NULL) THEN SET NEW.numero_de_contratante = CONCAT('CTE000001');ELSE SET NEW.numero_de_contratante = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0'));END IF;END IF;end

/* Creamos algunos usuarios */
INSERT INTO users (username, password, enabled) VALUES('andres', '$2a$10$debpqzhpXFd4O/Lx3kAhX.KeOqhesTfrJMStixsYSqcQFvIXicHbC', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);

/* Creamos algunos roles para los usuarios */
INSERT INTO authorities (user_id, authority) VALUES(1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_ADMIN');

INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Andres', 'Guzman', 'Canevaro', 'profesor@bolsadeideas.com', '2017-08-01', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('John', 'Doe', 'Law' ,'john.doe@gmail.com', '2017-08-02', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Linus', 'Torvalds', 'Teuva' , 'linus.torvalds@gmail.com', '2017-08-03', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Jane', 'Doe', 'Canario','jane.doe@gmail.com', '2017-08-04', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Rasmus', 'Lerdorf', 'Wing', 'rasmus.lerdorf@gmail.com', '2017-08-05', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Erich', 'Gamma', 'Wing', 'erich.gamma@gmail.com', '2017-08-06', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Richard', 'Helm', 'Wing', 'richard.helm@gmail.com', '2017-08-07', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Ralph', 'Johnson', 'Wing', 'ralph.johnson@gmail.com', '2017-08-08', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('John', 'Vlissides', 'Wing', 'john.vlissides@gmail.com', '2017-08-09', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('James', 'Gosling', 'Wing', 'james.gosling@gmail.com', '2017-08-010', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Bruce', 'Lee', 'Wing', 'bruce.lee@gmail.com', '2017-08-11', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Johnny', 'Doe', 'Wing', 'johnny.doe@gmail.com', '2017-08-12', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('John', 'Roe', 'Wing', 'john.roe@gmail.com', '2017-08-13', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Jane', 'Roe', 'Wing', 'jane.roe@gmail.com', '2017-08-14', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Richard', 'Doe', 'Wing', 'richard.doe@gmail.com', '2017-08-15', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Janie', 'Doe', 'Wing', 'janie.doe@gmail.com', '2017-08-16', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Phillip', 'Webb', 'Wing', 'phillip.webb@gmail.com', '2017-08-17', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Stephane', 'Nicoll', 'Wing', 'stephane.nicoll@gmail.com', '2017-08-18', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Sam', 'Brannen', 'Wing', 'sam.brannen@gmail.com', '2017-08-19', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Juergen', 'Hoeller', 'Wing', 'juergen.Hoeller@gmail.com', '2017-08-20', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Janie', 'Roe', 'Wing', 'janie.roe@gmail.com', '2017-08-21', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('John', 'Smith', 'Wing', 'john.smith@gmail.com', '2017-08-22', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Joe', 'Bloggs', 'Wing', 'joe.bloggs@gmail.com', '2017-08-23', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('John', 'Stiles', 'Wing', 'john.stiles@gmail.com', '2017-08-24', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Richard', 'Roe', 'Wing' , 'stiles.roe@gmail.com', '2017-08-25', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Andrés', 'Vila', 'Román' , 'andresvrif@gmail.com', '1990-08-23', '', NOW());
INSERT INTO contactos (nombre, apellido_paterno, apellido_materno, email, fecha_nacimiento, foto, create_at) VALUES('Robinzon', 'Vila', 'Zevallos' , null, '1990-08-23', '', NOW());

/* CONTRATANTES */
INSERT INTO contratantes (contacto_id) VALUES(26);