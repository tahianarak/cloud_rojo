<%@ page import="com.crypto.model.cryptos.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="com.crypto.model.crypto.Cryptos" %>
<%@ page import="com.crypto.model.Utilisateur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique des Transactions</title>
</head>
<body>
<h2>Filtrer les transactions</h2>
<form method="GET" action="/historique">
    <label for="dateDebut">Date de début :</label>
    <input type="datetime-local" id="dateDebut" name="dateDebut" value="<%= request.getParameter("dateDebut") %>">

    <label for="dateFin">Date de fin :</label>
    <input type="datetime-local" id="dateFin" name="dateFin" value="<%= request.getParameter("dateFin") %>">

    <label for="crypto">Crypto :</label>
    <select name="crypto" id="crypto">
        <option value="">Tous</option>
        <%
            for (Cryptos crypto : (List<Cryptos>) request.getAttribute("cryptos")) {
        %>
        <option value="<%= crypto.getIdCrypto() %>" <%= request.getParameter("crypto") != null && request.getParameter("crypto").equals(String.valueOf(crypto.getIdCrypto())) ? "selected" : "" %>>
            <%= crypto.getLibelle() %>
        </option>
        <%
            }
        %>
    </select>

    <label for="utilisateur">Utilisateur :</label>
    <select name="utilisateur" id="utilisateur">
        <option value="">Tous</option>
        <%
            for (Utilisateur utilisateur : (List<Utilisateur>) request.getAttribute("utilisateurs")) {
        %>
        <option value="<%= utilisateur.getIdUtilisateur() %>" <%= request.getParameter("utilisateur") != null && request.getParameter("utilisateur").equals(String.valueOf(utilisateur.getIdUtilisateur())) ? "selected" : "" %>>
            <%= utilisateur.getNom() %>
        </option>
        <%
            }
        %>
    </select>

    <button type="submit">Filtrer</button>
</form>

<!-- Tableau des transactions -->
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
    <tbody>
    <%
        for (Transaction transaction : (List<Transaction>) request.getAttribute("transactions")) {
    %>
    <tr>
        <td>
            <a href="/historique?utilisateur=<%= transaction.getUtilisateur().getIdUtilisateur() %>">
                <img src="image/<%= transaction.getUtilisateur().getPhotoProfil() %>" alt="Photo de Profil de  <%= transaction.getUtilisateur().getNom() %>" width="50" height="50">
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
    %>
    </tbody>
</table>
</body>
</html>
