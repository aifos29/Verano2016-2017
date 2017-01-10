CREATE FUNCTION [dbo].[getViewOfUser]()
RETURNS table AS
RETURN(
	Select sec.idSecretary as "ID", sec.name as "Nombre", logg.email,dep.department as "Departamento"
			from dbo.Secretary as sec inner join dbo.Department as dep on dep.idDepartment = sec.idDepartment
									  inner join dbo.logging as logg on sec.idLogging = logg.idLogging
		UNION
		Select plat.idPlataformers,plat.name, logg.email,'Plataformista' from dbo.Plataformers as plat
										inner join dbo.logging as logg on plat.idLogging = logg.idLogging
)
GO
