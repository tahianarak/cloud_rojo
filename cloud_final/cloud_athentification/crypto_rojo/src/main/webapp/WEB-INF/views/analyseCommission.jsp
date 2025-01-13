<%@ page import="com.crypto.model.cryptos.Crypto, java.util.List" %>
<%@ page import="com.crypto.model.*" %>


<%
    
    List<Commission> commissions = (List<Commission>) request.getAttribute("commissions");
    List<Crypto> cryptos = (List<Crypto>) request.getAttribute("cryptos");
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>Analyse des Commissions</title>

  <!-- Vendor CSS Files -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background-color: #f7f7f7;
    }
    .form-container {
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      padding: 30px;
      margin-top: 50px;
    }
    .form-container h2 {
      text-align: center;
      margin-bottom: 20px;
    }

    /* Align les filtres sur une même ligne */
    .form-row {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
    }
    .form-row .col {
      flex: 1;
    }

    /* Amélioration de la table */
    table {
      width: 100%;
      margin-top: 30px;
      border-collapse: collapse;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }
    table, th, td {
      border: 1px solid #ddd;
    }
    th, td {
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f8f9fa;
      color: #333;
    }
    tr:nth-child(even) {
      background-color: #f2f2f2;
    }
    tr:hover {
      background-color: #f1f1f1;
    }

    /* Bouton valider centré */
    .submit-btn-container {
      display: flex;
      justify-content: center;
      margin-top: 20px;
    }
  </style>
</head>

<body>

  <!-- Main Content -->
  <main class="container">
    <div class="form-container">
      <h2>Analyse des Commissions</h2>

      <form action="resultats" method="get">

        <!-- Filtres sur une même ligne -->
        <div class="form-row">
          <!-- Type Analyse -->
          <div class="col">
            <label for="type-analyse" class="form-label">Type d'analyse</label>
            <select class="form-select" id="type-analyse" name="type_analyse" required>
              <option value="0">Somme</option>
              <option value="1">Moyenne</option>
            </select>
          </div>

          <!-- Crypto Selection -->
          <div class="col">
            <label for="crypto" class="form-label">Crypto</label>
            <select class="form-select" id="crypto" name="crypto" required>
              <option value="-1">Tous</option>
              <% for(Crypto crypto : cryptos) { %>
                <option value="<%= crypto.getIdCrypto() %>"><%= crypto.getLibelle() %></option>
              <% } %>
            </select>
          </div>

          <!-- Date and Time Min -->
          <div class="col">
            <label for="date-min" class="form-label">Date et Heure Min</label>
            <input type="datetime-local" id="date-min" name="date_min" class="form-control" required>
          </div>

          <!-- Date and Time Max -->
          <div class="col">
            <label for="date-max" class="form-label">Date et Heure Max</label>
            <input type="datetime-local" id="date-max" name="date_max" class="form-control" required>
          </div>
        </div>

        <!-- Submit Button -->
        <div class="submit-btn-container">
          <button type="submit" class="btn btn-primary">Valider</button>
        </div>
      </form>
    </div>
  </main>

  <!-- Results Table -->
  <div class="container mt-5">
    <h3>Résultats de l'analyse</h3>
    <table>
      <thead>
        <tr>
          <th>Commission</th>
        </tr>
      </thead>
      <tbody>
        <% if (commissions != null && !commissions.isEmpty()) { %>
          <% for (Commission commission : commissions) { %>
            <tr>
              <td><%= commission.getValeurCommission() %></td>
            </tr>
          <% } %>
        <% } else { %>
          <tr>
            <td colspan="1">Aucune commission trouvée.</td>
          </tr>
        <% } %>
      </tbody>
    </table>
  </div>

  <!-- Vendor JS Files -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
