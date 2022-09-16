-- liquibase formatted sql

-- changeset MaxV:1
DROP INDEX name_const1;
CREATE INDEX student_name_idx ON student (name);

-- changeset MaxV:2
CREATE INDEX faculty_pair_name_color_idx ON faculty (name, color);