
ALTER VIEW ct_reporting.wetlist_view AS
WITH cargos AS(
	SELECT 
		ac.id,
		bl_date,
		laycan_start,
		laycan_end,
		g.name grade,
		volume,
		convert(date, CONCAT(month, '-01'), 23) first_of_month,
		comments,
		ta.name trading_area
	FROM [CT].[analytics_cargo_rm] ac
		LEFT JOIN ct.Grade g ON ac.grade_id = g.id
		LEFT JOIN ct.trading_area ta ON ac.trading_area_id = ta.id
	where convert(date, CONCAT(month, '-01'), 23) >=  DateAdd(yy, -1, GetDate())
	)

,transfers AS(
	SELECT
		c.id, 
		string_agg(t.company,'-') within group (order by sequence) as company
	FROM cargos c inner join[ct_reporting].[title_transfer_view] t on c.id = t.cargo_id
	GROUP BY c.id)


select 	c.id, 
		bl_date, 
		laycan_start, 
		laycan_end, 
		first_of_month, 
		grade, 
		volume, 
		comments, 
		company,
		trading_area
from cargos c 
	inner join transfers t on c.id = t.id;