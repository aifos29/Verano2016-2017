USE [procedureDB]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_periodOfAbsence_Secretary]') AND parent_object_id = OBJECT_ID(N'[dbo].[periodOfAbsence]'))
ALTER TABLE [dbo].[periodOfAbsence] DROP CONSTRAINT [FK_periodOfAbsence_Secretary]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[periodOfAbsence]    Script Date: 12/28/2016 22:47:50 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[periodOfAbsence]') AND type in (N'U'))
DROP TABLE [dbo].[periodOfAbsence]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[periodOfAbsence]    Script Date: 12/28/2016 22:47:50 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[periodOfAbsence](
	[idPeriodOfAbsence] [int] IDENTITY(1,1) NOT NULL,
	[from] [date] NOT NULL,
	[to] [date] NOT NULL,
	[idSecretary] [int] NOT NULL,
 CONSTRAINT [PK_periodOfAbsence] PRIMARY KEY CLUSTERED 
(
	[idPeriodOfAbsence] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[periodOfAbsence]  WITH CHECK ADD  CONSTRAINT [FK_periodOfAbsence_Secretary] FOREIGN KEY([idSecretary])
REFERENCES [dbo].[Secretary] ([idSecretary])
GO

ALTER TABLE [dbo].[periodOfAbsence] CHECK CONSTRAINT [FK_periodOfAbsence_Secretary]
GO


