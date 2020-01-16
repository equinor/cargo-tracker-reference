CREATE SCHEMA CT;
GO
create table CT.analytics_cargo (
    id nvarchar(36) primary key,
    cargo nvarchar(max) NULL,
	version int NOT NULL,
	bl_date date NULL,
	source_system nvarchar(50) NOT NULL,
	source_system_identifier nvarchar(50) NULL,
	cancelled bit,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL
);
GO