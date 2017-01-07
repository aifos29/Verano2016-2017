USE [procedureDB]
GO
Create PROCEDURE updateProcedure
(
	@code varchar(50),
	@idTypeOfIdentify int,
	@personID varchar(50),
	@idTypeOfProcedure int,
	@detail varchar(50)
)
AS
BEGIN
    BEGIN TRY
		update [dbo].[procedure] 
		SET idTypeOfIdentify = @idTypeOfIdentify, 
		identifyCode = @personID,
		idTypeOfProcedure = @idTypeOfProcedure,
		details = @detail
		where code = @code;
		
	END TRY
	BEGIN CATCH
		Select ERROR_MESSAGE() as messageError;
	END CATCH;
END
GO