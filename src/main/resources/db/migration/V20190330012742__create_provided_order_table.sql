CREATE TABLE provided_order
(
  id            SERIAL PRIMARY KEY,
  id_order      INTEGER     NOT NULL REFERENCES "order" (id),
  id_customer   INTEGER     NOT NULL REFERENCES customer (id),
  id_worker     INTEGER REFERENCES worker (id),
  id_material   INTEGER REFERENCES material (id),
  id_instrument INTEGER REFERENCES instrument (id),
  date_of_order DATE        NOT NULL,
  address       VARCHAR(60) NOT NULL,
  price         FLOAT       NOT NULL
);