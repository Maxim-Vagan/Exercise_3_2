/* Шаг 1 */
CREATE TABLE student(
	studentid BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) UNIQUE NOT NULL,
	age INTEGER CHECK (age > 16) DEFAULT 20,
	faculty_id INTEGER,
	CONSTRAINT faculty_id_fkey FOREIGN KEY (faculty_id)
		REFERENCES faculty (facultyid) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE faculty(
	facultyid BIGSERIAL PRIMARY KEY,
	name VARCHAR(255),
	color VARCHAR(255),
	CONSTRAINT pair_const1 UNIQUE (name, color)
);