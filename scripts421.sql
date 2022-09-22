/* Шаг 1 */
ALTER TABLE student
    ADD CONSTRAINT age_const1 CHECK (age > 16),
    ADD CONSTRAINT name_const1 UNIQUE (name),
    ADD CONSTRAINT name_const2 CHECK (name IS NOT NULL),
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT pair_const1 UNIQUE (name, color);