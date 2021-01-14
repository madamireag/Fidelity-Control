package com.example.fidelitycontrol;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "cards")
public class FidelityCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cardNumber;
    private int nrPuncte;
    private String Companie;
    private Date dataExpirare;
    private String Proprietar;
    private static int minPointsForPremium=1000;

    @Ignore
    public FidelityCard(){

   }

   @Ignore
    public FidelityCard(String cardNumber, int nrPuncte, String companie, Date dataExpirare, String proprietar) {
        this.cardNumber = cardNumber;
        this.nrPuncte = nrPuncte;
        Companie = companie;
        this.dataExpirare = dataExpirare;
        Proprietar = proprietar;
    }


    public FidelityCard(int id, String cardNumber, int nrPuncte, String Companie, Date dataExpirare, String Proprietar) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.nrPuncte = nrPuncte;
        this.Companie = Companie;
        this.dataExpirare = dataExpirare;
        this.Proprietar = Proprietar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getNrPuncte() {
        return nrPuncte;
    }

    public void setNrPuncte(int nrPuncte) {
        this.nrPuncte = nrPuncte;
    }

    public String getCompanie() {
        return Companie;
    }

    public void setCompanie(String companie) {
        Companie = companie;
    }

    public Date getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(Date dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public String getProprietar() {
        return Proprietar;
    }

    public void setProprietar(String proprietar) {
        Proprietar = proprietar;
    }

    public static int getMinPointsForPremium() {
        return minPointsForPremium;
    }

    public static void setMinPointsForPremium(int minPointsForPremium) {
        FidelityCard.minPointsForPremium = minPointsForPremium;
    }

    @Override
    public String toString() {
        return "FidelityCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", nrPuncte=" + nrPuncte +
                ", Companie='" + Companie + '\'' +
                ", dataExpirare=" + dataExpirare +
                ", Proprietar='" + Proprietar + '\'' +
                '}';
    }
}
