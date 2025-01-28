package com.crypto.controller.commission;

import com.crypto.model.Commission;
import com.crypto.model.cryptos.Crypto;
import com.crypto.service.CommissionService;
import com.crypto.service.crypto.MyCommissionService;
import com.crypto.service.crypto.MyCryptoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/commission")
public class CommissionController
{
    @Autowired
    CommissionService commissionService;

    @Autowired
    MyCryptoService myCryptoService;

    @Autowired
    MyCommissionService myCommissionService;

    @PostMapping("/updatePrix")
    public String updateCommission(HttpServletRequest request)
    {
        Crypto crypto = myCryptoService.getById(request.getParameter("idCrypto"));
        Date date = java.sql.Date.valueOf(request.getParameter("date"));
        Timestamp timestamp = new Timestamp(date.getTime());
        double new_commission  = Double.valueOf(request.getParameter("commission"));
        com.crypto.model.cryptos.Commission commission = new com.crypto.model.cryptos.Commission();
        commission.setPourcentage(new_commission);
        commission.setDescription("UPDATE commission of :"+crypto.getLibelle());
        commission.setDateEns(timestamp);
        commission.setCrypto(crypto);

        myCommissionService.save(commission);
        return "redirect:/commission/getForm";
    }
    @GetMapping("/getForm")
    public String getFormCommission(HttpServletRequest request)
    {
        request.setAttribute("cryptos",myCryptoService.getAll());
        return "UpdateCommission";
    }
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
