

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
      <!-- ======= Header ======= -->
      <%@ include file="header.jsp" %>
      <!-- End Header -->
      <!-- ======= Sidebar ======= -->
      <%@ include file="sidebar.jsp" %>
      <!-- End Sidebar-->

      <div class="card">
<div class="container mt-5">
  <div class="card shadow-sm">
    <div class="card-header bg-primary text-white">
      <h2 class="text-center mb-0">Filtre</h2>
    </div>
    <div class="card-body">
      <form action="/ValiderRetrait" method="post">
        <!-- DateTime Picker -->
        <div class="mb-3">
          <label for="datetime" class="form-label">Filtrer par Date et Heure Max</label>
          <input 
            type="datetime-local" 
            id="datetime" 
            name="datetime" 
            class="form-control form-control-lg" 
            placeholder="Sélectionnez une date et une heure"
          >
        </div>
        <!-- Submit Button -->
        <div class="text-center">
          <button type="submit" class="btn btn-primary btn-lg w-50">
            Valider
          </button>
        </div>
      </form>
    </div>
  </div>
</div>

            <div class="card">
            <div class="card-body">
              <h5 class="card-title">Table </h5>

              <!-- Table with stripped rows -->
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">idUser</th>
                    <th scope="col">TotalAchat</th>
                    <th scope="col">TotalVente</th>
                    <th scope="col">Valeur portefeuille</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">1</th>
                    <td>Brandon Jacob</td>
                    <td>Designer</td>
                    <td>28</td>
                  </tr>

                </tbody>
              </table>
              <!-- End Table with stripped rows -->

            </div>
          </div>



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