CREATE TABLE feedback
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(30),
  text VARCHAR(200),
  date VARCHAR(20),
  rank INTEGER
);