DROP TABLE IF EXISTS order_table;
 
CREATE TABLE order_table (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  payload VARCHAR(250) NOT NULL,
  tid VARCHAR(250) NOT NULL
);