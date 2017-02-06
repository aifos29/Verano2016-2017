USE [procedureDB]
GO
insert into [dbo].[department] (department, code)
values ('Administraci�n Tributaria','AT');

insert into [dbo].[department] (department, code)
values ('Administraci�n General','ADM GEN');

insert into [dbo].[department] (department, code)
values ('Alcald�a','ALC');

insert into [dbo].[department] (department, code)
values ('Vice Alcald�a','VICE-ALC');

insert into [dbo].[department] (department, code)
values ('Asesor�a Legal','ASES-LEG');

insert into [dbo].[department] (department, code)
values ('Auditor�a','AUD');

insert into [dbo].[department] (department, code)
values ('Bienes Inmuebles','BI');

insert into [dbo].[department] (department, code)
values ('Catastro','CATA');

insert into [dbo].[department] (department, code)
values ('Cementerio','CEM');

insert into [dbo].[department] (department, code)
values ('Concejo','CONCEJO');

insert into [dbo].[department] (department, code)
values ('Contabilidad','CONTA');

insert into [dbo].[department] (department, code)
values ('Gesti�n Ambiental','G AMB');

insert into [dbo].[department] (department, code)
values ('Gesti�n Cultural','G CULT');

insert into [dbo].[department] (department, code)
values ('Inform�tica','INF');

insert into [dbo].[department] (department, code)
values ('Ingenier�a','ING');

insert into [dbo].[department] (department, code)
values ('Inspecci�n','INSP');

insert into [dbo].[department] (department, code)
values ('Junta Vial','JV');

insert into [dbo].[department] (department, code)
values ('Oficina de la Mujer','OFIC MUJ');

insert into [dbo].[department] (department, code)
values ('Patentes','PAT');

insert into [dbo].[department] (department, code)
values ('Planificaci�n','PLANI');

insert into [dbo].[department] (department, code)
values ('Proveedur�a','PROVEE');

insert into [dbo].[department] (department, code)
values ('Recursos Humanos','RH');

insert into [dbo].[department] (department, code)
values ('Tesorer�a','TESO');

insert into [dbo].[department] (department, code)
values ('Unidad T�cnica de Gesti�n Vial','UTGV');

insert into [dbo].[department] (department, code)
values ('Plataforma','PLAT');
	   
insert into [dbo].[typeOfProcedure] (TypeOfProcedure)
values ('Inquietud');
insert into [dbo].[typeOfProcedure] (TypeOfProcedure)
values('Solicitud');
	   
insert into [dbo].[typeOfIdentify] (TypeOfIdentify)
values ('Nacional');
insert into [dbo].[typeOfIdentify] (TypeOfIdentify)
values ('Extranjero');
insert into [dbo].[typeOfIdentify] (TypeOfIdentify)
values ('Pasaporte');

insert into [dbo].[consecutive] (consecutiveNumber,consecutiveYear)
values (0,2017);

insert into [dbo].[status] (status)
values ('Nuevo');
insert into [dbo].[status] (status)
values ('En Proceso');
insert into [dbo].[status] (status)
values ('Finalizado');
