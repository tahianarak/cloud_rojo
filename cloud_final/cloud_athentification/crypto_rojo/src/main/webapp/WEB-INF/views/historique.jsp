<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique des Transactions</title>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            loadUtilisateurs();
            loadCryptos();
            loadTransactions();

            document.getElementById("filterForm").addEventListener("submit", function (event) {
                event.preventDefault();
                loadTransactions();
            });
        });

        async function loadUtilisateurs() {
            try {
                let response = await fetch("/api/historique/utilisateurs");
                let utilisateurs = await response.json();
                let select = document.getElementById("utilisateur");

                utilisateurs.forEach(user => {
                    let option = document.createElement("option");
                    option.value = user.idUtilisateur;
                    option.textContent = user.nom;
                    select.appendChild(option);
                });
            } catch (error) {
                console.error("Erreur lors du chargement des utilisateurs", error);
            }
        }

        async function loadCryptos() {
            try {
                let response = await fetch("/api/historique/cryptos");
                let cryptos = await response.json();
                let select = document.getElementById("crypto");

                cryptos.forEach(crypto => {
                    let option = document.createElement("option");
                    option.value = crypto.idCrypto;
                    option.textContent = crypto.libelle;
                    select.appendChild(option);
                });
            } catch (error) {
                console.error("Erreur lors du chargement des cryptos", error);
            }
        }

        async function loadTransactions() {
            let dateDebut = document.getElementById("dateDebut").value;
            let dateFin = document.getElementById("dateFin").value;
            let crypto = document.getElementById("crypto").value;
            let utilisateur = document.getElementById("utilisateur").value;

            let url = `/api/historique?`;
            if (dateDebut) url += `dateDebut=${dateDebut}&`;
            if (dateFin) url += `dateFin=${dateFin}&`;
            if (crypto) url += `crypto=${crypto}&`;
            if (utilisateur) url += `utilisateur=${utilisateur}`;

            try {
                let response = await fetch(url);
                let transactions = await response.json();
                let tbody = document.getElementById("transactionBody");
                tbody.innerHTML = "";

                transactions.forEach(transaction => {
                    let tr = document.createElement("tr");

                    tr.innerHTML = `
                        <td>
                            <a href="#" onclick="filterByUser(${transaction.utilisateur.idUtilisateur})">
                                <img src="image/${transaction.utilisateur.photoProfil}" alt="Photo de ${transaction.utilisateur.nom}" width="50" height="50">
                            </a>
                        </td>
                        <td>${transaction.utilisateur.nom}</td>
                        <td>${transaction.dateDebut}</td>
                        <td>${transaction.crypto.libelle}</td>
                        <td>${transaction.vente}</td>
                        <td>${transaction.achat}</td>
                    `;

                    tbody.appendChild(tr);
                });

            } catch (error) {
                console.error("Erreur lors du chargement des transactions", error);
            }
        }

        function filterByUser(userId) {
            document.getElementById("utilisateur").value = userId;
            loadTransactions();
        }
    </script>
</head>
<body>
<form id="filterForm">
    <label for="dateDebut">Date de début :</label>
    <input type="datetime-local" id="dateDebut" name="dateDebut">

    <label for="dateFin">Date de fin :</label>
    <input type="datetime-local" id="dateFin" name="dateFin">

    <label for="crypto">Crypto :</label>
    <select name="crypto" id="crypto">
        <option value="">Tous</option>
    </select>

    <label for="utilisateur">Utilisateur :</label>
    <select name="utilisateur" id="utilisateur">
        <option value="">Tous</option>
    </select>

    <button type="submit">Filtrer</button>
</form>

<h2>Liste des Transactions</h2>
<table border="1">
    <thead>
    <tr>
        <th>Photo</th>
        <th>Nom Utilisateur</th>
        <th>Date</th>
        <th>Crypto</th>
        <th>Vente</th>
        <th>Achat</th>
    </tr>
    </thead>
    <tbody id="transactionBody">
    </tbody>
</table>
</body>
</html>
