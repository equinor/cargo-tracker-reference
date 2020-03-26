create table CTREF.company (
    [id] [nvarchar](36) primary key,
    name nvarchar(450) NOT NULL,
	verified bit  NOT NULL,
	version int NOT NULL,
	updated_date_time date NOT NULL,
	updated_by nvarchar(255) NOT NULL,
	constraint company_name unique(name)
);
GO

create table CTREF.company_ocd_mapping (
    id bigint primary key identity,
    company_id nvarchar(36) NOT NULL,
	ocd_name nvarchar(450) NOT NULL,
	constraint ocd_mapping_ocd_name unique(company_id,ocd_name)
);
GO



ALTER TABLE CTREF.company_ocd_mapping  WITH CHECK ADD CONSTRAINT FK_ocd_mapping FOREIGN KEY(company_id)
REFERENCES CTREF.company (id)
GO

ALTER TABLE CTREF.company_ocd_mapping CHECK CONSTRAINT FK_ocd_mapping
GO