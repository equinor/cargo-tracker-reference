CREATE VIEW ct_reporting.available_cargoes_view AS
SELECT 
	aca.id, 
	aca.month, 
	aca.available, 
	aca.available_date, 
	grade, 
	destinationPort, 
	destinationRegion, 
	laycan_Start, 
	laycan_End, 
	source_Country, 
	sourceRegion,
	vessel,
	volume,
	bl_date, 
	source_system,
	source_system_identifier,
	source_system_reference
  FROM 
	ct_reporting.analytics_cargo_availability aca, 
	ct_reporting.analytics_cargo_view acv
  WHERE aca.cargo_id = acv.id;
