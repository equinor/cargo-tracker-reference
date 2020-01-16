CREATE VIEW ct_reporting.available_cargoes_view AS
SELECT 
	aca.id, 
	aca.month, 
	aca.available_cargoes,
	aca.total_cargoes,
	aca.available_date,
	g.name grade,
	t.name trading_area,
	aca.equinor_cargo,
	convert(date, CONCAT(month, '-01'), 23) first_of_month
FROM ct_reporting.analytics_cargo_availability aca
	LEFT JOIN ct.Grade g ON aca.grade_id = g.id
	LEFT JOIN ct.trading_area t ON aca.trading_area_id = t.id
UNION
(SELECT
	null, 
	year_month, 
	SUM(CASE WHEN available=1 THEN 1 ELSE 0 END),
	COUNT(*),
	CAST(GETDATE() AS DATE), 
	grade,
	trading_area,
	equinor_cargo,
	convert(date, CONCAT(year_month, '-01'), 23)
FROM  ct_reporting.analytics_cargo_view  acw
where convert(date, CONCAT(year_month, '-01'), 23) >= DATEADD(DAY,1,EOMONTH(CAST(GETDATE() AS DATE),-1))
GROUP BY year_month, grade, trading_area, equinor_cargo);