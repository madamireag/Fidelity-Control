package com.example.fidelitycontrol;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "oferte",foreignKeys = @ForeignKey(entity = FidelityCard.class,parentColumns = "id",childColumns = "CardId",onDelete = CASCADE),indices=@Index("CardId"))
public class Oferta {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String denumireOferta;
    private String mesajOferta;
    private int nrDeZileValabilitate;

    private int CardId;

    @Ignore
    public Oferta(String denumireOferta, String mesajOferta, int nrDeZileValabilitate) {
        this.denumireOferta = denumireOferta;
        this.mesajOferta = mesajOferta;
        this.nrDeZileValabilitate = nrDeZileValabilitate;
    }

    public Oferta(String denumireOferta, String mesajOferta, int nrDeZileValabilitate, int CardId) {
        this.denumireOferta = denumireOferta;
        this.mesajOferta = mesajOferta;
        this.nrDeZileValabilitate = nrDeZileValabilitate;
        this.CardId = CardId;
    }

    @Ignore
    public Oferta(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumireOferta() {
        return denumireOferta;
    }

    public void setDenumireOferta(String denumireOferta) {
        this.denumireOferta = denumireOferta;
    }

    public String getMesajOferta() {
        return mesajOferta;
    }

    public void setMesajOferta(String mesajOferta) {
        this.mesajOferta = mesajOferta;
    }

    public int getNrDeZileValabilitate() {
        return nrDeZileValabilitate;
    }

    public void setNrDeZileValabilitate(int nrDeZileValabilitate) {
        this.nrDeZileValabilitate = nrDeZileValabilitate;
    }

    public int getCardId() {
        return CardId;
    }

    public void setCardId(int cardId) {
        CardId = cardId;
    }

//    @Override
//    public String toString() {
//        return "Oferta{" +
//                "id=" + id +
//                ", denumireOferta='" + denumireOferta + '\'' +
//                ", mesajOferta='" + mesajOferta + '\'' +
//                ", nrDeZileValabilitate=" + nrDeZileValabilitate +
//                ", CardId=" + CardId +
//                '}';
//    }

    @Override
    public String toString() {
        return "Oferta{" +
                "denumireOferta='" + denumireOferta + '\'' +
                ", mesajOferta='" + mesajOferta + '\'' +
                ", nrDeZileValabilitate=" + nrDeZileValabilitate +
                '}';
    }
}
