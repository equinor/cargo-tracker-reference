CREATE TABLE [CT].[terminal](
	[id] [nvarchar](36) primary key,
	[name] [nvarchar](255) NULL,
	[source] [nvarchar](255) NULL,
	[country_id] nvarchar(36) NULL,
	[latitude] numeric(18,13) NULL,
	[longitude] numeric(18,13) NULL,
	[version] [int] NOT NULL,
	[updated_date_time] [datetime] NOT NULL,
	[updated_by] [nvarchar](255) NOT NULL
)
GO

ALTER TABLE [CT].[terminal]  WITH CHECK ADD  CONSTRAINT [FK_terminal_country] FOREIGN KEY([country_id])
REFERENCES [CT].[country] ([id])
GO

ALTER TABLE [CT].[terminal] CHECK CONSTRAINT [FK_terminal_country]
GO