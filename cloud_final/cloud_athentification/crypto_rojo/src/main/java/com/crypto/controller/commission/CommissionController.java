package com.crypto.controller.commission;

import com.crypto.model.Commission;
import com.crypto.model.cryptos.Crypto;
import com.crypto.service.CommissionService;
import com.crypto.service.crypto.MyCryptoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commission")
public class CommissionController
{
    @Autowired
    CommissionService commissionService;

    @Autowired
    MyCryptoService myCryptoService;

    @GetMapping("/resultats")
    public ModelAndView getResultatFiltre(HttpServletRequest request) throws SQLException {
        ModelAndView mv=getFiltresCriteria();
        String id_crypto=request.getParameter("crypto");

        int idCrypto=-1;
        int typeAnalyse=Integer.valueOf(request.getParameter("type_analyse"));

        Timestamp dateMin=Commission.parseDatetimeLocal(request.getParameter("date_min"));
        Timestamp dateMax=Commission.parseDatetimeLocal(request.getParameter("date_max"));

        if(id_crypto!=null && id_crypto.equals("")==false)
        {
            idCrypto=Integer.valueOf(id_crypto);
        }
        List<Commission> commissions=new ArrayList<>();
        Commission commission=this.commissionService.getCommissionByCriteria(dateMin,dateMax,typeAnalyse,idCrypto);
        commissions.add(commission);
        mv.addObject("commissions",commissions);

        return  mv;

    }
    @GetMapping("/filtres")
    public ModelAndView getFiltresCriteria()
    {
        ModelAndView mv = new ModelAndView("analyseCommission");

        List<Crypto> lsCryptos =myCryptoService.getAll();
        List<Commission> commissions=new ArrayList<>();

        mv.addObject("cryptos",lsCryptos);
        mv.addObject("commissions",commissions);

        return  mv;

    }
}
