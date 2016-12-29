USE [procedureDB]
GO

/****** Object:  Table [dbo].[Binnacle]    Script Date: 12/28/2016 22:01:57 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Binnacle]') AND type in (N'U'))
DROP TABLE [dbo].[Binnacle]
GO

USE [procedureDB]
GO

/****** Object:  Table [dbo].[Binnacle]    Script Date: 12/28/2016 22:01:57 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Binnacle](
	[idBinnacle] [int] NOT NULL,
	[date] [datetime2](7) NOT NULL,
	[code] [nvarchar](50) NOT NULL,
	[column] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Binnacle] PRIMARY KEY CLUSTERED 
(
	[idBinnacle] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


