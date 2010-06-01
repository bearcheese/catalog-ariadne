--Fill up categories table
DELETE FROM categories;
INSERT INTO categories(id, name, description) VALUES (1, 'Film', 'Egész estés mozifilmek');
INSERT INTO categories(id, name, description) VALUES (2, 'Sorozat', 'Sorozatok');
INSERT INTO categories(id, name, description) VALUES (3, 'Játék', 'Játékok');
INSERT INTO categories(id, name, description) VALUES (4, 'Zene', 'Mp3 és a többiek');
INSERT INTO categories(id, name, description) VALUES (5, 'Képek', '');
INSERT INTO categories(id, name, description) VALUES (6, 'Programok', '');
INSERT INTO categories(id, name, description) VALUES (7, 'Vegyes', '');
INSERT INTO categories(id, name, description) VALUES (8, 'Egyéb', '');

--Fill up types table
DELETE FROM types;
INSERT INTO types(id, name) VALUES (1, 'DVD');
INSERT INTO types(id, name) VALUES (2, 'CD');
INSERT INTO types(id, name) VALUES (3, 'USB');
INSERT INTO types(id, name) VALUES (4, 'HDD');
INSERT INTO types(id, name) VALUES (5, 'FDD');
INSERT INTO types(id, name) VALUES (6, 'Other');