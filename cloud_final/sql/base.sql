CREATE TABLE utilisateur(
   id_utilisateur serial,
   nom VARCHAR(255)  NOT NULL,
   date_ens DATE NOT NULL,
   date_naissance DATE NOT NULL,
   email VARCHAR(512)  NOT NULL,
   mdp VARCHAR(255)  NOT NULL,
   tentative_restant INTEGER NOT NULL,
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

INSERT INTO utilisateur ( nom, date_ens, date_naissance, email, mdp, tentative_restant)
VALUES
( 'Randria Valisoa', '2023-01-15', '1985-07-23', 'RandriaVals2303@gmail.com', MD5('vals'),3),
( 'Andria Mirindra', '2023-01-15', '1985-07-23', 'mixandria19@gmail.com', MD5('mix'), 3);