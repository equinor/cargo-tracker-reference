CREATE TABLE [CTREF].[grade](
	[id] nvarchar(36) primary key,
	[name] [varchar](255) NULL,
	[ocd_name] [varchar](255) NULL,
	[source] [varchar](255) NULL,
	[trading_area_id] nvarchar(36) NULL,
	[verified] [bit] NOT NULL,
	[version] [int] NOT NULL,
	[updated_date_time] [datetime] NOT NULL,
	[updated_by] [nvarchar](255) NOT NULL,
	CONSTRAINT [grade_name] UNIQUE(name)
)
GO

ALTER TABLE [CTREF].[grade]  WITH CHECK ADD  CONSTRAINT [FK_grade_trading_area] FOREIGN KEY([trading_area_id])
REFERENCES [CTREF].[trading_area] ([id])
GO

ALTER TABLE [CTREF].[grade] CHECK CONSTRAINT [FK_grade_trading_area]
GO

create table CTREF.analysis (
	id nvarchar(36) primary key,
	grade_id nvarchar(36),
	api numeric(5,2) NULL,
	sulphur numeric(5,2) NULL,
	from_date date,
	to_date date,
	reference varchar(255) NULL,
	country_id nvarchar(36) NULL,
	version int not null,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL
	);

ALTER TABLE [CTREF].[analysis]  WITH CHECK ADD  CONSTRAINT [FK_analysis_grade] FOREIGN KEY([grade_id])
REFERENCES [CTREF].[grade] ([id]);

ALTER TABLE [CTREF].[analysis]  WITH CHECK ADD  CONSTRAINT [FK_analysis_country] FOREIGN KEY([country_id])
REFERENCES [CTREF].[country] ([id])
GO