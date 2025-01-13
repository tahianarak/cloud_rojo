<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Cryptos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="/assets/js/jquery.min.js"></script>

      <!-- Vendor JS Files -->
    <script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="assets/vendor/chart.js/chart.umd.js"></script>
    <script src="assets/vendor/echarts/echarts.min.js"></script>
    <script src="assets/vendor/quill/quill.min.js"></script>
    <script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
    <script src="assets/vendor/php-email-form/validate.js"></script>
    <script src="/assets/js/crypto.js"></script>
</head>
<body>
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
</body>
</html>
