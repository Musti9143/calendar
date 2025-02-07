/*
 * Engine: PostgreSQL
 * Version: 1.2
 * Description: Alter table user add column
    ALTER TABLE table_name
	ADD CONSTRAINT constraint_name constraint_definition;
*/

ALTER TABLE public.user
ADD COLUMN IF NOT EXISTS password varchar(255);