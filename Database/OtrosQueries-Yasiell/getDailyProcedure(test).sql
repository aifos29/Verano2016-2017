USE [procedureDB]
GO
CREATE FUNCTION getDailyProcedures(
)
RETURNS table AS
RETURN(
	Select pr.date as "Fecha" ,pr.code as "Consecutivo",pr.identifyCode as "C�dula",
		tp.TypeOfProcedure as "Tipo de Procedimiento", plat.name as "Plataformista", pr.idProcedure as "id"
	FROM dbo.[procedure] pr
	INNER JOIN dbo.[Department] dep on pr.idDepartment = dep.idDepartment
	INNER JOIN dbo.[typeOfProcedure] tp on pr.idTypeOfProcedure = tp.idTypeOfProcedure
	INNER JOIN dbo.[typeOfIdentify] ti on pr.idTypeOfIdentify = ti.idTypeOfIdentify
	INNER JOIN dbo.[Plataformers] plat on pr.idPlatformers = plat.idPlataformers	
)