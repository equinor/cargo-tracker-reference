CREATE TABLE [CT].[wet_list_grade_mapper](
	[id] nvarchar(36) primary key,
	[grade_id] nvarchar(36),
	[name] [varchar](255) NULL,
	[bofet] [varchar](255) NULL,
	[country_group] [varchar](255) NULL,
	[category] nvarchar(255) NULL
)
GO

ALTER TABLE [CT].[wet_list_grade_mapper]  WITH CHECK ADD CONSTRAINT [FK_grade_wet_list_grade] FOREIGN KEY([grade_id])
REFERENCES [CT].[grade] ([id])
GO
