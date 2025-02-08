package com.crypto.controller.front;


import com.crypto.model.crypto.Cryptos;
import com.crypto.model.crypto.Mapper;
import com.crypto.model.cryptos.Crypto;
import com.crypto.service.CryptoService;
import com.crypto.service.crypto.MyCryptoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Controller
public class CryptoController
{
    @Value("${link_spring}")
    private String url;

    @Autowired
    MyCryptoService myCryptoService;

    @GetMapping("/formulaireDeVente")
    public ModelAndView getFormulaireVente()throws  Exception
    {
        try{
            ModelAndView mv=new ModelAndView("vendre");
            List<Crypto> lsCryptos =myCryptoService.getAll();
            mv.addObject("cryptos", lsCryptos);
            return mv;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;

    }

    @PostMapping("/insertVente")
    public void insertVente(HttpServletRequest requestpage, HttpServletResponse response, HttpSession session) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String idUser=(String)session.getAttribute("idUser");



        HashMap<String,String> map=new HashMap<>();
        map.put("idCrypto",requestpage.getParameter("idCrypto"));
        map.put("quantite",requestpage.getParameter("quantite"));
        map.put("idUtilisateur",String.valueOf((String)session.getAttribute("idUser")));
        map.put("token",String.valueOf((String)session.getAttribute("token")));

        String reponse=Mapper.sendPostRequest(this.url+"/create",map,restTemplate);
        String redirectString = "/insertVente";
        PrintWriter writer = response.getWriter();
        writer.println("<script type='text/javascript'>"
                + "alert('"+reponse+"');"
                + "window.location.href = '" + redirectString + "';"
                + "</script>");

    }
}
