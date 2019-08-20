package com.example.a13162.activitytest.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a13162.activitytest.database.AppDataBase;
import com.example.a13162.activitytest.entity.NfcUsageEntity;
import com.example.a13162.activitytest.utils.DatabaseInitializer;

import java.util.Date;
import java.util.List;

public class NfcUsageViewModel extends AndroidViewModel {

    public final LiveData<List<NfcUsageEntity>> nfcUsages;

    private AppDataBase mdb;

    public NfcUsageViewModel(Application application) {
        super(application);
        createDb();

        nfcUsages = mdb.nfcUsageDAO().getAll();
    }

    public void createDb() {
        mdb = AppDataBase.getInstance(this.getApplication());
    }

    public void insert(String xcxName, String xcxPath) {
        DatabaseInitializer.insertAsync(mdb, xcxName, xcxPath);
    }



}
