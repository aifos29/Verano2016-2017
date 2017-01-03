USE [procedureDB]
GO
insert into [dbo].[department] (department, code)
values ('Recursos Humanos','HR');
insert into [dbo].[department] (department, code)
values ('Alcaldía','ALC');
insert into [dbo].[department] (department, code)
values ('Patentes','PAT');
	   
insert into [dbo].[typeOfProcedure] (TypeOfProcedure)
values ('Inquietud');
insert into [dbo].[typeOfProcedure] (TypeOfProcedure)
values('Solicitud');
	   
insert into [dbo].[typeOfIdentify] (TypeOfIdentify)
values ('Nacional');
insert into [dbo].[typeOfIdentify] (TypeOfIdentify)
values ('Extranjero');

insert into [dbo].[consecutive] (consecutiveNumber,consecutiveYear)
values (0,2017);

insert into [dbo].[status] (status)
values ('Nuevo');
insert into [dbo].[status] (status)
values ('Pendiente');
insert into [dbo].[status] (status)
values ('En proceso');
insert into [dbo].[status] (status)
values ('Terminado');
