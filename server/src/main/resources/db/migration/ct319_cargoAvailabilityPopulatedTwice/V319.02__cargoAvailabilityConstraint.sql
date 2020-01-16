ALTER TABLE ct_reporting.analytics_cargo_availability
ADD CONSTRAINT cargoAvailUniq UNIQUE (trading_area_id, equity_owner_id, grade_id, month, available_date, equinor_cargo);