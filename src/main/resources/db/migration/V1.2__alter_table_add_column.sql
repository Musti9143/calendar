/*
 * Engine: PostgreSQL
 * Version: 1.2
 * Description: Alter table user add column
    ALTER TABLE table_name
	ADD CONSTRAINT constraint_name constraint_definition;
*/

ALTER TABLE public.user
ADD COLUMN hashed_password char(60) not null;