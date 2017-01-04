USE [procedureDB]
GO
Create FUNCTION getConsecutive
(
    @departmentId int
)
RETURNS varchar(100) -- or whatever length you need
AS
BEGIN
    Declare @logid varchar(50);
    Declare @departmentCode varchar(50);
    Declare @number int;
    Declare @year int;
    
    Select @departmentCode = d.code from [dbo].[department] as d
    where d.idDepartment = @departmentId
    SELECT @number = consecutiveNumber from [dbo].[consecutive]
    SELECT @year = consecutiveYear from [dbo].[consecutive]
    
    SET @logid = @departmentCode + '-' + cast(@number as varchar) + '-' + cast(@year as varchar);

    RETURN  @logid

END
GO