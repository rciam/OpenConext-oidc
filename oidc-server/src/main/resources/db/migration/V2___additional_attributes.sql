--
-- Additional attributes to store for the UserInfo,
-- see https://wiki.surfnet.nl/pages/viewpage.action?spaceKey=P3GFeI2015&title=OpenID%20Connect%20Implementatie
--
CREATE INDEX ui_sub_idx ON user_info(sub);

ALTER TABLE user_info ADD DTYPE varchar(256) DEFAULT NULL;

ALTER TABLE user_info ADD unspecified_name_id varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_unique_id varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_principal_name varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_targeted_id varchar(256) DEFAULT NULL;

CREATE TABLE IF NOT EXISTS user_edu_person_scoped_affiliation (
	user_id BIGINT,
	edu_person_scoped_affiliation VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS user_edu_person_entitlement (
	user_id BIGINT,
	edu_person_entitlement VARCHAR(256)
);

CREATE INDEX uepsa_ui_idx ON user_edu_person_scoped_affiliation(user_id);
CREATE INDEX uepe_ui_idx ON user_edu_person_entitlement(user_id);