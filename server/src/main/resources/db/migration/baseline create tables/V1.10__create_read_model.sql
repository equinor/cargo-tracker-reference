create table CT.analytics_cargo_rm (
    id nvarchar(36) primary key,
	month varchar(50) not null, 
	world_scale nvarchar(255),
	available bit,
	comments nvarchar(1000),
	grade_id nvarchar(36),
	trading_area_id nvarchar(36),
	destination_port_id nvarchar(36),
	destination_region_id nvarchar(36),
	laycan_start date,
	laycan_end date,
	source_country_id nvarchar(36),
	source_region_id nvarchar(36),
	destination_status smallint,
	vessel nvarchar(255),
	volume decimal(10,3),
	bl_date date NULL,
	source_system nvarchar(50) NOT NULL,
	source_system_identifier nvarchar(50) NULL,
	source_system_reference nvarchar(50) NULL,
	cancelled bit,
	version int NOT NULL,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL
);
GO

CREATE INDEX cargo_month_idx ON CT.analytics_cargo_rm (month);
GO

create table CT.title_transfer_rm (
	id nvarchar(36) primary key,
	company_id nvarchar(36),
	price decimal(18,4),
	price_basis nvarchar(255),
	cargo_id nvarchar(36),
	sequence int,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL
	);

create table CT.analytics_cargo_property_status_rm (
    cargo_id nvarchar(36),
    name nvarchar(255),
    status smallint,
	CONSTRAINT PK_analytics_cargo_property_status_rm PRIMARY KEY CLUSTERED (cargo_id, name)      
);
GO

ALTER TABLE [CT].[analytics_cargo_property_status_rm]  WITH CHECK ADD  CONSTRAINT [FK_field_status_cargo] FOREIGN KEY([cargo_id])
REFERENCES [CT].[analytics_cargo_rm] ([id]);


ALTER TABLE [CT].[title_transfer_rm]  WITH CHECK ADD  CONSTRAINT [FK_buyer_company] FOREIGN KEY([company_id])
REFERENCES [CT].[company] ([id]);
ALTER TABLE [CT].[title_transfer_rm]  WITH CHECK ADD  CONSTRAINT [FK_tt_cargo] FOREIGN KEY([cargo_id])
REFERENCES [CT].[analytics_cargo_rm] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_cargo_grade] FOREIGN KEY([grade_id])
REFERENCES [CT].[grade] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_cargo_trading_area] FOREIGN KEY([trading_area_id])
REFERENCES [CT].[trading_area] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_destination_port_terminal] FOREIGN KEY([destination_port_id])
REFERENCES [CT].[terminal] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_destination_region_region] FOREIGN KEY([destination_region_id])
REFERENCES [CT].[region] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_cargo_country] FOREIGN KEY([source_country_id])
REFERENCES [CT].[country] ([id]);
ALTER TABLE [CT].[analytics_cargo_rm]  WITH CHECK ADD  CONSTRAINT [FK_source_region_region] FOREIGN KEY([source_region_id])
REFERENCES [CT].[region] ([id]);










