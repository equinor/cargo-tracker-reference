-- Company
update CT.company set cancelled = 0;

ALTER TABLE CT.company
alter column cancelled bit not NULL;

-- Grade
update CT.Grade set cancelled = 0;

ALTER TABLE CT.Grade
alter column cancelled bit not NULL;

-- Refinery
update CT.refinery set cancelled = 0;

ALTER TABLE CT.refinery
alter column cancelled bit not NULL;

-- Terminal
update CT.terminal set cancelled = 0;

ALTER TABLE CT.terminal
alter column cancelled bit not NULL;

-- Country
update CT.country set cancelled = 0;

ALTER TABLE CT.country
alter column cancelled bit not NULL;