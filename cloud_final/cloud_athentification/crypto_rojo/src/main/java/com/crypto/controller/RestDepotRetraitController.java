package com.crypto.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.crypto.service.DepotRetraitTemporaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import com.crypto.model.DepotRetrait;
import com.crypto.model.DepotRetraitTemporaire;
import com.crypto.model.ResponseFormatter;
import com.crypto.repository.DepotRetraitRepository;
import com.crypto.repository.DepotRetraitTemporaireRepository;
import com.crypto.repository.UtilisateurRepository;
import com.crypto.service.UserService;
import com.crypto.service.utilisateur.UtilisateurService;


@RestController
public class RestDepotRetraitController {


    @Value("${link_symfony}")
    private String url;

    @Autowired
    private  DepotRetraitRepository depotRetraitRepo ;

    @Autowired
    private DepotRetraitTemporaireService depotRetraitTempRepo ;


    @Autowired
    UserService userService ;

    @Autowired
    UtilisateurRepository utilisateurRepository ;

    @PostMapping("/DepotApi")
    public ResponseEntity<String> DepotMoney(@RequestParam("money") double money , 
                                             @RequestParam("token") String token , 
                                             @RequestParam("idUser") int idUser , 
                                             @RequestParam("dateDepotRetrait") LocalDateTime dateDepotRetrait , 
                                             @RequestParam("idDepotRetraitTemporaire")  int idDepotRetraitTemporaire ) throws Exception {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                 throw new Exception("Token invalid");
            }
            DepotRetrait depotRetrait = new DepotRetrait();
            depotRetrait.setIdUtilisateur(idUser);
            depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
            depotRetrait.setDepot(money);
            depotRetrait.setDateDepotRetrait( dateDepotRetrait ); 
            depotRetrait.setRetrait(0);
            depotRetraitRepo.insertDepotRetrait(depotRetrait);

            DepotRetraitTemporaire depotRetraitTemporaire = depotRetraitTempRepo.getById(String.valueOf(idDepotRetraitTemporaire)) ;
            depotRetraitTempRepo.delete( depotRetraitTemporaire ) ; 

            String data = "insertion de depot effectuee";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            String errorResponse = formatter.formatResponseToJson( "err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @PostMapping("/RetraitApi")
    public ResponseEntity<String> RetraitMoney(@RequestParam("money") double money ,  
                                               @RequestParam("token") String token , 
                                               @RequestParam("idUser") int idUser , 
                                               @RequestParam("dateDepotRetrait") LocalDateTime dateDepotRetrait , 
                                               @RequestParam("idDepotRetraitTemporaire")  int idDepotRetraitTemporaire ) {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                throw new Exception("Token invalid");
           }
           DepotRetrait depotRetrait = new DepotRetrait();
           depotRetrait.setIdUtilisateur(idUser);
           depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
           depotRetrait.setDepot( 0);
           depotRetrait.setDateDepotRetrait( dateDepotRetrait ); 
           depotRetrait.setRetrait(money);
           depotRetraitRepo.insertDepotRetrait(depotRetrait);

           DepotRetraitTemporaire depotRetraitTemporaire = depotRetraitTempRepo.getById(String.valueOf(idDepotRetraitTemporaire)) ;
           depotRetraitTempRepo.delete( depotRetraitTemporaire ) ; 

            String data = "insertion de retrait effectuee";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
           
            String errorResponse = formatter.formatResponseToJson("err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }



    @PostMapping("/DepotApiTemp")
    public ResponseEntity<String> DepotMoneyTemp(@RequestParam("money") double money , @RequestParam("token") String token , @RequestParam("idUser") int idUser ) throws Exception {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                 throw new Exception("Token invalid");
            }
            DepotRetrait depotRetrait = new DepotRetrait();
            DepotRetraitTemporaire depotRetraitTemporaire = new DepotRetraitTemporaire();
            depotRetraitTemporaire.setUtilisateur( utilisateurRepository.getById( idUser) );
            depotRetraitTemporaire.setDateDepotRetrait(LocalDateTime.now());
            depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
            depotRetrait.setDepot(money);
            depotRetrait.setRetrait(0);

            BigDecimal trueDepot = new BigDecimal( money );
            depotRetraitTemporaire.setDepot( trueDepot ); 
            BigDecimal trueRetrait = new BigDecimal(0 );
            depotRetraitTemporaire.setRetrait(trueRetrait) ;
            depotRetraitTempRepo.save( depotRetraitTemporaire ) ; 
           
            String data = "validation de depot effectuee";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            String errorResponse = formatter.formatResponseToJson( "err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/RetraitApiTemp")
    public ResponseEntity<String> RetraitMoneyTemp(@RequestParam("money") double money ,  @RequestParam("token") String token , @RequestParam("idUser") int idUser ) {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                throw new Exception("Token invalid");
           }
           DepotRetrait depotRetrait = new DepotRetrait();
           DepotRetraitTemporaire depotRetraitTemporaire = new DepotRetraitTemporaire();
           depotRetraitTemporaire.setUtilisateur( utilisateurRepository.getById( idUser) );
           depotRetraitTemporaire.setDateDepotRetrait(LocalDateTime.now());

           depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
           depotRetrait.setIdUtilisateur(idUser);
           depotRetrait.setDepot( 0 );
           depotRetrait.setRetrait(money);  

           BigDecimal trueDepot = new BigDecimal( 0 );
           depotRetraitTemporaire.setDepot( trueDepot ); 
           BigDecimal trueRetrait = new BigDecimal(money );
           depotRetraitTemporaire.setRetrait(trueRetrait) ;
           depotRetraitTempRepo.save( depotRetraitTemporaire ) ; 

            String data = "validation de retrait effectuee";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
           
            String errorResponse = formatter.formatResponseToJson("err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
