

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

<%@ page import="com.crypto.model.*" %>
<%@ page import="java.util.List" %>
 <!-- End Sidebar-->



  <main id="main" class="main">
  <form action="/ValiderRetrait" method="post"> 
    <div class="container mt-5">
        <h2 class="text-center">Liste retrait invalide</h2>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>id</th>
                    <th>Date retrait </th>
                    <th>Retrait</th>
                    <th>Utilisateur</th>
                    <th>Validation</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Supposons que 'depotRetraitTemporaire' soit un objet de type DepotRetraitTemporaire passé à la vue
                    List<DepotRetraitTemporaire> depotRetraitTemporaires = (List<DepotRetraitTemporaire>) request.getAttribute("retraits");
                %>
                <tr>
                
                <% 
                  if (depotRetraitTemporaires != null && !depotRetraitTemporaires.isEmpty()) {  
                      for (DepotRetraitTemporaire depotRetraitTemporaire : depotRetraitTemporaires ) { %> 
                          <td><%= depotRetraitTemporaire.getIdDepotRetrait() %></td>
                          <td><%= depotRetraitTemporaire.getDateDepotRetrait() %></td>
                          <td><%= depotRetraitTemporaire.getRetrait() %></td>
                          <td><%= depotRetraitTemporaire.getUtilisateur().getNom() %></td>
                          <td> 
                                 <button type="submit" class="btn btn-primary">valider</button>
                          </td>
                          <input type="hidden" name="idDepotRetraitTemporaire" value="<%= depotRetraitTemporaire.getIdDepotRetrait() %>" > 
                          <input type="hidden" name="dateDepotRetrait" value="<%= depotRetraitTemporaire.getDateDepotRetrait() %>" >
                          <input type="hidden" name="idUser" value="<%= depotRetraitTemporaire.getUtilisateur().getIdUtilisateur() %>" >  
                          <input type="hidden" name="montant" value="<%= depotRetraitTemporaire.getRetrait() %>" >
                      <% }} %> 
                </tr>
            </tbody>
        </table> 
    </div>
    </form> 
  </main><!-- End #main -->

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