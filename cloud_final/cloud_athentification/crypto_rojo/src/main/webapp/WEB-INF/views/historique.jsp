<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Charts / ApexCharts - NiceAdmin Bootstrap Template</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="../../assets/img/favicon.png" rel="icon">
  <link href="../../assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="../../assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="../../assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="../../assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="../../assets/vendor/quill/quill.snow.css" rel="stylesheet">
  <link href="../../assets/vendor/quill/quill.bubble.css" rel="stylesheet">
  <link href="../../assets/vendor/remixicon/remixicon.css" rel="stylesheet">
  <link href="../../assets/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="../../assets/css/style.css" rel="stylesheet">

  <!-- =======================================================
  * Template Name: NiceAdmin
  * Updated: Aug 30 2023 with Bootstrap v5.3.1
  * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.crypto.model.cryptos.Transaction" %>
<%@ page import="com.crypto.model.Utilisateur" %>
<%@ page import="com.crypto.model.cryptos.Crypto" %>

  <!-- ======= Header ======= -->
<%@ include file="header.jsp" %>
  <!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <%@ include file="sidebar.jsp" %>
 <!-- End Sidebar-->


 <main id="main" class="main">
    <!-- Filter Form -->
  <div class="card">
      <div class="card-body">
      <h5 class="card-title fw-semibold mb-4">Filtrer les Transactions</h5>
    <form action="historique" method="get" class="form-inline justify-content-center">
        <div class="form-group mx-2">
            <label for="dateDebut" class="mr-2">Date de début :</label>
            <input type="datetime-local" id="dateDebut" name="dateDebut" class="form-control">
        </div>
        <div class="form-group mx-2">
            <label for="dateFin" class="mr-2">Date de fin :</label>
            <input type="datetime-local" id="dateFin" name="dateFin" class="form-control">
        </div>
        <div class="form-group mx-2">
            <label for="utilisateur" class="mr-2">Utilisateur :</label>
            <select name="utilisateur" id="utilisateur" class="form-control">
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
        </div>
        <div class="form-group mx-2">
            <label for="crypto" class="mr-2">Crypto :</label>
            <select name="crypto" id="crypto" class="form-control">
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
        </div>
         <center>
                <button type="submit" class="btn btn-primary" style="margin-top: 20px;width:100px;">Filtrer</button>
         </center>
    </form>
    </div>
  </main>

 <main id="main" class="main">
    <!-- Transactions Table -->
    <h2 class="text-center mt-5">Liste des Transactions</h2>
     <table class="table table-bordered table-striped">
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
                             width="50" height="50" class="rounded-circle">
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
  </main>

</div>


  <!-- ======= Footer ======= -->
  <%@ include file="footer.jsp" %>
<!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="../../assets/vendor/apexcharts/apexcharts.min.js"></script>
  <script src="../../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="../../assets/vendor/chart.js/chart.umd.js"></script>
  <script src="../../assets/vendor/echarts/echarts.min.js"></script>
  <script src="../../assets/vendor/quill/quill.min.js"></script>
  <script src="../../assets/vendor/simple-datatables/simple-datatables.js"></script>
  <script src="../../assets/vendor/tinymce/tinymce.min.js"></script>
  <script src="../../assets/vendor/php-email-form/validate.js"></script>

  <!-- Template Main JS File -->
  <script src="../../assets/js/main.js"></script>

</body>

</html>

