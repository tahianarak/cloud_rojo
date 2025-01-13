
-- Insertion dans la table departement
INSERT INTO departement (id, libelle) VALUES
(nextval('s_departement'), 'Achats'),
(nextval('s_departement'), 'Ventes'),
(nextval('s_departement'), 'Logistique');

-- Insertion dans la table employe
INSERT INTO employe (id, libelle, status) VALUES
(nextval('s_employe'), 'Jean Dupont', 1),
(nextval('s_employe'), 'Marie Martin', 1),
(nextval('s_employe'), 'Pierre Durand', 1);

-- Insertion dans la table emp_dept
INSERT INTO emp_dept (id, id_departement, id_employe) VALUES
(nextval('s_emp_dept'), 1, 1), -- Jean Dupont dans Achats
(nextval('s_emp_dept'), 2, 2), -- Marie Martin dans Ventes
(nextval('s_emp_dept'), 3, 3); -- Pierre Durand dans Logistique

-- Insertion dans la table fournisseur
INSERT INTO fournisseur (id, libelle) VALUES
(nextval('s_fournisseur'), 'Fourniture Industrielle'),
(nextval('s_fournisseur'), 'Quincaillerie Nationale'),
(nextval('s_fournisseur'), 'Materiel Pro');

-- Insertion dans la table produit
INSERT INTO produit (id, libelle, unite) VALUES
(nextval('s_produit'), 'Vis à bois', 'boîte'),
(nextval('s_produit'), 'Marteau', 'unité'),
(nextval('s_produit'), 'Tournevis', 'unité');

-- Insertion dans la table demande_achat_dept
INSERT INTO demande_achat_dept (id, date_demande, id_departement, id_produit) VALUES
(nextval('s_demande_achat_dept'), '2024-10-01', 1, 1), -- Demande de vis à bois pour Achats
(nextval('s_demande_achat_dept'), '2024-10-02', 3, 2); -- Demande de marteau pour Logistique

-- Insertion dans la table achat_proforma
INSERT INTO achat_proforma (id, prix_unitaire, id_demande_achat_dept, id_fournisseur) VALUES
(nextval('s_achat_proforma'), 25.50, 1, 1), -- Fourniture Industrielle pour vis à bois
(nextval('s_achat_proforma'), 12.75, 2, 2); -- Quincaillerie Nationale pour marteau

-- Insertion dans la table bon_commande
INSERT INTO bon_commande (id, date_validation, quantite, id_achat_proforma) VALUES
(nextval('s_bon_commande'), '2024-10-05', 100, 1), -- 100 boîtes de vis à bois
(nextval('s_bon_commande'), '2024-10-06', 50, 2); -- 50 marteaux

-- Insertion dans la table bon_reception
INSERT INTO bon_reception (id, date_livraison, id_bon_commande) VALUES
(nextval('s_bon_reception'), '2024-10-10', 1), -- Réception de vis à bois
(nextval('s_bon_reception'), '2024-10-11', 2); -- Réception de marteaux

-- Insertion dans la table client
INSERT INTO client (id, libelle) VALUES
(nextval('s_client'), 'Construction Pro'),
(nextval('s_client'), 'Bricolage Maison');

-- Insertion dans la table demande_client
INSERT INTO demande_client (id, date_demande, id_client, id_produit) VALUES
(nextval('s_demande_client'), '2024-10-12', 1, 1), -- Demande de vis à bois par Construction Pro
(nextval('s_demande_client'), '2024-10-13', 2, 2); -- Demande de marteaux par Bricolage Maison

-- Insertion dans la table proforma_vente
INSERT INTO proforma_vente (id, prix_unitaire, id_demande_client) VALUES
(nextval('s_proforma_vente'), 30.00, 1), -- Vente de vis à bois
(nextval('s_proforma_vente'), 15.00, 2); -- Vente de marteaux

-- Insertion dans la table bon_commande_client
INSERT INTO bon_commande_client (id, date, quantite, id_proforma_vente) VALUES
(nextval('s_bon_commande_client'), '2024-10-14', 200, 1), -- Commande de vis à bois
(nextval('s_bon_commande_client'), '2024-10-15', 100, 2); -- Commande de marteaux

-- Insertion dans la table bon_sortie
INSERT INTO bon_sortie (id, date_sortie, id_bon_commande_client) VALUES
(nextval('s_bon_sortie'), '2024-10-16', 1), -- Sortie des vis à bois
(nextval('s_bon_sortie'), '2024-10-17', 2); -- Sortie des marteaux

-- Insertion dans la table bon_livraison
INSERT INTO bon_livraison (id, date, id_bon_sortie) VALUES
(nextval('s_bon_livraison'), '2024-10-18', 1), -- Livraison de vis à bois
(nextval('s_bon_livraison'), '2024-10-19', 2); -- Livraison de marteaux

-- Insertion dans la table mvt_stock
INSERT INTO mvt_stock (id, quantite, id_produit, entree, sortie) VALUES
(nextval('s_mvt_stock'), 100, 1, 100.00, NULL), -- Entrée en stock de vis à bois
(nextval('s_mvt_stock'), 50, 2, 50.00, NULL), -- Entrée en stock de marteaux
(nextval('s_mvt_stock'), 200, 1, NULL, 200.00), -- Sortie de vis à bois pour livraison
(nextval('s_mvt_stock'), 100, 2, NULL, 100.00); -- Sortie de marteaux pour livraison


CREATE OR REPLACE VIEW check_emp AS SELECT * FROM Employe where password = "123" ; 