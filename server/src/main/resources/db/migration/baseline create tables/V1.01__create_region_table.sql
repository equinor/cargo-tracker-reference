CREATE TABLE [CTREF].[region](
	[id] nvarchar(36) primary key,
	[name] [varchar](255) NULL,
	[active] [bit] NOT NULL,
	[version] [int] NOT NULL,
	[updated_by] [varchar](255) NOT NULL,
	[updated_date_time] [datetime] NOT NULL,
 CONSTRAINT [region_name] UNIQUE(name)
)
GO
