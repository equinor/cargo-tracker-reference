create table CT.analytics_cargo_aud (
    id nvarchar(36) not null,
    cargo nvarchar(max) NULL,
	bl_date date NULL,
	source_system nvarchar(50) NOT NULL,
	source_system_identifier nvarchar(50) NULL,
	cancelled bit,
	updated_date_time datetime NOT NULL,
	updated_by nvarchar(255) NOT NULL,
	rev INT NOT NULL,
	revtype INT NULL,
	
	PRIMARY KEY NONCLUSTERED(ID, REV)
	
);

CREATE TABLE CT.revinfo 
( 
    rev      INT PRIMARY KEY IDENTITY, 
    revtstmp BIGINT
);
GO