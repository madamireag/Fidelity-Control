package com.example.fidelitycontrol;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FidelityCardDao {

    @Insert
    long insert(FidelityCard card);

    @Insert
    void insert(List<FidelityCard> carduri);

    @Query("select * from cards")
    List<FidelityCard> getAll();

    @Query("delete from cards")
    void deleteAll();

    @Delete
    void deleteFidelityCard(FidelityCard fc);

    @Query("SELECT * FROM cards WHERE id =:id")
    FidelityCard getCardById(int id);

    @Update
    int update(FidelityCard fc);
}
