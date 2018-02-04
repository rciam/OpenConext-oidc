--
-- Add Authentication Context Class Reference (acr) to UserInfo,
--

ALTER TABLE user_info ADD acr varchar(256) DEFAULT NULL;