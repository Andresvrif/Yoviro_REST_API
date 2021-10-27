/* TRIGGERS */
drop trigger if exists before_insert_contractor;
create trigger before_insert_contractor before insert on contractor for each row begin IF (NEW.contractor_number IS NULL) THEN SELECT MAX(contractor_number) INTO @max_label FROM contractor;IF (@max_label IS NULL) THEN SET NEW.contractor_number = CONCAT('CTA000001');ELSE SET NEW.contractor_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0'));END IF;END IF;end
drop trigger if exists before_insert_contrato;
create trigger before_insert_contrato before insert on agreement for each row begin IF (NEW.agreement_number IS NULL) THEN SELECT MAX(agreement_number) INTO @max_label FROM agreement; IF (@max_label IS NULL) THEN SET NEW.agreement_number = CONCAT('CTR000001'); ELSE SET NEW.agreement_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end
drop trigger if exists before_insert_job;
create trigger before_insert_job before insert on job for each row begin IF (NEW.job_number IS NULL) THEN SELECT MAX(job_number) INTO @max_label FROM job; IF (@max_label IS NULL) THEN SET NEW.job_number = CONCAT('JOB000001'); ELSE SET NEW.job_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 6, '0')); END IF; END IF; end
drop trigger if exists before_insert_inventory_request;
create trigger before_insert_inventory_request before insert on inventory_request for each row begin IF (NEW.inventory_request_number IS NULL) THEN SELECT MAX(inventory_request_number) INTO @max_label FROM inventory_request; IF (@max_label IS NULL) THEN SET NEW.inventory_request_number = CONCAT('SOLIC0000001'); ELSE SET NEW.inventory_request_number = CONCAT(SUBSTR(@max_label, 1, 5), LPAD(SUBSTR(@max_label, 6) + 1, 7, '0')); END IF; END IF; end
drop trigger if exists before_insert_proposal;
create trigger before_insert_proposal before insert on proposal for each row begin IF (NEW.proposal_number IS NULL) THEN SELECT MAX(proposal_number) INTO @max_label FROM proposal; IF (@max_label IS NULL) THEN SET NEW.proposal_number = CONCAT('PROPO0000001'); ELSE SET NEW.proposal_number = CONCAT(SUBSTR(@max_label, 1, 5), LPAD(SUBSTR(@max_label, 6) + 1, 7, '0')); END IF; END IF; end
drop trigger if exists before_insert_product;
create trigger before_insert_product before insert on product for each row begin IF (NEW.sku IS NULL) THEN SELECT MAX(sku) INTO @max_label FROM product; IF (@max_label IS NULL) THEN SET NEW.sku = CONCAT('SKU0000001'); ELSE SET NEW.sku = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 7, '0')); END IF; END IF; end
drop trigger if exists before_insert_purchase_order;
create trigger before_insert_purchase_order before insert on purchase_order for each row begin IF (NEW.purchase_order_number IS NULL) THEN SELECT MAX(purchase_order_number) INTO @max_label FROM purchase_order; IF (@max_label IS NULL) THEN SET NEW.purchase_order_number = CONCAT('PUO0000001'); ELSE SET NEW.purchase_order_number = CONCAT(SUBSTR(@max_label, 1, 3), LPAD(SUBSTR(@max_label, 4) + 1, 7, '0')); END IF; END IF; end

/* WORK SHIFTS */
INSERT INTO db_yoviro.work_shift (id, name) VALUES (1,'Lunes y Miercoles Full');
INSERT INTO db_yoviro.work_shift (id, name) VALUES (2,'Martes y Jueves Full');
INSERT INTO db_yoviro.work_shift (id, name) VALUES (3,'Viernes y Domingo Full');
INSERT INTO db_yoviro.work_shift (id, name) VALUES (4,'Sabado Full');
INSERT INTO db_yoviro.work_shift (id, name) VALUES (5, 'Lunes a viernes de 9am a 5pm');


/* WORK SHIFT ITEMS de 6 a 6, horario 24hrs */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('MONDAY', '06:00:00', '23:59:59', 1); /* START - LUNES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('TUESDAY', '00:00:00', '05:59:59', 1); /* END - LUNES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('WEDNESDAY', '06:00:00', '23:59:59', 1); /* START - MIERCOLES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('THURSDAY', '00:00:00', '05:59:59', 1); /* END - MIERCOLES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('TUESDAY', '06:00:00', '23:59:59', 2);/* START - MARTES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('WEDNESDAY', '00:00:00', '05:59:59', 2);/* END - MARTES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('THURSDAY', '06:00:00', '23:59:59', 2);/* START - JUEVES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('FRIDAY', '00:00:00', '05:59:59', 2);/* END - JUEVES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('FRIDAY', '06:00:00', '23:59:59', 3); /* START - VIERNES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SATURDAY', '00:00:00', '05:59:59', 3); /* START - VIERNES FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SUNDAY', '06:00:00', '23:59:59', 3); /* START - DOMINGO FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('MONDAY', '00:00:00', '05:59:59', 3); /* START - DOMINGO FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SATURDAY', '06:00:00', '23:59:59', 4); /* START - SABADO FULL DAY */
INSERT INTO db_yoviro.workshift_item (day_of_week, start_time, end_time, work_shift_id) VALUES ('SUNDAY', '00:00:00', '05:59:59', 4); /* START - SABADO FULL DAY */

/*  WORK SHIFT ITEMS de L a V, horario 8 hrs de 9 a 17 */
INSERT INTO db_yoviro.workshift_item (day_of_week, end_time, start_time, work_shift_id) VALUES ('MONDAY', '09:00:00', '17:00:00', 5);
INSERT INTO db_yoviro.workshift_item (day_of_week, end_time, start_time, work_shift_id) VALUES ('TUESDAY', '09:00:00', '17:00:00', 5);
INSERT INTO db_yoviro.workshift_item (day_of_week, end_time, start_time, work_shift_id) VALUES ('WEDNESDAY', '09:00:00', '17:00:00', 5);
INSERT INTO db_yoviro.workshift_item (day_of_week, end_time, start_time, work_shift_id) VALUES ('THURSDAY', '09:00:00', '17:00:00', 5);
INSERT INTO db_yoviro.workshift_item (day_of_week, end_time, start_time, work_shift_id) VALUES ('FRIDAY', '09:00:00', '17:00:00', 5);

/* USERS */
INSERT INTO db_yoviro.users (username, password, enabled) VALUES('avila', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);
INSERT INTO db_yoviro.users (username, password, enabled) VALUES('rrojas', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'dvila');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'fvila');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'jevan');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'rcuadros');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'gevan');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'fserrato');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'ghinojoza');
INSERT INTO db_yoviro.users (enabled, password, username) VALUES (true, '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 'system');

/* CONTACTS */
/* CONTACTS - WORKERS */
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'andresvrif@gmail.com', 1, 'andrés', '1990-08-23', 'vila', null, 'román', 'antony');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'rrojas@gmail.com', 1, 'renzo', '1990-04-10', 'rios', null, 'rojas', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'dvila@gmail.com', 1, 'deborah', '1986-02-12', 'vila', null, 'román', 'francesca');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'fvila@gmail.com', 1, 'frank', '1985-07-13', 'vila', null, 'román', 'robin');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'jevangelistac@gmail.com', 1, 'joselin', '1994-04-19', 'evangelista', null,'candiotti', 'fiorella');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'rcuadros@gmail.com', 1, 'ricardo', '1990-12-28', 'garcia', null, 'cuadros', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 1, 'gloria', '2000-09-28', 'evangelista', null, 'candiotti', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'fserrato@gmail.com', 1, 'fernando', '1989-09-05', 'serrato', null, 'montalvan', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, 'ghinojoza@gmail.com', 1, 'gonzalo', '1989-09-05', 'hinojoza', null, 'santillan', null);

/* CONTACTS - RESIDENT */
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'aura', '1924-12-30', 'llontop', null, 'enriquez', 'berthina');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'julvi', '1959-12-19', 'lira', null, 'sawaya', 'saida');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'graciela', '1938-11-24', 'garcia', null, 'garcia', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, 0, 'celia', '1934-08-23', 'olaechea', null, 'garcia', 'vilma');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'bertha', '1942-11-10', 'domingues', null, 'de sotomayor', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'demetria', '1927-06-19', 'ñahuincopa', null, 'copa', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'jimmy', '1946-04-08', 'de lama', null, 'fowks', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'enrique', '1942-05-02', 'heredia', null, 'serrano', 'miguel');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'luis', '1929-06-21', 'gonzales', null, 'rivera', 'jesus');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'javier', '1930-06-13', 'ramos', null, 'garcia', 'antonio');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'lorenzo', '1942-03-17', 'sotomayor', null, 'von mack', 'jose');
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, birth_date, last_name, photo,second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'enrique', '1947-07-18', 'villalobos', null, 'farfan', 'eduardo');

/* CONTACTS - CONTRACTORS */
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'maria', null, '1981-05-25', 'llontop', null, 'vargas', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'liz', null, '1986-07-01', 'lira', null, 'cuadros', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'jackelin', null, '1990-08-23', 'garcia', null, 'llevara', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'stephany', null, '1984-04-19', 'olaechea', null, 'mori', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'robert', null, '1996-09-10', 'domingues', null, 'peron', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'christian', null, '1988-01-23', 'vidonni', null, 'ñahuincopa', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'pedro', null, '1987-05-31', 'bellido', null, 'domingues', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'pablo', null, '1989-02-21', 'mondragon', null, 'de lama', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'paula', null, '1987-03-04', 'requena', null, 'heredia', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'jesus', null, '1990-01-05', 'gonzales', null, 'gonzales', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'carlos', null, '1993-04-15', 'ramos', null, 'guevara', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'esteban', null, '1990-02-28', 'sotomayor', null, 'rojas', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo, second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'luis', null, '1990-01-01', 'villalobos', null, 'leonord', null);
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date, birth_date, last_name, photo,second_last_name, second_name) VALUES ('PERSON', DEFAULT, null, DEFAULT, 'ronald', null, '1987-10-15', 'rojas', null, 'calderon', null);

/* CONTACTS - COMPANY */
INSERT INTO db_yoviro.contact (contact_type, create_at, email, internal, name, start_date) VALUES ('COMPANY', DEFAULT, 'contacto@universal.com', 1, 'FARMACIA UNIVERSAL S.A.C.', '1961-10-01');

/* Official ID */
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('70007861', 'DNI', 1, 1);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('48813667', 'DNI', 1, 2);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('98878497', 'DNI', 1, 3);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('60087417', 'DNI', 1, 4);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('50000741', 'DNI', 1, 5);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('98740087', 'DNI', 1, 6);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('66008877', 'DNI', 1, 7);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('04842147', 'DNI', 1, 8);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('04333147', 'DNI', 1, 9);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('29242147', 'DNI', 1, 10);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('07918010', 'DNI', 1, 11);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('06181637', 'DNI', 1, 12);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('21401947', 'DNI', 1, 13);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('10063132', 'DNI', 1, 14);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('09300621', 'DNI', 1, 15);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('02586289', 'DNI', 1, 16);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('06300288', 'DNI', 1, 17);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('09147773', 'DNI', 1, 18);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('08827599', 'DNI', 1, 19);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('10063165', 'DNI', 1, 20);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('41663523', 'DNI', 1, 21);

/* Official ID - FOR CONTRACTOR */
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('98475645', 'DNI', 1, 21);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('98415270', 'DNI', 1, 22);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('54987123', 'DNI', 1, 23);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('66778187', 'DNI', 1, 24);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('87846932', 'DNI', 1, 25);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('06581621', 'DNI', 1, 26);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('48784889', 'DNI', 1, 27);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('15498288', 'DNI', 1, 28);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('78615873', 'DNI', 1, 29);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('87462599', 'DNI', 1, 30);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('87615485', 'DNI', 1, 31);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('68742523', 'DNI', 1, 32);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('59800771', 'DNI', 1, 33);
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('60000620', 'DNI', 1, 34);

/* Official ID - FOR COMPANIES */
INSERT INTO db_yoviro.official_id (official_id_number, official_id_type, primary_official_id, contact_id) VALUES ('20100025168', 'RUC', 1, 36);

/* WORKER */
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 1, 1, 1);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 2, 2, 1);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('DIRECTOR', 3, 3, 2);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 4, 4, 2);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 5, 5, 3);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 6, 6, 3);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 7, 7, 4);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('NURSE', 8, 8, 4);
INSERT INTO db_yoviro.worker (worker_type, contact_id, user_id, work_shift_id) VALUES ('STOREKEEPER', 9, 9, 1);

/* RELATON BETWEEN CONTRACTOR AND CONTACT */
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 21);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 22);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 23);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 24);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 25);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 26);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 27);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 28);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 29);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 30);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 31);
INSERT INTO db_yoviro.contractor (contractor_number, create_at, contact_id) VALUES (null, DEFAULT, 32);

/* RELATION BETWEEN RESIDENT AND CONTACT */
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 10);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 11);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 12);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 13);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 14);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 15);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 16);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 17);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 18);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 19);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 20);
INSERT INTO db_yoviro.resident (create_at, enable, contact_id) VALUES (DEFAULT, true, 21);

/* ROLES */
INSERT INTO db_yoviro.role (id, role_code) VALUES(1,'ROLE_ADMIN');
INSERT INTO db_yoviro.role (id, role_code) VALUES(2,'ROLE_USER');
INSERT INTO db_yoviro.role (id, role_code) VALUES(3,'ROLE_NURSE');
INSERT INTO db_yoviro.role (id, role_code) VALUES(4,'ROLE_STORE_KEEPER');
INSERT INTO db_yoviro.role (id, role_code) VALUES(5,'SYSTEM');

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
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(9, 4); -- STORE KEEPER ROLE
INSERT INTO db_yoviro.user_role (user_id, role_id) VALUES(10, 5); -- SYSTEM ADMIN ROLE

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

/* AGREEMENTS */
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 1);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 2);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 3);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 4);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 5);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 6);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 7);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 8);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 9);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 10);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 11);
INSERT INTO db_yoviro.agreement (agreement_number, create_at, contractor_id) VALUES (null, DEFAULT, 12);

/* JOB - SUBMISSION */
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 1, 1);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 2, 2);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 3, 3);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 4, 4);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 5, 5);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 6, 6);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 7, 7);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 8, 8);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 9, 9);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 10, 10);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 11, 11);
INSERT INTO db_yoviro.job (job_type, create_at, effective_date, end_date, job_number, start_date, agreement_id, resident_id) VALUES ('SUBMISSION', DEFAULT, '2020-10-08 12:51:03.000000', null, null, '2020-10-08 12:51:03.000000', 12, 12);

/* ACTIVITY PATTERNS */
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Desayuno Normal', 'Desayuno sin restricciones', '2020-07-05', null, '07:30:00', 'desayuno_normal', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Desayuno celiaco', 'Desayuno con arina arina celiaca', '2020-07-05', null, '07:30:00', 'desayuno_celiaco', 0, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 2, 'Cuidado de hongos', 'Aplicar fungisil en crema', '2020-07-25', null, '07:00:00', 'crema_hongos_mañana', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 2, 'Cuidado de hongos', 'Aplicar fungisil en crema', '2020-07-25', null, '19:00:00', 'crema_hongos_tarde', 0, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 4, 'Paseo al parque', 'Paseo al parque quintana', '2020-07-20', null, '12:00:00', 'paseo_parque', 0, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Lonche', 'Lonche sin restricciones', '2020-07-05', null, '19:20:00', 'lonche_normal', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '07:00:00', 'inyeccion_insulina_mañana', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '15:00:00', 'inyeccion_insulina_tarde', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 2, 'Inyección de insulina', 'Aplicar 50 ml de insulina', '2020-07-20', null, '23:00:00', 'inyeccion_insulina_noche', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Almuerzo', 'Almuerzo normal', '2020-07-20', null, '15:00:00', 'almuerzo', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Almuerzo celiaco', 'Almuerzo celiaco, no harinas', '2020-07-20', null, '23:00:00', 'almuerzo_celiaco', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Aseo PAM', 'Aseo y limpieza del PAM (ducha, cepillarse los dientes, vestirlo, etc...)', '2020-07-20', null, '06:00:00', 'aseo_mañana', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Cambio de pañal en la mañana', 'Cambio de pañal en la mañana', '2020-07-20', null, '06:30:00', 'cambio_pañal_1', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Cambio de pañal en la mañana', 'Cambio de pañal en la mañana', '2020-07-20', null, '10:30:00', 'cambio_pañal_2', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Balance hidrico', 'Registro de balance hidrico para pacientes NO postrados', '2020-07-20', null, '07:00:00', 'balance_hidrico_mañana', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Balance hidrico postrado', 'Registro de balance hidrico para pacientes postrados', '2020-07-20', null, '08:00:00', 'balance_hidrico_mañana_postrados', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Limpieza habitación', 'Limpieza de habitación', '2020-07-20', null, '08:20:00', 'limpieza_habitación', 1, '#089bab');
INSERT INTO db_yoviro.activity_pattern (create_at, day_frequency, subject, description, start_date, end_date, hour_frequency, pattern_code, enable, color_code) VALUES (now(), 1, 'Medicación', 'Medicación', '2020-07-20', null, '09:00:00', 'medicación', 1, '#089bab');

/* ACTIVITY PATTERNS IN AGREEMENTS */
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 1);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 1);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 1);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 1);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 1);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 2);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 2);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 2);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 2);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 2);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 3);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 3);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 3);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 3);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 3);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 4);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 4);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 4);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 4);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 4);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 5);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 5);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 5);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 5);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 5);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 6);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 6);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 6);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 6);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 6);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 7);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 7);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 7);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 7);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 7);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 8);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 8);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 8);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 8);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 8);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 9);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 9);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 9);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 9);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 9);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 10);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 10);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 10);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 10);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 10);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 11);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 11);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 11);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 11);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 11);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (1, 12);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (6, 12);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (10, 12);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (12, 12);
INSERT INTO db_yoviro.activity_pattern_agreement (activity_pattern_id, agreement_id) VALUES (17, 12);

/* INVENTORY */
INSERT INTO db_yoviro.product (unit_measure, category, create_at, description, name) VALUES ('UNIT', 'PERSONAL_CARE', now() - INTERVAL 3 MONTH, 'marca : practipañal, modelo : plenitud,talla : XL', 'practipañal plenitud XL');
INSERT INTO db_yoviro.product (unit_measure, category, create_at, description, name) VALUES ('UNIT', 'PERSONAL_CARE', now() - INTERVAL 2 MONTH,'marca : practipañal,  modelo : plenitud fenme, talla : XL', 'practipañal plenitud fenme XL');
INSERT INTO db_yoviro.product (unit_measure, category, create_at, description, name) VALUES ('UNIT', 'PERSONAL_CARE', now() - INTERVAL 1 MONTH,'marca : secos, modelo : fenme,talla : M', 'practipañal secos G');
INSERT INTO db_yoviro.product (unit_measure, category, create_at, description, name) VALUES ('UNIT', 'PERSONAL_CARE', now() - INTERVAL 9 MONTH,'marca : colgate, pasta dental', 'pasta dental grande colgate');

/* REQUEST INVENTORY */
INSERT INTO db_yoviro.inventory_request (create_at, status, resident_id, worker_id) VALUES (now() - INTERVAL 1 DAY, 'IN_PROGRESS', 1, 7);
INSERT INTO db_yoviro.inventory_request (create_at, status, resident_id, worker_id) VALUES (now() - INTERVAL 2 DAY, 'IN_PROGRESS', 3, 7);
INSERT INTO db_yoviro.inventory_request (create_at, status, resident_id, worker_id) VALUES (now(), 'PENDING', 2, 8);
    /*IN PROGRESS WITH A PROPOSAL APPROVED */
INSERT INTO db_yoviro.inventory_request (create_at, status, resident_id, worker_id) VALUES (now() - INTERVAL 3 DAY, 'IN_PROGRESS', 1, 7);
INSERT INTO db_yoviro.inventory_request (create_at, status, resident_id, worker_id) VALUES (now(), 'IN_PROGRESS', 3, 7);


/* REQUEST INVENTORY DETAIL */
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (21, 1, 1);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (20, 2, 2);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (20, 3, 4);

INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (10, 4, 1);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (20, 4, 2);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (30, 4, 3);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (40, 4, 4);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (10, 5, 1);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (20, 5, 2);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (30, 5, 3);
INSERT INTO db_yoviro.inventory_request_detail (quantity, inventory_request_id, product_id) VALUES (40, 5, 4);

/* PROPOSAL */
INSERT INTO db_yoviro.proposal (create_at, update_at, reason_for_denied, status, store_keeper_id, director_id) VALUES (now(),now(), null, 'PENDING', 9, null);
INSERT INTO db_yoviro.proposal (create_at, update_at, reason_for_denied, status, store_keeper_id, director_id) VALUES (now(),now(), null, 'APPROVED', 9, 3);

/* REQUEST INVENTORY WITH ASSSIGNED PROPOSAL */
INSERT INTO db_yoviro.inventory_request_proposal (proposal_id, inventory_request_id) VALUES (1, 1);
INSERT INTO db_yoviro.inventory_request_proposal (proposal_id, inventory_request_id) VALUES (1, 2);
INSERT INTO db_yoviro.inventory_request_proposal (proposal_id, inventory_request_id) VALUES (2, 4);
INSERT INTO db_yoviro.inventory_request_proposal (proposal_id, inventory_request_id) VALUES (2, 5);

/* PURCHASE ORDER */
INSERT INTO db_yoviro.purchase_order (attachment_document, create_at, status, total_price, worker_id, company_id)VALUES (null, now(), 'QUOTED', 50.22, 7, 36);
INSERT INTO db_yoviro.purchase_order (attachment_document, create_at, status, total_price, worker_id, company_id)VALUES (null, now(), 'QUOTED', 75.32, 7, 36);

/* PURCHASE ORDER DETAIL */
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 10, 1, 1);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 20, 1, 2);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 30, 1, 3);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 40, 1, 4);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 10, 2, 1);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 20, 2, 2);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 30, 2, 3);
INSERT INTO db_yoviro.purchase_order_detail (create_at, quantity, purchase_order_id, product_id) VALUES (now(), 40, 2, 4);

/* PURCHASE ORDER WITH PROPOSAL ASSIGNED */
INSERT INTO db_yoviro.purchase_order_proposal (proposal_id, purchase_order_id) VALUES (2, 1);
INSERT INTO db_yoviro.purchase_order_proposal (proposal_id, purchase_order_id) VALUES (2, 2);