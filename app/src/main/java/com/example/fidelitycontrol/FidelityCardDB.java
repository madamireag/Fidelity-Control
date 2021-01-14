package com.example.fidelitycontrol;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Oferta.class,FidelityCard.class},version = 1,exportSchema = false)
@TypeConverters({DateConvertor.class})
public abstract class FidelityCardDB extends RoomDatabase {
    private final static String DB_NAME = "cards.db";
    private static FidelityCardDB instanta;

    public static FidelityCardDB getInstanta(Context context)
    { if (instanta == null) {
        //nu permite accesul simultan pt 2 activitati/fragmente
        synchronized (FidelityCardDB.class) {
            if (instanta == null) {
                instanta = Room.databaseBuilder(context, FidelityCardDB.class, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration().build();
            }
        }
    }
        return instanta;
    }
   public abstract FidelityCardDao getFidelityCardDao();
   public abstract OfertaDao getOfertaDao();
}
