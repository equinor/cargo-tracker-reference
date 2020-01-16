create table CT.terminal_ocd_mapping (
    id bigint primary key identity,
    terminal_id nvarchar(36) NOT NULL,
	ocd_name nvarchar(450) NOT NULL,
	constraint ocd_terminalmapping_ocd_name unique(terminal_id,ocd_name)
);
GO



ALTER TABLE CT.terminal_ocd_mapping  WITH CHECK ADD CONSTRAINT FK_ocd_mapping_terminal FOREIGN KEY(terminal_id)
REFERENCES CT.terminal (id)
GO

ALTER TABLE CT.terminal_ocd_mapping CHECK CONSTRAINT FK_ocd_mapping_terminal
GO