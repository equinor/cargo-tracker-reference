create table CT.analytics_cargo_rm_aud (
    id nvarchar(36),
	world_scale nvarchar(255),
	available bit,	
	comments nvarchar(1000),
	grade_id nvarchar(36),
	trading_area_id varchar(36), 
	destination_port_id nvarchar(36),
	destination_region_id nvarchar(36),
	laycan_start date,
	laycan_end date,
	source_country_id nvarchar(36),
	source_region_id nvarchar(36),
	vessel nvarchar(255),
	volume decimal(10,3),
	bl_date date NULL,
	source_system nvarchar(50) NOT NULL,
	source_system_identifier nvarchar(50) NULL,
	source_system_reference nvarchar(50) NULL,
	version int NOT NULL,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL,
	cancelled bit,
	month varchar(50) not null,
	REV INT NOT NULL,
	REVTYPE INT NULL,
	
	PRIMARY KEY NONCLUSTERED(ID, REV)
);

create table CT.analytics_cargo_rm_title_transfer_rm_aud (
	id nvarchar(255),
	cargo_id nvarchar(36),
	sequence int,
	REV INT NOT NULL,
	REVTYPE INT NULL);
	
create table CT.title_transfer_rm_aud (
	id nvarchar(36),
	company_id nvarchar(36),
	price decimal,
	price_basis nvarchar(255),
	sequence int,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL,
	REV INT NOT NULL,
	REVTYPE INT NULL);
	
	
create table CT.analytics_cargo_property_status_rm_aud (
    cargo_id nvarchar(36) primary key,
    name nvarchar(255),
    status smallint,
    REV INT NOT NULL,
	REVTYPE INT NULL
);
GO	
	