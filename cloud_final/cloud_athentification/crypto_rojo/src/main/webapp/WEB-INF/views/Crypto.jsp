<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Cryptos</title>

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

    <!-- External JS Files -->
    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/js/crypto.js"></script>
</head>
<body>
    <!-- ======= Header ======= -->
    <%@ include file="header.jsp" %>
    <!-- End Header -->

    <!-- ======= Sidebar ======= -->
    <%@ include file="sidebar.jsp" %>
    <!-- End Sidebar -->

    <main id="main" class="main">

        <h1>Liste des Cryptos</h1>

        <!-- Tableau des cryptos -->
        <table id="cryptoTable" class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prix</th>
                    <th>Date de Mise à Jour</th>
                    <th>Graphique</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>

        <!-- Graphique -->
        <div class="card">
            <canvas id="cryptoChart" width="400" height="200"></canvas>
        </div>

    </main><!-- End #main -->

    <!-- ======= Footer ======= -->
    <%@ include file="footer.jsp" %>
    <!-- End Footer -->

    <!-- Back to Top Button -->
    <a href="#" class="back-to-top d-flex align-items-center justify-content-center">
        <i class="bi bi-arrow-up-short"></i>
    </a>

    <!-- Vendor JS Files -->
    <script src="../../assets/vendor/apexcharts/apexcharts.min.js"></script>
    <script src="../../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="../../assets/vendor/chart.js/chart.umd.js"></script>
    <script src="../../assets/vendor/echarts/echarts.min.js"></script>
    <script src="../../assets/vendor/quill/quill.min.js"></script>
    <script src="../../assets/vendor/simple-datatables/simple-datatables.js"></script>
    <script src="../../assets/vendor/php-email-form/validate.js"></script>

    <!-- Template Main JS File -->
    <script src="../../assets/js/main.js"></script>

</body>
</html>
