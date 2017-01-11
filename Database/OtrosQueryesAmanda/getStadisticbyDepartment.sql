

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo.getStadisticbyDepartment] (	)
RETURNS TABLE 
AS
RETURN 
(
	Select Pro.date, dep.department, COUNT(dep.department) as Cantidad from Department as dep
	inner join [procedure] as Pro
	on Pro.idDepartment = dep.idDepartment
	group by Pro.date, dep.department
)
GO
