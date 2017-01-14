USE [procedureDB]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Administrator_logging]') AND parent_object_id = OBJECT_ID(N'[dbo].[Administrator]'))
ALTER TABLE [dbo].[Administrator] DROP CONSTRAINT [FK_Administrator_logging]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[Administrator]    Script Date: 01/14/2017 17:05:23 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Administrator]') AND type in (N'U'))
DROP TABLE [dbo].[Administrator]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[Administrator]    Script Date: 01/14/2017 17:05:23 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Administrator](
	[idAdministrator] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[idLogging] [int] NOT NULL,
 CONSTRAINT [PK_Administrator] PRIMARY KEY CLUSTERED 
(
	[idAdministrator] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Administrator]  WITH CHECK ADD  CONSTRAINT [FK_Administrator_logging] FOREIGN KEY([idLogging])
REFERENCES [dbo].[logging] ([idLogging])
GO

ALTER TABLE [dbo].[Administrator] CHECK CONSTRAINT [FK_Administrator_logging]
GO


