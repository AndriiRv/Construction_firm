CREATE TABLE instrument_order
(
  id_instrument     INTEGER NOT NULL REFERENCES instrument (id),
  id_provided_order INTEGER NOT NULL REFERENCES provided_order (id) ON DELETE CASCADE
);