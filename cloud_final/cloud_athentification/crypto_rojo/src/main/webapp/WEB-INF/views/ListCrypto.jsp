<table class="table table-striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>Libellé</th>
            <th>Prix Actuel</th>
            <th>Date de Mise à Jour</th>
        </tr>
    </thead>
    <tbody>
        <%@ page import="com.cryptos.model.Crypto" %>
        <%@ page import="java.util.List" %>

        <% 
            List<Crypto> cryptos = (List<Crypto>) request.getAttribute("cryptos"); 
            if (cryptos != null && !cryptos.isEmpty()) {
                for (Crypto cryptos : cryptos) {
        %>
        <tr>
            <td><%= cryptos.getIdCrypto() %></td>
            <td><%= cryptos.getLibelle() %></td>
            <td><%= cryptos.getPrixActuelle() %></td>
            <td><%= cryptos.getDateUpdate() %></td>
        </tr>
        <% 
                } 
            } else { 
        %>
        <tr>
            <td colspan="4" style="text-align: center;">Aucune cryptos disponible</td>
        </tr>
        <% } %>
    </tbody>
</table>
