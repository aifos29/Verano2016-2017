USE [procedureDB]
GO
CREATE FUNCTION searchByDepartment(
	@department nvarchar(50) 
)
RETURNS table AS
RETURN(
	Select pr.date as "Fecha" ,pr.code as "Consecutivo",pr.details as "Detalle",pr.identifyCode as "C�dula",
		st.status as "Estado", tp.TypeOfProcedure as "Tipo de Procedimiento", plat.name as "Plataformista"
	FROM dbo.[procedure] pr
	INNER JOIN dbo.[Department] dep on pr.idDepartment = dep.idDepartment
	INNER JOIN dbo.[status] st on pr.idStatus = st.idStatus
	INNER JOIN dbo.[typeOfProcedure] tp on pr.idTypeOfProcedure = tp.idTypeOfProcedure
	INNER JOIN dbo.[typeOfIdentify] ti on pr.idTypeOfIdentify = ti.idTypeOfIdentify
	INNER JOIN dbo.[Plataformers] plat on pr.idPlatformers = plat.idPlataformers
	Where dep.department = @department 		
)