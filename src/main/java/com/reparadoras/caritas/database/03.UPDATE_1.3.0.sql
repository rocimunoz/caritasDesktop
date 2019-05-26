-- familia 
ALTER TABLE caritas.c_family_type modify COLUMN description varchar(255);
update caritas.c_family_type set description = 'Unipersonal' where id = 1;
update caritas.c_family_type set description = 'Otro tipo de parentesco' where id = 5;
insert into caritas.c_family_type(id, description) values(6, 'Personas sin relación de parentesco');
insert into caritas.c_family_type(id, description) values(7, 'Sin hogar');


-- estudios
update caritas.c_studies set description = 'Sin alfabetizar' where id = 1;
update caritas.c_studies set description = 'Lee y escribe' where id = 2;
update caritas.c_studies set description = 'ESO -FP Básico' where id = 5;

-- situacion laboral
ALTER TABLE caritas.c_job_situation modify COLUMN description varchar(255);

update caritas.c_job_situation set description= 'Desempleado (en búsqueda de empleo)' where id = 1;
update caritas.c_job_situation set description = 'Trabaja por cuenta ajena con contrato' where id = 2;
update caritas.c_job_situation set description = 'Trabaja por cuenta ajena sin contrato' where id = 3;
update caritas.c_job_situation set description = 'Labores del hogar' where id = 4;
update caritas.c_job_situation set description = 'Jubilado y pensionista, incapacidad laboral y permanente' where id = 5;
update caritas.c_job_situation set description = 'Estudiando (con 16 años y más sin trabajo)' where id = 6;

insert into caritas.c_job_situation (id, description) values(7, 'Persona que no está buscando trabajo');
insert into caritas.c_job_situation (id, description) values(8, 'Trabaja por cuenta propia con alta SS.SS');
insert into caritas.c_job_situation (id, description) values(9, 'Trabaja por cuenta ajena sin alta SS.SS');
insert into caritas.c_job_situation (id, description) values(10, 'Sin edad laboral (menor de 16 años)');

-- vivienda
ALTER TABLE caritas.c_home modify COLUMN reg_holding varchar(200);

ALTER TABLE caritas.c_home_type modify column description varchar(200);
update caritas.c_home_type set description = 'Vivienda individual: piso, vivienda unifamiliar' where id = 1;
insert into caritas.c_home_type (id, description) values(5, 'Sin alojamiento (Albergue, vivir en espacio público');
insert into caritas.c_home_type (id, description) values(6, 'Sin vivienda (Hogares P.S.H., Centros Inmigrantes)');
insert into caritas.c_home_type (id, description) values(7, 'Infravivienda (Chabola)');
insert into caritas.c_home_type (id, description) values(8, 'Alojamiento Colectivo (Habitación, Residencia P.Mayores)');
insert into caritas.c_home_type (id, description) values(9, 'Residir instituciones penitenciarias, sanitarias ...)');
insert into caritas.c_home_type (id, description) values(10, 'Desconocido)');

-- tipo autorizacion
update caritas.c_authorization_type set description = 'Residencia temporal' where id = 1;
update caritas.c_authorization_type set description = 'Residencia y trabajo temporal' where id = 2;
update caritas.c_authorization_type set description = 'Turista' where id = 4;

update caritas.c_authorization_type set description = 'Sin documentar' where id = 6;
update caritas.c_authorization_type set description = 'P.Situación administrativa irregular' where id = 7;

insert into caritas.c_authorization_type(id, description) values(8, 'Residencia permanente');
insert into caritas.c_authorization_type(id, description) values(9, 'Trabajo');
insert into caritas.c_authorization_type(id, description) values(10, 'Español');
insert into caritas.c_authorization_type(id, description) values(11, 'Comunitario (UE) NO español');
insert into caritas.c_authorization_type(id, description) values(12, 'Familiares de comunitarios');


-- RELATIVES
ALTER TABLE caritas.c_relative add column LIVE_WORK varchar(100);
