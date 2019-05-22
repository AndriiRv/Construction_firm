CREATE TABLE worker
(
  id            SERIAL PRIMARY KEY,
  first_name    VARCHAR(30) NOT NULL,
  last_name     VARCHAR(30) NOT NULL,
  telephone     VARCHAR(30) NOT NULL,
  address       VARCHAR(60) NOT NULL,
  email         VARCHAR(40) NOT NULL,
  busy          BOOLEAN,
  title_of_work VARCHAR(60)
);