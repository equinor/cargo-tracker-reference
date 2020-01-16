CREATE TABLE [CT].[country](
	[id] nvarchar(36) primary key,
	[name] [varchar](255) NULL,
	[source] [varchar](255) NULL,
	[region_id] nvarchar(36) NULL,
	[latitude] numeric(18,13) NULL,
	[longitude] numeric(18,13) NULL,
	[version] [int] NOT NULL,
	[updated_date_time] [datetime] NOT NULL,
	[updated_by] [nvarchar](255) NOT NULL)
GO

ALTER TABLE [CT].[country]  WITH CHECK ADD  CONSTRAINT [FK_country_region] FOREIGN KEY([region_id])
REFERENCES [CT].[region] ([id])
GO

ALTER TABLE [CT].[country] CHECK CONSTRAINT [FK_country_region]
GO