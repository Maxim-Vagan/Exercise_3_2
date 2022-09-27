/* Шаг 2 */
CREATE TABLE person (
	id BIGSERIAL NOT NULL,
	name VARCHAR(50) NOT NULL DEFAULT 'Default Name',
	age INTEGER NOT NULL DEFAULT 1,
	has_driver_license BOOLEAN DEFAULT 0,
	car_id BIGSERIAL,
	CONSTRAINT person_id_pkey PRIMARY KEY (id),
	CONSTRAINT car_id_fkey FOREIGN KEY (car_id)
		REFERENCES car (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE car (
	id BIGSERIAL NOT NULL,
	vendor_name VARCHAR(255) NOT NULL DEFAULT 'Default Vendor',
	model_name VARCHAR(255) NOT NULL DEFAULT 'Default Model',
	price NUMERIC(12, 2) NOT NULL DEFAULT 0,
	CONSTRAINT car_id_pkey PRIMARY KEY (id)
);
/* Шаг 3 */
SELECT st.name,
	st.age,
	flt.name as faculty
FROM student st
	LEFT JOIN faculty flt ON st.faculty_id = flt.facultyid;

SELECT st.*
FROM student st
	INNER JOIN avatar avt ON st.studentid = avt.student_studentid;