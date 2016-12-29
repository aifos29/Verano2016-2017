USE [procedureDB]
GO

/****** Object:  Table [dbo].[logging]    Script Date: 12/28/2016 21:42:59 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[logging]') AND type in (N'U'))
DROP TABLE [dbo].[logging]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[logging]    Script Date: 12/28/2016 21:42:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[logging](
	[idLogging] [int] NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[password] [nvarchar](300) NOT NULL,
 CONSTRAINT [PK_logging] PRIMARY KEY CLUSTERED 
(
	[idLogging] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


