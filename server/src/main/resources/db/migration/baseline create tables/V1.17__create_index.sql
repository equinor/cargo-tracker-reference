CREATE NONCLUSTERED INDEX title_transfer_cargo_id_idx ON CT.title_transfer_rm (cargo_id) INCLUDE (company_id, price, price_basis, sequence, updated_by, updated_date_time) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX analytics_cargo_rm_idx ON CT.analytics_cargo_rm (cancelled, grade_id, bl_date) INCLUDE (month, source_country_id, volume) WITH (ONLINE = ON);

CREATE NONCLUSTERED INDEX title_transfer_aud_cargo_id_idx ON CT.title_transfer_rm_aud (id) INCLUDE (company_id, price, price_basis, sequence, rev, updated_by, updated_date_time) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX analytics_cargo_rm_title_transfer_rm_aud_idx ON CT.analytics_cargo_rm_title_transfer_rm_aud (cargo_id) INCLUDE (id, sequence, rev) WITH (ONLINE = ON);

CREATE NONCLUSTERED INDEX analysis_grade_id_idx ON CT.analysis (grade_id) INCLUDE (api, sulphur, from_date, to_date, reference, country_id, version, updated_by, updated_date_time) WITH (ONLINE = ON);
