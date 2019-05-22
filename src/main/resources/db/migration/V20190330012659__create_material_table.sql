CREATE TABLE material
(
  id               SERIAL PRIMARY KEY,
  title            VARCHAR(60) NOT NULL,
  purchase_price   FLOAT       NOT NULL,
  date_of_purchase DATE        NOT NULL
);