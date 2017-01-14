Create PROCEDURE [dbo].[insertAdministrator]
(
	@name nvarchar(50),
	@idLogginging int
)
AS
BEGIN

    Declare @message varchar(100);
    
INSERT INTO [procedureDB].[dbo].[Administrator]
           ([name]
           ,[idLogging])
     VALUES
           (@name
           ,@idLogginging)
		
END

GO
