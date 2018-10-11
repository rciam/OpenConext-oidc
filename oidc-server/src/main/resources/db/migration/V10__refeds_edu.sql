--
-- Add eduperson_entitlement to UserInfo,
--

CREATE TABLE IF NOT EXISTS user_eduperson_assurance (
	user_id BIGINT,
	eduperson_assurance VARCHAR(256)
);
CREATE INDEX uepa_id_idx ON user_eduperson_assurance(user_id);