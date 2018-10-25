--
-- System scopess
--

INSERT INTO system_scope (scope, description, icon, restricted, default_scope, structured, structured_param_description) VALUES
  ('openid', 'log in using your identity', 'user', false, true, false, null),
  ('profile', 'read your basic profile info', 'list-alt', true, true, false, null),
  ('email', 'read your email address', 'envelope', true, true, false, null),
  ('offline_access', 'access your info while not being logged in', 'time', true, false, false, null);
  ('eduperson_entitlement', 'read your rights to resources', 'bookmark', true, true, false, null);
  ('eduperson_scoped_affiliation', 'read your affiliation within a domain', 'briefcase', true, true, false, null);
  ('eduperson_unique_id', 'read your unique identifier', 'tag', true, true, false, null);