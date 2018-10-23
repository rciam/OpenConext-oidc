CREATE TABLE IF NOT EXISTS user_edu_person_scoped_affiliation (
	user_id BIGINT,
	edu_person_scoped_affiliation VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS user_edu_person_entitlement (
	user_id BIGINT,
	edu_person_entitlement VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS user_eduperson_assurance (
	user_id BIGINT,
	eduperson_assurance VARCHAR(256)
);

CREATE INDEX uepsa_ui_idx ON user_edu_person_scoped_affiliation(user_id);
CREATE INDEX uepe_ui_idx ON user_edu_person_entitlement(user_id);
CREATE INDEX uepa_id_idx ON user_eduperson_assurance(user_id);