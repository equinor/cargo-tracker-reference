ALTER VIEW ct_reporting.analytics_cargo_view AS
SELECT 
	rm.id,
	a.api,
	a.sulphur,
	rm.world_Scale,
	rm.available,
	rm.comments,
	g.name grade,
	t.name destinationPort,
	rd.name destinationRegion,
	rm.laycan_Start,
	rm.laycan_End,
	c.name source_Country,
	rs.name sourceRegion,
	rm.destination_Status,
	rm.vessel,
	rm.volume,
	rm.bl_date,
	rm.month year_month,
	convert(date, CONCAT(month, '-01'), 23) first_of_month,
	rm.source_system,
	rm.source_system_identifier,
	rm.source_system_reference,
	ta.name trading_area,
	rm.version,
	rm.updated_date_time,
	rm.updated_by,
	(select CASE WHEN count(*)>0 THEN 1 ELSE 0 END from ct_reporting.title_transfer_view t where t.cargo_id = rm.id and upper(t.company) like '%EQUINOR%') equinor_cargo,
	(select top 1 company from ct_reporting.title_transfer_view t where t.cargo_id = rm.id order by sequence) equity_owner
	FROM ct.Analytics_cargo_rm rm
		LEFT JOIN ct.Grade g ON rm.grade_id = g.id
		LEFT JOIN ct.terminal t ON rm.destination_Port_Id = t.id
		LEFT JOIN ct.region rd ON rm.destination_Region_Id = rd.id
		LEFT JOIN ct.region rs ON rm.source_Region_Id = rs.id
		LEFT JOIN ct.country c ON rm.source_Country_Id = c.id
		LEFT JOIN ct.trading_area ta ON rm.trading_area_Id = ta.id
		LEFT JOIN ct.analysis a on g.id = a.grade_id 
			and (convert(date, CONCAT(month, '-01'), 23) > a.from_date 
				and (convert(date, CONCAT(month, '-01'), 23) < a.to_date
				or a.to_date is null))
		where rm.cancelled = 0;