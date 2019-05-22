CREATE TABLE customer
(
  id          SERIAL PRIMARY KEY,
  id_users    INTEGER     NOT NULL REFERENCES users (id),
  first_name  VARCHAR(30) NOT NULL,
  last_name   VARCHAR(30) NOT NULL,
  title       VARCHAR(60) NOT NULL,
  address     VARCHAR(60) NOT NULL,
  telephone   VARCHAR(30) NOT NULL,
  email       VARCHAR(40) NOT NULL,
  edrpou_code INTEGER
);