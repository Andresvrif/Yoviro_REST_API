/* TRIGGERS */
drop trigger if exists before_insert_contractor;
create trigger before_insert_contractor before insert on contractor for each row begin IF (NEW.contractor_number IS NULL) THEN SELECT MAX(contractor_number) INTO @max_label FROM contractor;IF (@max_label IS NULL) THEN SET NEW.contractor_number = CONCAT('CTA000001');ELSE SET NEW.contractor_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0'));END IF;END IF;end
drop trigger if exists before_insert_contrato;
create trigger before_insert_contrato before insert on agreement for each row begin IF (NEW.agreement_number IS NULL) THEN SELECT MAX(agreement_number) INTO @max_label FROM agreement; IF (@max_label IS NULL) THEN SET NEW.agreement_number = CONCAT('CTR000001'); ELSE SET NEW.agreement_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end
drop trigger if exists before_insert_job;
create trigger before_insert_job before insert on job for each row begin IF (NEW.job_number IS NULL) THEN SELECT MAX(job_number) INTO @max_label FROM job; IF (@max_label IS NULL) THEN SET NEW.job_number = CONCAT('JOB000001'); ELSE SET NEW.job_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end

/* WORK SHIFTS */
INSERT INTO work_shift (id, name) VALUES (1,'Lunes y Miercoles Full');
INSERT INTO work_shift (id, name) VALUES (2,'Martes y Jueves Full');
INSERT INTO work_shift (id, name) VALUES (3,'Viernes y Domingo Full');
INSERT INTO work_shift (id, name) VALUES (4,'Sabado Full');

/* WORK SHIFT ITEMS de 6 a 6, horario 24hrs */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('MONDAY', '06:00:00', '23:59:59', 1); /* START - LUNES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('TUESDAY', '00:00:00', '05:59:59', 1); /* END - LUNES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('WEDNESDAY', '06:00:00', '23:59:59', 1); /* START - MIERCOLES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('THURSDAY', '00:00:00', '05:59:59', 1); /* END - MIERCOLES FULL DAY */

INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('TUESDAY', '06:00:00', '23:59:59', 2);/* START - MARTES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('WEDNESDAY', '00:00:00', '05:59:59', 2);/* END - MARTES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('THURSDAY', '06:00:00', '23:59:59', 2);/* START - JUEVES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('FRIDAY', '00:00:00', '05:59:59', 2);/* END - JUEVES FULL DAY */

INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('FRIDAY', '06:00:00', '23:59:59', 3); /* START - VIERNES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SATURDAY', '00:00:00', '05:59:59', 3); /* START - VIERNES FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SUNDAY', '06:00:00', '23:59:59', 3); /* START - DOMINGO FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('MONDAY', '00:00:00', '05:59:59', 3); /* START - DOMINGO FULL DAY */

INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SATURDAY', '06:00:00', '23:59:59', 4); /* START - SABADO FULL DAY */
INSERT INTO workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SUNDAY', '00:00:00', '05:59:59', 4); /* START - SABADO FULL DAY */

/* USERS */
INSERT INTO users (username, password, enabled) VALUES('avila', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);
INSERT INTO users (username, password, enabled) VALUES('rrojas', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'dvila');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'fvila');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'jevan');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'rcuadros');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'gevan');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'fserrato');

/* CONTACTS */
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'andresvrif@gmail.com', 1, 'andrés', '1990-08-23', 'vila', null, 'román', 'antony');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'rrojas@gmail.com', 1, 'renzo', '1990-04-10', 'rios', null, 'rojas', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'dvila@gmail.com', 1, 'deborah', '1986-02-12', 'vila', null, 'román', 'francesca');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'fvila@gmail.com', 1, 'frank', '1985-07-13', 'vila', null, 'román', 'robin');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'jevangelistac@gmail.com', 1, 'joselin', '1994-04-19', 'evangelista', null,'candiotti', 'fiorella');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'rcuadros@gmail.com', 1, 'ricardo', '1990-12-28', 'garcia', null, 'cuadros', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 1, 'gloria', '2000-09-28', 'evangelista', null, 'candiotti', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'fserrato@gmail.com', 1, 'fernando', '1989-09-05', 'serrato', null, 'montalvan', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'aura', '1924-12-30', 'llontop', null, 'enriquez', 'berthina');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'julvi', '1959-12-19', 'lira', null, 'sawaya', 'saida');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'graciela', '1938-11-24', 'garcia', null, 'garcia', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'celia', '1934-08-23', 'olaechea', null, 'garcia', 'vilma');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'bertha', '1942-11-10', 'domingues', null, 'de sotomayor', '');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name)VALUES ('PERSON', DEFAULT, null, DEFAULT, 'demetria', '1927-06-19', 'ñahuincopa', null, 'copa', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'jimmy', '1946-04-08', 'de lama', null, 'fowks', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name)VALUES ('PERSON', DEFAULT, null, DEFAULT, 'enrique', '1942-05-02', 'heredia', null, 'serrano', 'miguel');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name)VALUES ('PERSON', DEFAULT, null, DEFAULT, 'luis', '1929-06-21', 'gonzales', null, 'rivera', 'jesus');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'javier', '1930-06-13', 'ramos', null, 'garcia', 'antonio');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'lorenzo', '1942-03-17', 'sotomayor', null, 'von mack', 'jose');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo,second_last_name, second_name)VALUES ('PERSON', DEFAULT, null, DEFAULT, 'enrique', '1947-07-18', 'villalobos', null, 'farfan', 'eduardo');

/* Official ID */
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('29242147', 'DNI', 1, 9);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('07918010', 'DNI', 1, 10);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('06181637', 'DNI', 1, 11);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('21401947', 'DNI', 1, 12);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('10063132', 'DNI', 1, 13);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('09300621', 'DNI', 1, 14);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('02586289', 'DNI', 1, 15);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('06300288', 'DNI', 1, 16);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('09147773', 'DNI', 1, 17);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('08827599', 'DNI', 1, 18);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('10063165', 'DNI', 1, 19);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('41663523', 'DNI', 1, 20);

/* WORKER */
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (1, 1, 1);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (2, 2, 1);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (3, 3, 2);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (4, 4, 2);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (5, 5, 3);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (6, 6, 3);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (7, 7, 4);
INSERT INTO db_yoviro.worker (contact_id, user_id, work_shift_id) VALUES (8, 8, 4);

/* ROLES */
INSERT INTO db_yoviro.role (id, role_code) VALUES(1,'ROLE_ADMIN');
INSERT INTO db_yoviro.role (id, role_code) VALUES(2,'ROLE_USER');
INSERT INTO db_yoviro.role (id, role_code) VALUES(3,'ROLE_NURSE');

/* ROLES PARA USUARIOS */
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(1, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(2, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(3, 1);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(3, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(4, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(5, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(6, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(7, 3);
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(8, 3);

/* TEAMS */
INSERT INTO db_yoviro.team (team_type) VALUES('ADMINISTRATORS');
INSERT INTO db_yoviro.team (team_type) VALUES('NURSES');

/* TEAMS PARA USUARIOS */
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(1, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(3, 1);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(2, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(3, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(4, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(5, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(6, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(7, 2);
INSERT INTO db_yoviro.user_team (user_id, team_id) VALUES(8, 2);

