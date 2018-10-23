--
-- System scopess
--

INSERT INTO system_scope (scope, description, icon, restricted, default_scope, structured, structured_param_description) VALUES
  ('openid', 'log in using your identity', 'user', false, true, false, null),
  ('profile', 'read your basic profile info', 'list-alt', true, true, false, null),
  ('email', 'read your email address', 'envelope', true, true, false, null),
--('address', 'physical address', 'home', false, true, false, null),
--('phone', 'telephone number', 'bell', false, true, false, null),
  ('offline_access', 'access your info while not being logged in', 'time', true, false, false, null);
--('organization', 'organization information', 'home', false, true, false, null),
--('entitlement', 'entitlement information', 'home', false, true, false, null),
--('userids', 'userids information', 'home', false, true, false, null);