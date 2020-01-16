create table ct_reporting.analytics_cargo_availability (
    id nvarchar(36) primary key,
	month varchar(50) not null,
	grade_id nvarchar(36),
	trading_area_id nvarchar(36),
	available_cargoes integer,
	total_cargoes integer,		
	available_date date,
	equinor_cargo bit,
	updated_date_time datetime NOT NULL
);

CREATE INDEX analytics_cargo_availability_idx ON ct_reporting.analytics_cargo_availability (month);

ALTER TABLE ct_reporting.analytics_cargo_availability  WITH CHECK ADD CONSTRAINT [FK_ac_grade] FOREIGN KEY([grade_id])
REFERENCES [CT].[grade] ([id]);

ALTER TABLE ct_reporting.analytics_cargo_availability  WITH CHECK ADD CONSTRAINT [FK_ac_trading_area] FOREIGN KEY([trading_area_id])
REFERENCES [CT].[trading_area] ([id]);