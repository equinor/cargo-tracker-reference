WITH DUPLICATE_CA AS
(
SELECT *,ROW_NUMBER() OVER (
PARTITION BY	trading_area_id, equity_owner_id, grade_id, month, available_date, equinor_cargo 
ORDER BY		trading_area_id, equity_owner_id, grade_id, month, available_date, equinor_cargo) AS RN
FROM ct_reporting.analytics_cargo_availability
)

DELETE FROM DUPLICATE_CA WHERE RN<>1;