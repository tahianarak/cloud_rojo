CREATE TABLE utilisateur(
   id_utilisateur serial,
   nom VARCHAR(255)  NOT NULL,
   date_ens DATE NOT NULL,
   date_naissance DATE NOT NULL,
   email VARCHAR(512)  NOT NULL,
   mdp VARCHAR(255)  NOT NULL,
   tentative_restant INTEGER NOT NULL,
   is_admin INT NOT NULL default 0,
   PRIMARY KEY(id_utilisateur)
);


CREATE TABLE session_utilisateur(
   id_session serial,
   token VARCHAR(255)  NOT NULL,
   temps_restant TIMESTAMP NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_session),
   UNIQUE(token),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE validation_inscription(
   id_validation serial,
   nom VARCHAR(255)  NOT NULL,
   date_inscription TIMESTAMP NOT NULL,
   pin VARCHAR(10)  NOT NULL,
   date_naissance DATE NOT NULL,
   email VARCHAR(512)  NOT NULL,
   mdp VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id_validation)
);

CREATE TABLE validation_authentification(
   id_valid_auth serial,
   date_auth TIMESTAMP,
   pin VARCHAR(20) ,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_valid_auth),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

--SUITE KELY
CREATE TABLE validation_unlock(
   id_valid_auth SERIAL,
   pin VARCHAR(20) ,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_valid_auth),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

INSERT INTO utilisateur ( nom, date_ens, date_naissance, email, mdp, tentative_restant,is_admin)
VALUES
( 'Randria Valisoa', '2023-01-15', '1985-07-23', 'RandriaVals2303@gmail.com', MD5('vals'), 3,1),
( 'Andria Mirindra', '2023-01-15', '1985-07-23', 'mixandria19@gmail.com', MD5('mix'), 3,0);

CREATE TABLE crypto(
   id_crypto serial,
   libelle VARCHAR(255) ,
   prix_actuelle NUMERIC(15,2)  ,
   date_update TIMESTAMP,
   PRIMARY KEY(id_crypto)
);

CREATE TABLE portefeuille(
   id_porte_feuille serial,
   entree NUMERIC(15,2)  ,
   sortie NUMERIC(15,2)  ,
   date_ens TIMESTAMP,
   id_crypto INTEGER NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_porte_feuille),
   FOREIGN KEY(id_crypto) REFERENCES crypto(id_crypto),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE depot_retrait(
   id_depot_retrait serial,
   depot NUMERIC(15,2)  ,
   retrait NUMERIC(15,2)  ,
   date_depot_retrait TIMESTAMP,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_depot_retrait),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE depot_retrait_temporaire(
   id_depot_retrait serial,
   depot NUMERIC(15,2)  ,
   retrait NUMERIC(15,2)  ,
   date_depot_retrait TIMESTAMP,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_depot_retrait),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE transaction(
   id_transaction serial,
   vente NUMERIC(15,2)  ,
   achat NUMERIC(15,2)  ,
   date_debut TIMESTAMP,
   id_crypto INTEGER NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   commission NUMERIC(15,2)  ,
   PRIMARY KEY(id_transaction),
   FOREIGN KEY(id_crypto) REFERENCES crypto(id_crypto),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);


CREATE TABLE commission
(
    id_commission serial PRIMARY KEY,
    date_ens TIMESTAMP not null,
    pourcentage  NUMERIC(5,2),
    id_crypto INTEGER not null,
    FOREIGN KEY(id_crypto) REFERENCES crypto(id_crypto),
    description VARCHAR(100)
);
--DATA
INSERT INTO utilisateur (nom, date_ens, date_naissance, email, mdp, tentative_restant,is_admin)
VALUES
('Ravelo Herisoa', '2024-01-01', '1985-01-01', 'randria@mailinator.com', MD5('1111'), 3,0),
('Ramanantsoa Tiana', '2024-01-02', '1986-02-02', 'rakoto@mailinator.com', MD5('2222'), 3,0),
('Rasoamanana Haja', '2024-01-03', '1987-03-03', 'kaloina@mailinator.com', MD5('3333'), 3,0),
('Andrianirina Voahirana', '2024-01-04', '1988-04-04', 'hedy@mailinator.com', MD5('4444'), 3,0),
('Rakotomalala Miora', '2024-01-05', '1989-05-05', 'raly@mailinator.com', MD5('5555'), 3,0),
('Andriamampianina Zo', '2024-01-06', '1990-06-06', 'valisoa@mailinator.com', MD5('6666'), 3,0),
('Razafindralambo Lova', '2024-01-07', '1991-07-07', 'rotsy@mailinator.com', MD5('7777'), 3,0),
('Rambeloson Faly', '2024-01-08', '1992-08-08', 'dany@mailinator.com', MD5('8888'), 3,0),
('Rasolofomanana Noro', '2024-01-09', '1993-09-09', 'teddy@mailinator.com', MD5('9999'), 3,0),
('Randrianarisoa Arisoa', '2024-01-10', '1994-10-10', 'rohy@mailinator.com', MD5('0000'), 3,1);

INSERT INTO crypto (libelle, prix_actuelle, date_update)
VALUES
('Bitcoin', 47000.00, NOW() - INTERVAL '3 DAYS'),
('Ethereum', 3800.00, NOW() - INTERVAL '2 DAYS'),
('Litecoin', 540.00, NOW() - INTERVAL '1 DAY'),
('Ripple', 2.10, NOW() - INTERVAL '3 DAYS'),
('Bitcoin Cash', 175.00, NOW() - INTERVAL '2 DAYS'),
('Monero', 1.20, NOW() - INTERVAL '1 DAY'),
('Dogecoin', 35.00, NOW() - INTERVAL '3 DAYS'),
('Shiba Inu', 0.30, NOW() - INTERVAL '2 DAYS'),
('Cardano', 0.00007, NOW() - INTERVAL '1 DAY'),
('Polkadot', 180.00, NOW() - INTERVAL '3 DAYS');

-- Insertion des portefeuilles
INSERT INTO portefeuille (entree, sortie, date_ens, id_crypto, id_utilisateur)
VALUES
(1000.00, 0.00, NOW() - INTERVAL '3 DAYS', 1, 1),
(1500.00, 0.00, NOW() - INTERVAL '2 DAYS', 2, 2),
(100.00, 0.00, NOW() - INTERVAL '1 DAY', 3, 3),
(150.00, 0.00, NOW() - INTERVAL '3 DAYS', 4, 4),
(75.00, 0.00, NOW() - INTERVAL '2 DAYS', 5, 5),
(200.00, 0.00, NOW() - INTERVAL '1 DAY', 6, 6),
(300.00, 0.00, NOW() - INTERVAL '3 DAYS', 7, 7),
(50.00, 0.00, NOW() - INTERVAL '2 DAYS', 8, 8),
(60.00, 0.00, NOW() - INTERVAL '1 DAY', 9, 9),
(500.00, 0.00, NOW() - INTERVAL '3 DAYS', 10, 10),

(0.00, 500.00, NOW() - INTERVAL '3 DAYS', 1, 1),
(0.00, 700.00, NOW() - INTERVAL '2 DAYS', 2, 2),
(0.00, 150.00, NOW() - INTERVAL '1 DAY', 3, 3),
(0.00, 200.00, NOW() - INTERVAL '3 DAYS', 4, 4),
(0.00, 100.00, NOW() - INTERVAL '2 DAYS', 5, 5),
(0.00, 120.00, NOW() - INTERVAL '1 DAY', 6, 6),
(0.00, 250.00, NOW() - INTERVAL '3 DAYS', 7, 7),
(0.00, 30.00, NOW() - INTERVAL '2 DAYS', 8, 8),
(0.00, 40.00, NOW() - INTERVAL '1 DAY', 9, 9),
(0.00, 400.00, NOW() - INTERVAL '3 DAYS', 10, 10);

-- Insertion des dépôts et retraits
INSERT INTO depot_retrait (depot, retrait, date_depot_retrait, id_utilisateur)
VALUES
(500.00, 0.00, NOW() - INTERVAL '3 DAYS', 1),
(700.00, 0.00, NOW() - INTERVAL '2 DAYS', 2),
(150.00, 0.00, NOW() - INTERVAL '1 DAY', 3),
(200.00, 0.00, NOW() - INTERVAL '3 DAYS', 4),
(100.00, 0.00, NOW() - INTERVAL '2 DAYS', 5),
(120.00, 0.00, NOW() - INTERVAL '1 DAY', 6),
(250.00, 0.00, NOW() - INTERVAL '3 DAYS', 7),
(30.00, 0.00, NOW() - INTERVAL '2 DAYS', 8),
(40.00, 0.00, NOW() - INTERVAL '1 DAY', 9),
(400.00, 0.00, NOW() - INTERVAL '3 DAYS', 10),

(0.00, 500.00, NOW() - INTERVAL '3 DAYS', 1),
(0.00, 700.00, NOW() - INTERVAL '2 DAYS', 2),
(0.00, 150.00, NOW() - INTERVAL '1 DAY', 3),
(0.00, 200.00, NOW() - INTERVAL '3 DAYS', 4),
(0.00, 100.00, NOW() - INTERVAL '2 DAYS', 5),
(0.00, 120.00, NOW() - INTERVAL '1 DAY', 6),
(0.00, 250.00, NOW() - INTERVAL '3 DAYS', 7),
(0.00, 30.00, NOW() - INTERVAL '2 DAYS', 8),
(0.00, 40.00, NOW() - INTERVAL '1 DAY', 9),
(0.00, 400.00, NOW() - INTERVAL '3 DAYS', 10);

-- Ventes
INSERT INTO transaction (vente, achat, date_debut, id_crypto, id_utilisateur)
VALUES
(47000.00, 0.00, NOW() - INTERVAL '3 DAYS', 1, 1),
(3800.00, 0.00, NOW() - INTERVAL '2 DAYS', 2, 2),
(540.00, 0.00, NOW() - INTERVAL '1 DAY', 3, 3),
(2.10, 0.00, NOW() - INTERVAL '3 DAYS', 4, 4),
(175.00, 0.00, NOW() - INTERVAL '2 DAYS', 5, 5),
(1.20, 0.00, NOW() - INTERVAL '1 DAY', 6, 6),
(35.00, 0.00, NOW() - INTERVAL '3 DAYS', 7, 7),
(0.30, 0.00, NOW() - INTERVAL '2 DAYS', 8, 8),
(0.00007, 0.00, NOW() - INTERVAL '1 DAY', 9, 9),
(180.00, 0.00, NOW() - INTERVAL '3 DAYS', 10, 10);

-- Achats
INSERT INTO transaction (vente, achat, date_debut, id_crypto, id_utilisateur)
VALUES
(0.00, 46000.00, NOW() - INTERVAL '3 DAYS', 1, 1),
(0.00, 3700.00, NOW() - INTERVAL '2 DAYS', 2, 2),
(0.00, 530.00, NOW() - INTERVAL '1 DAY', 3, 3),
(0.00, 2.00, NOW() - INTERVAL '3 DAYS', 4, 4),
(0.00, 170.00, NOW() - INTERVAL '2 DAYS', 5, 5),
(0.00, 1.15, NOW() - INTERVAL '1 DAY', 6, 6),
(0.00, 34.00, NOW() - INTERVAL '3 DAYS', 7, 7),
(0.00, 0.28, NOW() - INTERVAL '2 DAYS', 8, 8),
(0.00, 0.00006, NOW() - INTERVAL '1 DAY', 9, 9),
(0.00, 175.00, NOW() - INTERVAL '3 DAYS', 10, 10);

INSERT INTO commission (date_ens, pourcentage, id_crypto, description)
VALUES
    ('2025-01-27 08:32:11.456000', 15.67, 1, 'Commission for Bitcoin'),
    ('2025-01-27 08:32:11.457000', 12.34, 2, 'Commission for Ethereum'),
    ('2025-01-27 08:32:11.458000', 8.45, 3, 'Commission for Litecoin'),
    ('2025-01-27 08:32:11.459000', 18.21, 4, 'Commission for Ripple'),
    ('2025-01-27 08:32:11.460000', 10.75, 5, 'Commission for Bitcoin Cash'),
    ('2025-01-27 08:32:11.461000', 9.87, 6, 'Commission for Monero'),
    ('2025-01-27 08:32:11.462000', 14.32, 7, 'Commission for Dogecoin'),
    ('2025-01-27 08:32:11.463000', 13.50, 8, 'Commission for Shiba Inu'),
    ('2025-01-27 08:32:11.464000', 7.45, 9, 'Commission for Cardano'),
    ('2025-01-27 08:32:11.465000', 19.88, 10, 'Commission for Polkadot');

