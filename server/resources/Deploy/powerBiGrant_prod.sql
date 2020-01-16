create user [Cargo Tracker - User] from external provider;
create user [Cargo Tracker - Super user] from external provider;
create user [Cargo Tracker - Read only user] from external provider;

grant select on schema :: ct_reporting to [Cargo Tracker - User];
grant select on schema :: ct_reporting to [Cargo Tracker - Super user];
grant select on schema :: ct_reporting to [Cargo Tracker - Read only user];