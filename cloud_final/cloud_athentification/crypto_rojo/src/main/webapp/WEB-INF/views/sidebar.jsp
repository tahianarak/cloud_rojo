<%@ page import="com.crypto.service.utilisateur.UtilisateurService" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.crypto.model.Utilisateur" %>
<%
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
%>
<aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
      <li class="nav-item">
        <a class="nav-link collapsed" href="/ListCrypto">
          <i class="bi bi-grid"></i>
          <span>list crypto</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="/crypto/getFormAcheter">
          <i class="bi bi-grid"></i>
          <span>Acheter Crypto</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="/formulaireDeVente">
          <i class="bi bi-grid"></i>
          <span>Vendre Crypto</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="/Depot">
          <i class="bi bi-grid"></i>
          <span>Depot</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link collapsed" href="/Retrait">
          <i class="bi bi-grid"></i>
          <span>Retrait</span>
        </a>
      </li>

       <li class="nav-item">
        <a class="nav-link collapsed" href="/getFormFiltreDate">
          <i class="bi bi-grid"></i>
          <span>Situation des utilisateurs</span>
        </a>
      </li>
      <li class="nav-item">
          <a class="nav-link collapsed" href="/historique">
              <i class="bi bi-clock-history"></i>
              <span>Historiques</span>
          </a>
      </li>


        <%if (utilisateur.getIs_admin()==1){%>
      <li class="nav-item">
              <a class="nav-link collapsed" href="/commission/filtres">
                <i class="bi bi-grid"></i>
                <span>Resume commission</span>
              </a>
            </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="/commission/getForm">
          <i class="bi bi-grid"></i>
          <span>Update commission</span>
        </a>
      </li>

       <li class="nav-item">
        <a class="nav-link collapsed" href="/ListDepot">
          <i class="bi bi-grid"></i>
          <span>List Depot</span>
        </a>
      </li>

       <li class="nav-item">
        <a class="nav-link collapsed" href="/ListRetrait">
          <i class="bi bi-grid"></i>
          <span>List Retrait</span>
        </a>
      </li>
        <%}%>

        <li class="nav-item">
            <a class="nav-link collapsed" href="/auth/deconnect">
                <i class="bi bi-grid"></i>
                <span>Deconnect</span>
            </a>
        </li>



    </ul>

  </aside>