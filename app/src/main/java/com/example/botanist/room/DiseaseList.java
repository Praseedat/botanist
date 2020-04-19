package com.example.botanist.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "disease_table") //name of the table
public class DiseaseList {
    @PrimaryKey(autoGenerate = true)//Room will auto generate the ID , we don't wanna worry about it
    private int id;
    private String disease;
    private String date;

    public DiseaseList(String disease, String date) {
        this.disease = disease;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public String getDate() {
        return date;
    }
}

