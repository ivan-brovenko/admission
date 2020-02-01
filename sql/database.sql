CREATE TABLE admission.user
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  role ENUM('admin', 'entrant', 'reg_entrant', 'admis', 'blocked') NOT NULL,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  patronymic VARCHAR(30) NOT NULL,
  city VARCHAR(30) NOT NULL,
  region VARCHAR(30) NOT NULL,
  school VARCHAR(30) NOT NULL
);
CREATE UNIQUE INDEX user_email_uindex ON user (email);

CREATE TABLE admission.atestat
(
  atestat_number INT PRIMARY KEY NOT NULL,
  average_mark INT DEFAULT 0 NOT NULL,
  entrant_id INT NOT NULL,
  CONSTRAINT atestat_user_id_fk FOREIGN KEY (entrant_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE admission.department
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(30) NOT NULL,
  places INT NOT NULL,
  free_places INT NOT NULL
);
CREATE UNIQUE INDEX department_name_uindex ON department (name);

CREATE TABLE admission.univer
(
  id INT DEFAULT 0 PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  city VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX univer_name_uindex ON univer (name);

CREATE TABLE admission.subject
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(30) NOT NULL
);
CREATE UNIQUE INDEX subject_name_uindex ON subject (name);

CREATE TABLE admission.department_entrant
(
  id INT PRIMARY KEY NOT NULL,
  entrant_id INT NOT NULL,
  priority INT NOT NULL,
  univer_id INT NOT NULL,
  department_id INT NOT NULL,
  CONSTRAINT department_entrant_user_id_fk FOREIGN KEY (entrant_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT department_entrant_department_id_fk FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT department_entrant_univer_id_fk FOREIGN KEY (univer_id) REFERENCES univer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE admission.marks
(
  subject_id INT NOT NULL,
  mark INT NOT NULL,
  atestat_id INT,
  CONSTRAINT marks_atestat_atestat_number_fk FOREIGN KEY (atestat_id) REFERENCES atestat (atestat_number) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT marks_subject_id_fk FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE admission.zno_results
(
  entrant_id INT NOT NULL,
  subject_id INT NOT NULL,
  mark INT NOT NULL,
  CONSTRAINT zno_results_user_id_fk FOREIGN KEY (entrant_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT zno_results_subject_id_fk FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE admission.final_mark
(
  entrant_id INT NOT NULL,
  final_avg_mark INT NOT NULL,
  CONSTRAINT final_mark_user_id_fk FOREIGN KEY (entrant_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE admission.final_admission
(
  entrant_id INT NOT NULL,
  department_id INT NOT NULL,
  price ENUM('f', 'nf') NOT NULL,
  CONSTRAINT final_admission_user_id_fk FOREIGN KEY (entrant_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT final_admission_department_id_fk FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO admission.subject (id, name) VALUES (11, 'algebra');
INSERT INTO admission.subject (id, name) VALUES (5, 'english');
INSERT INTO admission.subject (id, name) VALUES (9, 'fiz_ra');
INSERT INTO admission.subject (id, name) VALUES (7, 'geometry');
INSERT INTO admission.subject (id, name) VALUES (8, 'history');
INSERT INTO admission.subject (id, name) VALUES (6, 'inform');
INSERT INTO admission.subject (id, name) VALUES (10, 'physics');
INSERT INTO admission.subject (id, name) VALUES (3, 'russian');
INSERT INTO admission.subject (id, name) VALUES (4, 'rus_lit');
INSERT INTO admission.subject (id, name) VALUES (1, 'ukrainian');
INSERT INTO admission.subject (id, name) VALUES (2, 'ukr_lit');

INSERT INTO admission.univer (id, name, city) VALUES (3, 'karazina national university','kharkir');
INSERT INTO admission.univer (id, name, city) VALUES (2, 'kpi','kier');
INSERT INTO admission.univer (id, name, city) VALUES (4, 'med','lviv');
INSERT INTO admission.univer (id, name, city) VALUES (1, 'nure', 'kharkiv');

INSERT INTO admission.department (id, name, places, free_places) VALUES (1, 'KN', 100, 10);
INSERT INTO admission.department (id, name, places, free_places) VALUES (2, 'PI', 100, 10);
INSERT INTO admission.department (id, name, places, free_places) VALUES (3, 'PM', 100, 10);
INSERT INTO admission.department (id, name, places, free_places) VALUES (4, 'AKT', 100, 10);
INSERT INTO admission.department (id, name, places, free_places) VALUES (8, '����������', 100, 10);

INSERT INTO admission.user (email, password, role, first_name, last_name, patronymic, city, region, school) VALUES ('admin@gmail.com', '74a9f45bf5b8103978a633e00530e565', 'admin', 'admin', 'admin', 'admin', 'admincity', 'adminregino', 'adminschool');