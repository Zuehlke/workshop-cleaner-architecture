CREATE TABLE product_category (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(60));
CREATE TABLE product (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(60), summary VARCHAR(400), details VARCHAR(1000), category_id INT NOT NULL, CONSTRAINT fk_product FOREIGN KEY (category_id) REFERENCES product_category(id));
CREATE TABLE product_specification (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, description VARCHAR(1000), product_id INT NOT NULL, CONSTRAINT fk_product_specification FOREIGN KEY (product_id) REFERENCES product(id));
CREATE TABLE product_image (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, image CLOB, is_main BOOLEAN, product_id INT NOT NULL, CONSTRAINT fk_product_image FOREIGN KEY (product_id) REFERENCES product(id));

INSERT INTO product_category (id, name) VALUES (1, 'Bike');

INSERT INTO product (id, name, summary, details, category_id) VALUES (1, 'X-Bike H-XR', 'This is X-Bike H-XR...', 'The X-Bike H-XR in details...', 1);
INSERT INTO product (id, name, summary, details, category_id) VALUES (2, 'Velocity D-1', 'This is Velocity D-1...', 'The Velocity D-1 in details...', 1);

INSERT INTO product_specification (id, description, product_id) VALUES (1, '', 1);
INSERT INTO product_specification (id, description, product_id) VALUES (2, '', 2);

INSERT INTO product_image (id, image, is_main, product_id) VALUES (1, FILE_READ('classpath:/bike.png'), true, 1);
INSERT INTO product_image (id, image, is_main, product_id) VALUES (2, FILE_READ('classpath:/bike.png'), true, 2);

