--create user [Cargo Tracker - Read only user Test] from external provider;
--create user [Cargo Tracker - User Test] from external provider;
--create user [Cargo Tracker - Super user Test] from external provider;
--create user [fg_CrudeCargoTracking_Users] from external provider;
--create user [fg_CrudeCargoTracking_SuperUsers] from external provider;



--grant select on schema :: ct_reporting to [fg_CrudeCargoTracking_Users];
--grant select on schema :: ct_reporting to [fg_CrudeCargoTracking_SuperUsers];
grant select on schema :: ct_reporting to [Cargo Tracker - Read only user Test];
grant select on schema :: ct_reporting to [Cargo Tracker - User Test];
grant select on schema :: ct_reporting to [Cargo Tracker - Super user Test];