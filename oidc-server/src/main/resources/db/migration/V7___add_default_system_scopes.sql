DELETE FROM system_scope WHERE scope NOT IN ('openid');
INSERT INTO system_scope (scope, description, icon, restricted, default_scope, structured, structured_param_description)
VALUES
  ('address', 'The address of the user', '', false, true, false, ''),
  ('email', 'The email of the user', '', false, true, false, ''),
  ('profile', 'The profile of the user', '', false, true, false, ''),
  ('phone', 'The phone of the user', '', false, true, false, '');
