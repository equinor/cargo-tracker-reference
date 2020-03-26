CREATE TABLE [CTREF].[terminal](
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

ALTER TABLE [CTREF].[terminal]  WITH CHECK ADD  CONSTRAINT [FK_terminal_country] FOREIGN KEY([country_id])
REFERENCES [CTREF].[country] ([id])
GO

ALTER TABLE [CTREF].[terminal] CHECK CONSTRAINT [FK_terminal_country]
GO

create table CTREF.terminal_ocd_mapping (
    id bigint primary key identity,
    terminal_id nvarchar(36) NOT NULL,
	ocd_name nvarchar(450) NOT NULL,
	constraint terminal_ocd_mapping_ocd_name unique(terminal_id,ocd_name)
);