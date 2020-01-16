update ct.analysis 
	set country_id = (select id from ct.country where name = 'Ghana')
	where grade_id = (select id from ct.grade where name = 'Sankofa');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Nigeria')
	where grade_id = (select id from ct.grade where name = 'Okono');