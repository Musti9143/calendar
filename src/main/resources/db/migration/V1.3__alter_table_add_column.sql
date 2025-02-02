/*
 * Engine: PostgreSQL
 * Version: 1.3
 * Description: Alter table appointment add column
    ALTER TABLE table_name
	ADD CONSTRAINT constraint_name constraint_definition;
*/

ALTER TABLE public.appointment
ADD COLUMN IF NOT EXISTS location JSONB;