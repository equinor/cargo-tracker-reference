alter VIEW ct_reporting.custom_grades_view AS 
with grades as (SELECT name as grade
  FROM [CT].[Grade])

  SELECT 
  grade as Grade, 
  BOFET = 
	CASE
		WHEN grade in ('Brent', 'Eko', 'Fort', 'Ose', 'Trblend') then 'BOFET'
		WHEN grade in ('Alba', 'Alvblend','Asgblend','Drau', 'Duc','Flotta','Foin', 'Gfblend','Goliat','Grane','Gry', 'He', 'Jot','Knarr','Njor','Norne','Pierce','Schi','SF','Siri','Skarv','Varg', 'Volve') then 'NotBFOET'
		ELSE grade 
	END,
  GradesCountryGroup = 
	CASE
		WHEN grade in ('Duc','Siri') then 'Denmark'
		WHEN grade in ('Alvblend','Asgblend','Drau','Eko','Gfblend','Goliat','Grane','He','Jot','Knarr','Njor','Norne','Ose','Sf','Skarv','Trblend','Varg', 'Volve') then 'Norway'
		WHEN grade in ('Alba','Brent','Flotta','Foin','Fort','Gry','Pierce','Schi') then 'UK'
		ELSE grade 
	END,
  WetlistGrade =
	CASE
		WHEN grade = 'Gullfaks' then 'GF'
		WHEN grade = 'Gina Krog' then 'GK'
		WHEN grade = 'Grane' then 'Gr'
		WHEN grade = 'Gudrun Blend' then 'Gu'
		WHEN grade = 'Goliat Blend' then 'GO'
		WHEN grade = 'Skarv' then 'Sk'
		WHEN grade = 'Alvheim' then 'Al'
		WHEN grade = 'Norne' then 'NO'
		WHEN grade = 'Statfjord' then 'SF'
		WHEN grade = 'Oseberg Blend' then 'Ose'
		WHEN grade = 'Ekofisk Blend' then 'EKO'
		WHEN grade = 'Forties Blend' then 'F'
		WHEN grade = 'Brent Blend' then 'B'
		WHEN grade = 'Ormen Lange' then 'Or'
		WHEN grade = 'Troll' then 'Tr'
		WHEN grade = 'Heidrun' then 'Hei'
		WHEN grade = 'Draugen' then 'DR'
		WHEN grade = 'Snøhvit Condensate' then 'Sn'
		WHEN grade = 'Åsgard Blend' then 'Asg'
		WHEN grade = 'Njord' then 'NJ'
		WHEN grade = 'Jotun' then 'JO'
		WHEN grade = 'Johan Sverdrup' then 'JS'
		WHEN grade = 'Mariner Blend' then 'Mar'
		WHEN grade = 'Captain' then 'Cap'
		WHEN grade = 'Catcher Blend' then 'Ca'
		WHEN grade = 'Clair' then 'Cla'
		WHEN grade = 'Siri' then 'Si'
		WHEN grade = 'South Arne' then 'Syd'
		WHEN grade = 'Alba' then 'Al'
		WHEN grade = 'Schiehallion Blend' then 'Sch'
		WHEN grade = 'Kraken' then 'Kr'
		WHEN grade = 'DUC' then 'Duc'
		ELSE grade 
	END,
	WetlistColCategory = 
	CASE 
        WHEN grade = 'Ekofisk Blend' THEN 'Ekofisk'
        WHEN grade = 'Grane' THEN 'Grane/Hei'
        WHEN grade = 'Heidrun' THEN 'Grane/Hei'
        WHEN grade = 'Statfjord' THEN 'SF/DR/GOLIAT/DUC'
        WHEN grade = 'Draugen' THEN 'SF/DR/GOLIAT/DUC'
        WHEN grade = 'Goliat Blend' THEN 'SF/DR/GOLIAT/DUC'
        WHEN grade = 'DUC' THEN 'SF/DR/GOLIAT/DUC'
        WHEN grade = 'Åsgard Blend' THEN 'ÅSG/GUD/SNØ/ORM'
        WHEN grade = 'Gudrun Blend' THEN 'ÅSG/GUD/SNØ/ORM'
        WHEN grade = 'Snøhvit Condensate' THEN 'ÅSG/GUD/SNØ/ORM'
        WHEN grade = 'Ormen Lange' THEN 'ÅSG/GUD/SNØ/ORM'
        WHEN grade = 'Mariner Blend' THEN 'Mariner'
        WHEN grade = 'Brent blend' THEN 'Brent/Oseberg/Troll'
        WHEN grade = 'Oseberg blend' THEN 'Brent/Oseberg/Troll'
        WHEN grade = 'Troll' THEN 'Brent/Oseberg/Troll'
        ELSE grade
    END
from grades;
