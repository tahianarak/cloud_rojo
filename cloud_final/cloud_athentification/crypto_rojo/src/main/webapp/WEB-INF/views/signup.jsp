<%-- <form action="inscrire" method="post" > 

<input type="email" name="email" required="required" >
<input type="text" name="nom" required="required" >
<input type="date" name="date_naissance" required="required" >
<input type="password" name="mdp" required="required" >

<input type="submit" value="valider"> 

</form>   --%>



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


 <!-- End Sidebar-->



<div class="container">
  <div class="row justify-content-center">
    <div class="col-lg-4 col-md-2  flex-column align-items-center justify-content-center">
      
      <div class="d-flex justify-content-center py-4">
        <a href="index.html" class="logo d-flex align-items-center w-auto">
          <img src="assets/img/logo.png" alt="">
          <span class="d-none d-lg-block text-light">Crypto</span>
        </a>
      </div><!-- End Logo -->
      <div class="card mb-6" style="border: 1px solid #2e3b31; background-color: #1b1b1b; color: #ffffff;">
        <div class="card-body">
          <div class="pt-4 pb-2">
            <h5 class="card-title text-center pb-0 fs-4 text-light">Inscription</h5>
          </div>

              <form action="inscrire" method="post" >
                  <div class="mb-3">
                      <label for="email" class="form-label">Email</label>
                      <input type="email" name="email" id="email" class="form-control" required>
                  </div>
                  <div class="mb-3">
                      <label for="nom" class="form-label">Nom</label>
                      <input type="text" name="nom" id="nom" class="form-control" required>
                  </div>
                  <div class="mb-3">
                      <label for="date_naissance" class="form-label">Date de naissance</label>
                      <input type="date" name="date_naissance" id="date_naissance" class="form-control" required>
                  </div>
                  <div class="mb-3">
                      <label for="mdp" class="form-label">Mot de passe</label>
                      <input type="password" name="mdp" id="mdp" class="form-control" required>
                  </div>
                  <div class="d-grid gap-2">
                      <input type="submit" value="Valider" class="btn btn-primary">
                  </div>
              </form>     

        </div>
      </div>
  <center>
      <div class="credits text-light mt-3">
        Designed by <a href="https://bootstrapmade.com/" class="text-success">Andria</a>
      </div>
  </center>
    </div>
  </div>
</div>




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