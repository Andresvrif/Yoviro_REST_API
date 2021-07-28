/* TRIGGERS */
drop trigger if exists before_insert_contractor;
create trigger before_insert_contractor before insert on contractor for each row begin IF (NEW.contractor_number IS NULL) THEN SELECT MAX(contractor_number) INTO @max_label FROM contractor;IF (@max_label IS NULL) THEN SET NEW.contractor_number = CONCAT('CTA000001');ELSE SET NEW.contractor_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0'));END IF;END IF;end
drop trigger if exists before_insert_contrato;
create trigger before_insert_contrato before insert on agreement for each row begin IF (NEW.agreement_number IS NULL) THEN SELECT MAX(agreement_number) INTO @max_label FROM agreement; IF (@max_label IS NULL) THEN SET NEW.agreement_number = CONCAT('CTR000001'); ELSE SET NEW.agreement_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end
drop trigger if exists before_insert_job;
create trigger before_insert_job before insert on job for each row begin IF (NEW.job_number IS NULL) THEN SELECT MAX(job_number) INTO @max_label FROM job; IF (@max_label IS NULL) THEN SET NEW.job_number = CONCAT('JOB000001'); ELSE SET NEW.job_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end

/* USUARIOS */
INSERT INTO users (username, password, enabled) VALUES('andres', '$2a$10$debpqzhpXFd4O/Lx3kAhX.KeOqhesTfrJMStixsYSqcQFvIXicHbC', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);

/* ROLES PARA USUARIOS */
INSERT INTO authorities (user_id, authority) VALUES(1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_ADMIN');

/* CONTACTOS */
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Andres', null, 'Guzman', 'Canevaro', 'profesor@bolsadeideas.com', '2017-08-01', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('John', null, 'Doe', 'Law' ,'john.doe@gmail.com', '2017-08-02', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Linus', null, 'Torvalds', 'Teuva' , 'linus.torvalds@gmail.com', '2017-08-03', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Jane', null, 'Doe', 'Canario','jane.doe@gmail.com', '2017-08-04', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Rasmus', null, 'Lerdorf', 'Wing', 'rasmus.lerdorf@gmail.com', '2017-08-05', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Erich', null, 'Gamma', 'Wing', 'erich.gamma@gmail.com', '2017-08-06', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Richard', null, 'Helm', 'Wing', 'richard.helm@gmail.com', '2017-08-07', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Ralph',  null,'Johnson', 'Wing', 'ralph.johnson@gmail.com', '2017-08-08', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('John',  null,'Vlissides', 'Wing', 'john.vlissides@gmail.com', '2017-08-09', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('James',  null,'Gosling', 'Wing', 'james.gosling@gmail.com', '2017-08-010', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Bruce',  null,'Lee', 'Wing', 'bruce.lee@gmail.com', '2017-08-11', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Johnny',  null,'Doe', 'Wing', 'johnny.doe@gmail.com', '2017-08-12', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('John',  null,'Roe', 'Wing', 'john.roe@gmail.com', '2017-08-13', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Jane',  null,'Roe', 'Wing', 'jane.roe@gmail.com', '2017-08-14', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Richard',  null,'Doe', 'Wing', 'richard.doe@gmail.com', '2017-08-15', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Janie',  null,'Doe', 'Wing', 'janie.doe@gmail.com', '2017-08-16', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Phillip',  null,'Webb', 'Wing', 'phillip.webb@gmail.com', '2017-08-17', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Stephane',  null,'Nicoll', 'Wing', 'stephane.nicoll@gmail.com', '2017-08-18', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Sam',  null,'Brannen', 'Wing', 'sam.brannen@gmail.com', '2017-08-19', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Juergen',  null,'Hoeller', 'Wing', 'juergen.Hoeller@gmail.com', '2017-08-20', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Janie',  null,'Roe', 'Wing', 'janie.roe@gmail.com', '2017-08-21', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('John',  null,'Smith', 'Wing', 'john.smith@gmail.com', '2017-08-22', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Joe',  null,'Bloggs', 'Wing', 'joe.bloggs@gmail.com', '2017-08-23', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('John',  null,'Stiles', 'Wing', 'john.stiles@gmail.com', '2017-08-24', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Richard',  null,'Roe', 'Wing' , 'stiles.roe@gmail.com', '2017-08-25', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Andrés', 'Antony','Vila', 'Román' , 'andresvrif@gmail.com', '1990-08-23', '', NOW());
INSERT INTO contact (first_name, second_name, first_last_name, second_last_name, email, birth_date, photo, create_at) VALUES('Robinzon','Leoncio', 'Vila', 'Zevallos' , null, '1990-08-23', '', NOW());

/* DOCUMENTO DE IDENTIDAD */
INSERT INTO official_id (official_id_type, official_id_number, contact_id, primary_official_id) VALUES('dni', '70007861', 26, 1);
INSERT INTO official_id (official_id_type, official_id_number, contact_id) VALUES('passport', '116417071', 26);
INSERT INTO official_id (official_id_type, official_id_number, contact_id, primary_official_id) VALUES('dni', '48615978', 27, 1);

/* CONTRATANTES */
INSERT INTO contractor (contact_id) VALUES(26);
INSERT INTO agreement (contractor_id) VALUES(1);
INSERT INTO resident (enable, contact_id) VALUES (1,27);

/* ACTIVITY PATTERNS */
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 1, 'Desayuno Normal', 'Desayuno sin restricciones', '2020-07-05', null, '07:30:00', 'desayuno_normal', 1);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 1, 'Desayuno celiaco', 'Desayuno con arina arina celiaca', '2020-07-05', null, '07:30:00', 'desayuno_celiaco', 0);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 2, 'Cuidado de hongos', 'Aplicar fungisil en crema', '2020-07-25', null, '07:00:00', 'crema_hongos_mañana', 1);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 2, 'Cuidado de hongos', 'Aplicar fungisil en crema', '2020-07-25', null, '19:00:00', 'crema_hongos_tarde', 0);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 4, 'Paseo al parque', 'Paseo al parque quintana', '2020-07-20', null, '12:00:00', 'paseo_parque', 0);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 1, 'Lonche', 'Lonche sin restricciones', '2020-07-05', null, '07:00:00', 'lonche_normal', 1);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '07:00:00', 'inyeccion_insulina_mañana', 1);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '15:00:00', 'inyeccion_insulina_tarde', 1);
INSERT INTO activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '23:00:00', 'inyeccion_insulina_noche', 1);