create table ct.sport_ifc_cargo (
    id bigint primary key identity,
    payload nvarchar(max) NULL,	
	bl_date date NOT NULL,
	sport_no bigint NOT NULL,	
	sport_cargo_no nvarchar(50) NOT NULL,
	analytics_cargo_id bigint NULL,
	message_id nvarchar(100),
	causation_id nvarchar(100),
	correlation_id nvarchar(100)
);
GO


