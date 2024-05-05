/*
 * Engine: PostgreSQL
 * Version: 1.1
 * Description: Delete user on cascade
    ALTER TABLE table_name
	ADD CONSTRAINT constraint_name constraint_definition;
*/

ALTER TABLE public.appointment
DROP CONSTRAINT IF EXISTS appointment_author_fkey;

ALTER TABLE public.appointment
ADD CONSTRAINT appointment_author_fkey FOREIGN KEY (author) REFERENCES public.user(id) ON DELETE CASCADE;
