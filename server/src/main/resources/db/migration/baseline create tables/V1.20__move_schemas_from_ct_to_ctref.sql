INSERT INTO ctref.region
SELECT * 
FROM ct.region;

INSERT INTO ctref.trading_area
SELECT *
FROM ct.trading_area;

INSERT INTO ctref.country
SELECT *
FROM ct.country;

INSERT INTO ctref.refinery
SELECT *
FROM ct.refinery;

INSERT INTO ctref.grade
SELECT *
FROM ct.grade;

INSERT INTO ctref.terminal
SELECT *
FROM ct.terminal;

INSERT INTO ctref.company
SELECT *
FROM ct.company;

INSERT INTO ctref.analysis
SELECT *
FROM ct.analysis;

INSERT INTO ctref.company_ocd_mapping (company_id, ocd_name)
SELECT company_id, ocd_name
FROM ct.company_ocd_mapping;

INSERT INTO ctref.terminal_ocd_mapping (terminal_id, ocd_name)
SELECT terminal_id, ocd_name
FROM ct.terminal_ocd_mapping;

INSERT INTO ctref.file_upload
SELECT *
FROM ct.file_upload;
