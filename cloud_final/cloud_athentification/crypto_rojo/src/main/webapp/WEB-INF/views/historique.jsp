<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.crypto.model.cryptos.Transaction" %>
<%@ page import="com.crypto.model.Utilisateur" %>
<%@ page import="com.crypto.model.cryptos.Crypto" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Historique des Transactions</title>
</head>
<body>

<h2>Filtrer les Transactions</h2>
<form action="historique" method="get">
    <label for="dateDebut">Date de début :</label>
    <input type="datetime-local" id="dateDebut" name="dateDebut">

    <label for="dateFin">Date de fin :</label>
    <input type="datetime-local" id="dateFin" name="dateFin">

    <label for="utilisateur">Utilisateur :</label>
    <select name="utilisateur" id="utilisateur">
        <option value="">Tous</option>
        <%
            List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
            if (utilisateurs != null) {
                for (Utilisateur user : utilisateurs) {
        %>
        <option value="<%= user.getIdUtilisateur() %>"><%= user.getNom() %></option>
        <%
                }
            }
        %>
    </select>

    <label for="crypto">Crypto :</label>
    <select name="crypto" id="crypto">
        <option value="">Tous</option>
        <%
            List<Crypto> cryptos = (List<Crypto>) request.getAttribute("cryptos");
            if (cryptos != null) {
                for (Crypto crypto : cryptos) {
        %>
        <option value="<%= crypto.getIdCrypto() %>"><%= crypto.getLibelle() %></option>
        <%
                }
            }
        %>
    </select>

    <button type="submit">Filtrer</button>
</form>

<h2>Liste des Transactions</h2>
<table border="1">
    <thead>
    <tr>
        <th>Profil Utilisateur</th>
        <th>Nom Utilisateur</th>
        <th>Date</th>
        <th>Crypto</th>
        <th>Vente</th>
        <th>Achat</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
        if (transactions != null && !transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
    %>
    <tr>
        <td>
            <a href="historique?utilisateur=<%= transaction.getUtilisateur().getIdUtilisateur() %>">
                <img src="image/<%= transaction.getUtilisateur().getPhotoProfil() %>"
                     alt="Photo de <%= transaction.getUtilisateur().getNom() %>"
                     width="50" height="50">
            </a>
        </td>
        <td><%= transaction.getUtilisateur().getNom() %></td>
        <td><%= transaction.getDateDebut() %></td>
        <td><%= transaction.getCrypto().getLibelle() %></td>
        <td><%= transaction.getVente() %></td>
        <td><%= transaction.getAchat() %></td>
    </tr>
    <%
            }
        } else {
    %>
    <tr>
        <td colspan="6">Aucune transaction trouvée.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
