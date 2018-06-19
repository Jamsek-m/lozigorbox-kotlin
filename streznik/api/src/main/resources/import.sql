/*stevilo poskusov pred zaklepom*/
INSERT INTO nastavitev(id, kljuc, vrednost) VALUES (1, 'zaklep.poskusi', '5');
/*cas zaklepa po stevilu neuspesnih poskusih*/
INSERT INTO nastavitev(id, kljuc, vrednost) VALUES (2, 'zaklep.trajanje', '300');
/*trajanje veljavnosti JWT zetona*/
INSERT INTO nastavitev(id, kljuc, vrednost) VALUES (3, 'jwt.veljavnost', '300');
INSERT INTO nastavitev(id, kljuc, vrednost) VALUES (4, 'jwt.header', 'lozigorbox');
INSERT INTO nastavitev(id, kljuc, vrednost) VALUES (5, 'jwt.secret', 'PERicaREZeRAciRep');

INSERT INTO vloga(id, sifra, naziv) VALUES (1, 'ADMIN', 'Admin');
INSERT INTO vloga(id, sifra, naziv) VALUES (2, 'MOD', 'Moderator');
INSERT INTO vloga(id, sifra, naziv) VALUES (3, 'USER', 'Uporabnik');

INSERT INTO uporabniski_status(id, naziv, sifra) VALUES (1, 'Aktiven', 'AKTIVEN');
INSERT INTO uporabniski_status(id, naziv, sifra) VALUES (2, 'Neaktiven', 'NEAKTIVEN');

INSERT INTO uporabnik(id, email, geslo, ime, status_id) VALUES (1, 'test@mail.com', '$2a$10$eA0HCy8wDbdw1SpHMgdm0uWbYLp9STg9WML6yAwxkn9Ws9SDT.QiS', 'Miha', 1);

INSERT INTO uporabniske_vloge(uporabnik_id, vloga_id) VALUES (1, 1);
INSERT INTO uporabniske_vloge(uporabnik_id, vloga_id) VALUES (1, 2);
INSERT INTO uporabniske_vloge(uporabnik_id, vloga_id) VALUES (1, 3);