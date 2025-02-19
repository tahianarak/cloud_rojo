<%@ page import="java.util.List" %>
<%@ page import="com.crypto.model.cryptos.Transaction" %>
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
<%
    List<Transaction> transactionList = (List<Transaction>) request.getAttribute("transaction_list");
%>
<body>
<main id="main" class="main">
    <section class="section">
<form method="post" action="/filtreMoneyByDate"  class="container mt-5 p-4 border rounded bg-light shadow">
    <div class="mb-3">
        <label for="date" class="form-label">Date Max :</label>
        <input type="datetime-local" id="date" name="date" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-success">submit</button>
</form>
<hr>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Utilisateur</th>
        <th>Achat</th>
        <th>Vente</th>
        <th>Protefeuille monetaire</th>
    </tr>
    </thead>
    <tbody>
        <% if(transactionList != null) { for (Transaction transaction : transactionList){%>
            <tr>
                <td><%= transaction.getUtilisateur().getNom() %></td>
                <td><%= transaction.getAchat() %></td>
                <td><%= transaction.getVente() %></td>
                <td><%= transaction.getArgent() %></td>
            </tr>
        <%}}%>
    </tbody>
</table>
    </section>
</main>
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