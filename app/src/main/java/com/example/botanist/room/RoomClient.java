package com.example.botanist.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DiseaseList.class}, version = 1, exportSchema = false)
public abstract class RoomClient extends RoomDatabase {

    private static volatile RoomClient INSTANCE;

    public static RoomClient getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomClient.class, "plant_disease_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DiseaseDao diseaseDao();
}

