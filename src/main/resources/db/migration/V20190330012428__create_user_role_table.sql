CREATE TABLE user_role
(
  id_user INTEGER NOT NULL REFERENCES users (id),
  id_role INTEGER NOT NULL REFERENCES role (id)
);