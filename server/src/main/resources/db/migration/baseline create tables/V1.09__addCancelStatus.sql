-- Company
ALTER TABLE ctref.company
ADD short_name nvarchar(36) NULL;
ALTER TABLE ctref.company
ADD cancelled bit NULL;

-- Grade
ALTER TABLE ctref.grade
ADD cancelled bit NULL;

-- Refinery
ALTER TABLE ctref.refinery
ADD cancelled bit NULL;

-- Terminal
ALTER TABLE ctref.terminal
ADD cancelled bit NULL;

-- Country
ALTER TABLE ctref.country
ADD cancelled bit NULL;
