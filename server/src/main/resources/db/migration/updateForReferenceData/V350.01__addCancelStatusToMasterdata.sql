-- Company
ALTER TABLE CT.company
ADD cancelled bit NULL;

-- Grade
ALTER TABLE CT.grade
ADD cancelled bit NULL;

-- Refinery
ALTER TABLE CT.refinery
ADD cancelled bit NULL;

-- Terminal
ALTER TABLE CT.terminal
ADD cancelled bit NULL;

-- Country
ALTER TABLE CT.country
ADD cancelled bit NULL;
