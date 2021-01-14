package com.example.fidelitycontrol;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OfertaDao {

    @Insert
    long insert(Oferta oferta);

    @Query("select * from oferte")
    List<Oferta> getAll();

    @Query("delete from oferte")
    void deleteAll();

    @Query("select * from oferte where CardId= :CardId")
    List<Oferta> getOferteForCard(int CardId);
}
