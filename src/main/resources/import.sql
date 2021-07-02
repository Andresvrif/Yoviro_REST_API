/* Creamos algunos usuarios */
INSERT INTO users (username, password, enabled) VALUES('andres', '$2a$10$debpqzhpXFd4O/Lx3kAhX.KeOqhesTfrJMStixsYSqcQFvIXicHbC', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$X.1R6428OlgxcWCEpX2SSuFKMIBPpQ/EFG/e3GTZm57BWvzZEzdAO', 1);

/* Creamos algunos roles para los usuarios */
INSERT INTO authorities (user_id, authority) VALUES(1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2,'ROLE_ADMIN');