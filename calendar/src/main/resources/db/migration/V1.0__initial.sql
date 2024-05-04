/*
 * Engine: PostgreSQL
 * Version: 1.0
 * Description: Create user and appointment tables
*/

CREATE TABLE IF NOT EXISTS public.user (
   id               uuid            not null,
   active           boolean         not null default true,
   created_at       timestamp       default current_timestamp,
   name             varchar(255),
   surname          varchar(255),
   email            varchar(255)    unique,
   primary key (id)
);

CREATE TABLE IF NOT EXISTS public.appointment (
   id               uuid            not null,
   active           boolean         not null default true,
   created_at       timestamp       default current_timestamp,
   title            varchar(255),
   author           uuid,
   description      varchar(255),
   startDateTime    timestamp,
   endDateTime      timestamp,
   primary key (id),
   foreign key (author)             references public.user(id)
);
