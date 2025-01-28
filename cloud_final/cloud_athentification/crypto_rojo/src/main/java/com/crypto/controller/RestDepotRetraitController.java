package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import com.crypto.model.DepotRetrait;
import com.crypto.model.ResponseFormatter;
import com.crypto.repository.DepotRetraitRepository;
import com.crypto.service.UserService;


@RestController
public class RestDepotRetraitController {


    @Value("${link_symfony}")
    private String url;

    @Autowired
    private  DepotRetraitRepository depotRetraitRepo ;

    @Autowired
    UserService userService ;

    @PostMapping("/DepotApi")
    public ResponseEntity<String> DepotMoney(@RequestParam("money") double money , @RequestParam("token") String token , @RequestParam("idUser") int idUser ) throws Exception {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                 throw new Exception("Token invalid");
            }
            DepotRetrait depotRetrait = new DepotRetrait();
            depotRetrait.setIdUtilisateur(idUser);

            depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
            depotRetrait.setDepot(money);
            depotRetrait.setRetrait(0);
            depotRetraitRepo.insertDepotRetrait(depotRetrait);

            String data = "insertion de depot effectuée";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            String errorResponse = formatter.formatResponseToJson( "err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @PostMapping("/RetraitApi")
    public ResponseEntity<String> RetraitMoney(@RequestParam("money") double money ,  @RequestParam("token") String token , @RequestParam("idUser") int idUser ) {
        ResponseFormatter formatter = new ResponseFormatter(); 
        try {
            if( this.userService.verfiyValidityOfToken(token, url+"/api/verify/token") == false ){
                throw new Exception("Token invalid");
           }
            DepotRetrait depotRetrait = new DepotRetrait();
            depotRetrait.setDepotRetraitRepository( this.depotRetraitRepo); 
            depotRetrait.setIdUtilisateur(idUser);
            depotRetrait.setDepot(0);
            depotRetrait.setRetrait(money);
            depotRetraitRepo.insertDepotRetrait(depotRetrait);

            String data = "insertion de retrait effectuée";
            String jsonResponse = formatter.formatResponseToJson(data, null);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
           
            String errorResponse = formatter.formatResponseToJson("err", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
