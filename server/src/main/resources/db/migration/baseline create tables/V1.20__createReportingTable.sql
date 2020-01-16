create table ct_reporting.analytics_cargo_availability (
    id nvarchar(36) primary key,
	cargo_id nvarchar(36),
	month varchar(50) not null,
	available bit,	
	available_date date,
	updated_date_time datetime NOT NULL
);

CREATE INDEX analytics_cargo_availability_idx ON ct_reporting.analytics_cargo_availability (month);

ALTER TABLE ct_reporting.analytics_cargo_availability  WITH CHECK ADD CONSTRAINT [FK_ac_availability_cargo] FOREIGN KEY([cargo_id])
REFERENCES [CT].[analytics_cargo_rm] ([id]);