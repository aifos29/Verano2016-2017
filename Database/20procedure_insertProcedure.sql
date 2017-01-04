USE [procedureDB]
GO
Create PROCEDURE insertProcedure
(
    @date date,
	@departmentId int,
	@code varchar(50),
	@idTypeOfIdentify int,
	@personID varchar(50),
	@idTypeOfProcedure int,
	@detail varchar(50),
	@userId int
)
AS
BEGIN
    Declare @statusId int;
    Declare @message varchar(100);
    
    BEGIN TRY
		Select @statusId = s.idStatus from [dbo].[status] as s 
		where s.status = 'Nuevo';
		
		insert into [dbo].[procedure] (date,code,details,identifyCode,idStatus,idDepartment, idTypeOfProcedure, idTypeOfIdentify,idPlatformers)
		values (@date,@code,@detail,@personID,@statusId,@departmentId,@idTypeOfProcedure,@idTypeOfIdentify,@userId);
		
		update [dbo].[consecutive] 
		SET consecutiveNumber = consecutiveNumber + 1
		where consecutiveId = 1;
		
	END TRY
	BEGIN CATCH
		Select ERROR_MESSAGE() as messageError;
	END CATCH;
END
GO