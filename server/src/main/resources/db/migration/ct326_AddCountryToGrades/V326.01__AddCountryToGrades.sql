update ct.analysis 
	set country_id = (select id from ct.country where name = 'Equatorial Guinea')
	where grade_id = (select id from ct.grade where name = 'asseng');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Ivory Coast')
	where grade_id = (select id from ct.grade where name = 'Baobab');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Congo Brazzaville')
	where grade_id = (select id from ct.grade where name = 'Coco');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Nigeria')
	where grade_id = (select id from ct.grade where name = 'Ea');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Ivory coast')
	where grade_id = (select id from ct.grade where name = 'Espoir');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Cameroon')
	where grade_id = (select id from ct.grade where name = 'Lokele');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Nigeria')
	where grade_id = (select id from ct.grade where name = 'Okwori');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Nigeria')
	where grade_id = (select id from ct.grade where name = 'Okwuibome');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Equatorial Guinea')
	where grade_id = (select id from ct.grade where name = 'Olende');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Gabon')
	where grade_id = (select id from ct.grade where name = 'Rabi Light Blend');
update ct.analysis 
	set country_id = (select id from ct.country where name = 'Nigeria')
	where grade_id = (select id from ct.grade where name = 'Yoho');
