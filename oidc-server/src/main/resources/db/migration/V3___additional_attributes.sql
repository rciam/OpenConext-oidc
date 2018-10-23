--
-- Additional attributes to store for the UserInfo table
--
CREATE INDEX ui_sub_idx ON user_info(sub);

ALTER TABLE user_info ADD DTYPE varchar(256) DEFAULT NULL;

ALTER TABLE user_info ADD unspecified_name_id varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_unique_id varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_principal_name varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD edu_person_targeted_id varchar(256) DEFAULT NULL;

ALTER TABLE user_info ADD authenticating_authority varchar(256) DEFAULT NULL;
ALTER TABLE user_info ADD acr varchar(256) DEFAULT NULL;

BEGIN;
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
      NEW.updated_at = now(); 
      RETURN NEW;
END;
$$ language 'plpgsql';
COMMIT;

BEGIN;
ALTER TABLE user_info ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE user_info ADD updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE TRIGGER user_timestamp BEFORE INSERT OR UPDATE ON user_info
FOR EACH ROW EXECUTE PROCEDURE update_timestamp();

COMMIT;