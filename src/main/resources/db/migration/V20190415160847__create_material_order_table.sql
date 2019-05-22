CREATE TABLE material_order
(
  id_material       INTEGER NOT NULL REFERENCES material (id),
  id_provided_order INTEGER NOT NULL REFERENCES provided_order (id) ON DELETE CASCADE
);