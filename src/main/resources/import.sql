INSERT INTO tb_person (first_name, last_name, cpf, birth_date) VALUES ('Maria', 'Nogueira', '18244581344', '1989-07-03');
INSERT INTO tb_person (first_name, last_name, cpf, birth_date) VALUES ('Marcos', 'Pereira', '72408903092', '1999-07-21');
INSERT INTO tb_person (first_name, last_name, cpf, birth_date) VALUES ('Ronaldo', 'Silva', '55884844794', '1974-11-12');

INSERT INTO tb_phone (number, type) VALUES ('(88) 89999-9999', 'HOME');
INSERT INTO tb_phone (number, type) VALUES ('(77) 96666-9999', 'MOBILE');
INSERT INTO tb_phone (number, type) VALUES ('(55) 85555-9333', 'MOBILE');
INSERT INTO tb_phone (number, type) VALUES ('(96) 82222-4899', 'COMMERCIAL');

INSERT INTO tb_person_phone (person_id, phone_id) VALUES (1, 1);
INSERT INTO tb_person_phone (person_id, phone_id) VALUES (1, 2);
INSERT INTO tb_person_phone (person_id, phone_id) VALUES (2, 3);
INSERT INTO tb_person_phone (person_id, phone_id) VALUES (3, 4);
