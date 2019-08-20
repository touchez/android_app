package com.example.a13162.activitytest.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.a13162.activitytest.entity.NfcUsageEntity;
import com.example.a13162.activitytest.utils.DateConverter;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
@TypeConverters(DateConverter.class)
public interface NfcUsageDAO {

    @Query("SELECT * FROM nfcusageentity")
    LiveData<List<NfcUsageEntity>> getAll();

    @Insert
    void insertAll(NfcUsageEntity... nfcUsageEntities);

    @Insert(onConflict = IGNORE)
    void insert(NfcUsageEntity nfcUsageEntities);
}
