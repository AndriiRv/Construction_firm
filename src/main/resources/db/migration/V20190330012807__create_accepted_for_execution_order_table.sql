CREATE TABLE accepted_for_execution_order
(
  id          SERIAL PRIMARY KEY,
  id_order    INTEGER     NOT NULL REFERENCES provided_order (id),
  id_customer INTEGER     NOT NULL REFERENCES customer (id),
  title       VARCHAR(60) NOT NULL,
  done        BOOLEAN
);