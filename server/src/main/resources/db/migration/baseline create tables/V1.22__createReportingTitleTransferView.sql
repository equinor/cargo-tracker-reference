create VIEW ct_reporting.title_transfer_view AS 
SELECT
	tt.id id,
	c.name company,
	tt.price price,
	tt.price_basis price_basis,
	tt.cargo_id cargo_id,
	tt.sequence sequence,
	tt.updated_date_time updated_date_time,
	tt.updated_by updated_by
	FROM CT.title_transfer_rm tt
		LEFT JOIN ct.company c ON tt.company_id = c.id;
