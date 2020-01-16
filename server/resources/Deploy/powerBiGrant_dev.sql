create user [fg_CrudeCargoTracking_Users] from external provider;
create user [fg_CrudeCargoTracking_SuperUsers] from external provider;

grant select on schema :: ct_reporting to [fg_CrudeCargoTracking_Users];
grant select on schema :: ct_reporting to [fg_CrudeCargoTracking_SuperUsers];