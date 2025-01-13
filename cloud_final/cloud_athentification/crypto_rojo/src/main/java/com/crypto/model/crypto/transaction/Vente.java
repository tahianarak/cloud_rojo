package com.crypto.model.crypto.transaction;

import com.crypto.model.crypto.Cryptos;
import com.crypto.model.crypto.Portefeuille;
import com.crypto.model.crypto.depot.DepotRetrait;

import java.sql.Connection;
import java.sql.Date;

public class Vente {



    int idUtilisateur;

    int idCrypto;

    double qt;

    Transaction transaction;
    Portefeuille portefeuille;

    Cryptos cr;


    DepotRetrait depotRetrait;

    public DepotRetrait getDepotRetrait() {
        return depotRetrait;
    }

    public void setDepotRetrait(DepotRetrait depotRetrait) {
        this.depotRetrait = depotRetrait;
    }


    public Vente(int idUtilisateur, int idCrypto, double qt,Connection connection) throws Exception {

        this.idUtilisateur = idUtilisateur;
        this.idCrypto = idCrypto;
        this.setQt(qt);
        Cryptos cryptos = Cryptos.read(connection,idCrypto);
        this.cr= cryptos;
        Transaction trans=new Transaction(-1,qt* cryptos.getPrixActuelle(),0,new Date(new java.util.Date().getTime()),idCrypto,idUtilisateur);
        this.transaction=trans;
        Portefeuille porte=new Portefeuille(-1,0,qt,new Date(new java.util.Date().getTime()),idCrypto,idUtilisateur);
        this.portefeuille=porte;
        setDepotRetrait(new DepotRetrait(-1,qt* cryptos.getPrixActuelle(),0,new Date(new java.util.Date().getTime()),idUtilisateur));
    }

    public void createObject(Connection connection)throws  Exception
    {

        if (Portefeuille.verifyIfNotEmpty(connection,this.idUtilisateur,this.idCrypto)==false){
            throw  new Exception("vous n'avez pas assez de crypto:"+this.cr.getLibelle());
        }
        Transaction.create(connection,this.transaction);
        Portefeuille.create(connection,this.portefeuille);
        DepotRetrait.create(connection,this.depotRetrait);
    }


    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public double getQt() {
        return qt;
    }

    public void setQt(double qt)throws  Exception {
        if(qt<0)
        {
            throw new Exception("la quantite de crypto vendue n'est pas valide");
        }
        this.qt = qt;
    }






    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Portefeuille getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(Portefeuille portefeuille) {
        this.portefeuille = portefeuille;
    }

    public Vente(Transaction transaction) {
        this.transaction = transaction;
    }




}
