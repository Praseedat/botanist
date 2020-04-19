package com.example.botanist.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiseaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
// to replace in-case of any conflicts
    void insert(DiseaseList data);

    @Query("DELETE FROM disease_table")
    void deleteAllData();


    @Query("SELECT * FROM disease_table")
    LiveData<List<DiseaseList>> getAllData();// Observe the object , so if there is any changes in the table this value will be auto updated and the activity will be notified
}
